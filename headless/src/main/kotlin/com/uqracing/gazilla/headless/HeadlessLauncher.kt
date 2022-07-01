@file:JvmName("HeadlessLauncher")

package com.uqracing.gazilla.headless

import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
import com.uqracing.gazilla.client.GazillaClient
import com.uqracing.gazilla.server.GazillaServer

/** Launches the headless application. Can be converted into a server application or a scripting utility. */
fun main() {
    HeadlessApplication(GazillaServer(), HeadlessApplicationConfiguration().apply {
        updatesPerSecond = 60
    })
}