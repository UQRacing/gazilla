@file:JvmName("HeadlessLauncher")

package com.uqracing.gazilla.headless

import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
import com.uqracing.gazilla.Gazilla

/** Launches the headless application. Can be converted into a server application or a scripting utility. */
fun main() {
    HeadlessApplication(Gazilla(), HeadlessApplicationConfiguration().apply {
        // When this value is negative, GazillaTest#render() is never called:
        updatesPerSecond = -1
    })
}