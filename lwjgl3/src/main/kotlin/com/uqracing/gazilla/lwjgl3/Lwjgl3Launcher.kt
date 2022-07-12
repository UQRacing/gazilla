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
import com.badlogic.gdx.utils.reflect.ClassReflection
import com.esotericsoftware.yamlbeans.YamlConfig
import com.esotericsoftware.yamlbeans.YamlReader
import com.uqracing.gazilla.client.GazillaClient
import com.uqracing.gazilla.client.utils.CLIENT_CONFIG
import com.uqracing.gazilla.client.utils.ClientConfig
import java.io.File

/** Launches the desktop (LWJGL3) application. */
fun main() {
    // configure tinylog
    System.setProperty("tinylog.configuration", "assets/tinylog.properties")

    // perform early load of client config, we don't have Gdx.* yet, so we do it manually
    val configYaml = File("assets/client.yaml").readText()
    CLIENT_CONFIG = YamlReader(configYaml).read(ClientConfig::class.java)
    println("Early load of client config successful: $CLIENT_CONFIG")

    Lwjgl3Application(GazillaClient(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("Gazilla Client")
        setMaximized(true)
        setWindowedMode(1600, 900)
        // FIXME it's low res in the alt tab menu
        setWindowIcon(*(arrayOf(512, 128, 64, 32, 16).map { "gazilla$it.png" }.toTypedArray()))
        // required to enable MSAA
        setBackBufferConfig(8, 8, 8, 8, 16, 0, CLIENT_CONFIG.msaaSamples)
    })
}
