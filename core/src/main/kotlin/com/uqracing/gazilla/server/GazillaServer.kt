package com.uqracing.gazilla.server

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.esotericsoftware.yamlbeans.YamlReader
import com.uqracing.gazilla.server.utils.*
import org.tinylog.kotlin.Logger

class GazillaServer(private val vehicleName: String) : ApplicationAdapter() {
    override fun create() {
        // FIXME why is the log format not right?
        Logger.info("Gazilla Server v$SERVER_VERSION - Matt Young, 2022, UQRacing")
        Logger.info("Simulating vehicle $vehicleName")

        // load vehicle config YAML
        val vehicleConfigFile = Gdx.files.local("assets/vehicles/$vehicleName/vehicle.yaml")
        if (!vehicleConfigFile.exists()) {
            throw IllegalArgumentException("Vehicle config file $vehicleConfigFile for vehicle " +
             "$vehicleName does not exist")
        }
        VEHICLE_CONFIG = YamlReader(vehicleConfigFile.readString()).read(VehicleConfig::class.java)
        Logger.debug(VEHICLE_CONFIG)

        // load server config YAML
        val serverConfigFile = Gdx.files.local("assets/server.yaml")
        SERVER_CONFIG = YamlReader(serverConfigFile.readString()).read(ServerConfig::class.java)
        Logger.debug(SERVER_CONFIG)

        // TODO apply config!
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime
    }
}