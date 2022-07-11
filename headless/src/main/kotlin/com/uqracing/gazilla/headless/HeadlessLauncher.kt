/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:JvmName("HeadlessLauncher")

package com.uqracing.gazilla.headless

import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
import com.uqracing.gazilla.server.GazillaServer
import org.tinylog.kotlin.Logger

/** Launcher for the Gazilla server */
fun main(args: Array<String>) {
    // configure tinylog
    System.setProperty("tinylog.configuration", "assets/tinylog.properties")

    if (args.isEmpty()) {
        throw IllegalArgumentException("You must provide the name of the vehicle to simulate as the first argument.")
    }
    val vehicleName = args[0]

    HeadlessApplication(GazillaServer(vehicleName), HeadlessApplicationConfiguration().apply {
        updatesPerSecond = 60
    })
    // TODO start an Swing/JavaFX window in the background with the gazilla icon that just says "Server is running"
    //  and can be used to quit the server
}