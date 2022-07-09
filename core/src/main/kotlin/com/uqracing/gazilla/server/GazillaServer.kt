package com.uqracing.gazilla.server

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.esotericsoftware.yamlbeans.YamlReader
import com.uqracing.gazilla.common.TransformComponent
import com.uqracing.gazilla.server.ecs.PhysicsSystem
import com.uqracing.gazilla.server.ecs.SerialiserSystem
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

    /** Initialises the Ashley ECS for the server */
    private fun initialiseEcs() {
        Logger.debug("Initialising world")

        // add the car
        val car = engine.entity {
            with<TransformComponent>()
        }

        // TODO add cones (based on the map we're using)

        // add systems
        engine.addSystem(PhysicsSystem())
        engine.addSystem(SerialiserSystem()) // SerialiserSystem MUST run last
    }

    private fun loadConfig() {
        Logger.debug("Loading configuration")
        val vehicleConfigFile = Gdx.files.local("assets/vehicles/$vehicleName/vehicle.yaml")
        VEHICLE_CONFIG = YamlReader(vehicleConfigFile.readString()).read(VehicleConfig::class.java)
        Logger.debug(VEHICLE_CONFIG)

        // load server config YAML
        val serverConfigFile = Gdx.files.local("assets/server.yaml")
        SERVER_CONFIG = YamlReader(serverConfigFile.readString()).read(ServerConfig::class.java)
        Logger.debug(SERVER_CONFIG)
    }

    private fun initialiseServer() {
        socket = ServerSocket(SERVER_CONFIG.network.port)
    }

    override fun create() {
        // FIXME why is the log format not right?
        Logger.info("Gazilla Server v$SERVER_VERSION - Matt Young, 2022, UQRacing")
        Logger.info("Simulating vehicle $vehicleName")

        // load vehicle config YAML
        loadConfig()

        // initialise the ECS and server
        // TODO apply config to world
        initialiseEcs()
        initialiseServer()
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime
        engine.update(delta)

        // network dispatch?
    }
}