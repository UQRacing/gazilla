/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.client

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Version
import com.uqracing.gazilla.client.screens.LoadingScreen
import com.uqracing.gazilla.client.screens.ProceduralExamples
import com.uqracing.gazilla.client.utils.CLIENT_VERSION
import org.tinylog.kotlin.Logger

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
class GazillaClient : Game() {
    override fun create() {
        Logger.info("Gazilla Client v$CLIENT_VERSION - Matt Young, 2022, UQRacing")
        Logger.info("Using libGDX v${Version.VERSION}")
        setScreen(LoadingScreen(this))
//        setScreen(ProceduralExamples())
    }
}
