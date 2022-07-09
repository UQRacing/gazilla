/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:JvmName("Lwjgl3Launcher")

package com.uqracing.gazilla.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.uqracing.gazilla.client.GazillaClient

/** Launches the desktop (LWJGL3) application. */
fun main() {
    // configure tinylog
    System.setProperty("tinylog.configuration", "assets/tinylog.properties")

    Lwjgl3Application(GazillaClient(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("Gazilla Client")
        setMaximized(true)
        setWindowedMode(1600, 900)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
