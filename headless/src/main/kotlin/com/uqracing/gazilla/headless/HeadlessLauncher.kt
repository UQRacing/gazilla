@file:JvmName("HeadlessLauncher")

package com.uqracing.gazilla.headless

import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
import com.uqracing.gazilla.client.GazillaClient
import com.uqracing.gazilla.server.GazillaServer
import org.tinylog.kotlin.Logger

/** Launcher for the Gazilla server */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("You must provide the name of the vehicle to simulate as the first argument.")
    }
    val vehicleName = args[0]

    // FIXME why is tinylog configuration not working? (need wifi to debug that)

    HeadlessApplication(GazillaServer(vehicleName), HeadlessApplicationConfiguration().apply {
        updatesPerSecond = 60
    })
}