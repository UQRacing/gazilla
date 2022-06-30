@file:JvmName("Lwjgl3Launcher")

package com.uqracing.gazilla.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.uqracing.gazilla.Gazilla

/** Launches the desktop (LWJGL3) application. */
fun main() {
    Lwjgl3Application(Gazilla(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("Gazilla")
        setMaximized(true)
        setWindowedMode(1600, 900)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
