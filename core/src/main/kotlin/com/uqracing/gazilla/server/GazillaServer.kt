/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.server

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.esotericsoftware.yamlbeans.YamlReader
import com.uqracing.gazilla.common.ecs.EntityTypeComponent
import com.uqracing.gazilla.common.Track
import com.uqracing.gazilla.common.ecs.TransformComponent
import com.uqracing.gazilla.common.utils.COMMON_CONFIG
import com.uqracing.gazilla.common.utils.CommonConfig
import com.uqracing.gazilla.common.utils.EntityType
import com.uqracing.gazilla.server.ecs.PhysicsSystem
import com.uqracing.gazilla.server.ecs.SerialiserSystem
import com.uqracing.gazilla.server.ecs.TransportIndicatorComponent
import com.uqracing.gazilla.server.network.ServerSocket
import com.uqracing.gazilla.server.utils.*
import ktx.ashley.entity
import ktx.ashley.with
import org.tinylog.kotlin.Logger

/**
 * Main class for the Gazilla server.
 */
class GazillaServer(private val vehicleName: String) : ApplicationAdapter() {
    private val engine = Engine()
    private lateinit var socket: ServerSocket
    private val serialiser = SerialiserSystem()

    /** Initialises the Ashley ECS for the server */
    private fun initialiseEcs() {
        Logger.debug("Initialising ECS")
        // TODO apply config to the world

        // add the car
        val car = engine.entity {
            with<TransformComponent>()
            with<EntityTypeComponent>() // FIXME how to set the entity type in here?
            with<TransportIndicatorComponent>()
        }

        // add systems
        engine.addSystem(PhysicsSystem())
        engine.addSystem(serialiser) // SerialiserSystem MUST run last
    }

    private fun addCone(x: Double, y: Double, type: EntityType) {
        engine.entity {
            with<TransformComponent>() // TODO set the transform
            with<EntityTypeComponent>() // TODO set the entity type
            // we don't transmit cones over the network, since the client gets the same track by
            // reading the common config YAML
        }
    }

    private fun initialiseTrack() {
        val trackFile = Gdx.files.local(COMMON_CONFIG.trackFile)
        Logger.debug("Loading track file $trackFile")
        val track = YamlReader(trackFile.readString()).read(Track::class.java)

        // add cones based on track data
//        println("x,y")
        for (blueCone in track.cones_left) {
//            println("${blueCone[0]},${blueCone[1]}")
        }
        for (yellowCone in track.cones_right) {
//            println("${yellowCone[0]},${yellowCone[1]}")
        }
    }

    private fun loadConfig() {
        Logger.debug("Loading configuration")
        // TODO use Utils.readYaml instead of copy n pasting

        val vehicleConfigFile = Gdx.files.local("assets/vehicles/$vehicleName/vehicle.yaml")
        VEHICLE_CONFIG = YamlReader(vehicleConfigFile.readString()).read(VehicleConfig::class.java)
        Logger.debug("${VEHICLE_CONFIG.metadata.name} by ${VEHICLE_CONFIG.metadata.author}")
        Logger.debug("Licence: ${VEHICLE_CONFIG.metadata.copyright}")

        // load server config YAML
        val serverConfigFile = Gdx.files.local("assets/server.yaml")
        SERVER_CONFIG = YamlReader(serverConfigFile.readString()).read(ServerConfig::class.java)
        Logger.debug(SERVER_CONFIG)

        // load shared config
        val commonConfigFile = Gdx.files.local("assets/common.yaml")
        COMMON_CONFIG = YamlReader(commonConfigFile.readString()).read(CommonConfig::class.java)
        Logger.debug(COMMON_CONFIG)
    }

    private fun initialiseServer() {
        socket = ServerSocket(SERVER_CONFIG.network.port)
    }

    override fun create() {
        Logger.info("Gazilla Server v$SERVER_VERSION - Matt Young, 2022, UQRacing")
        Logger.info("Attempting to load vehicle $vehicleName")

        // load vehicle config YAML
        loadConfig()

        // initialise the ECS and server
        initialiseEcs()
        initialiseTrack()
        initialiseServer()
        Logger.info("Loaded successfully")
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime
        engine.update(delta)
        // FIXME this blocks, should we put it on a queue or something?
        socket.transmit(serialiser.data)
    }
}