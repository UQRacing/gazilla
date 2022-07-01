package com.uqracing.gazilla.client

import com.badlogic.gdx.Game
import com.uqracing.gazilla.client.screens.LoadingScreen
import com.uqracing.gazilla.client.utils.CLIENT_VERSION
import org.tinylog.kotlin.Logger

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
class GazillaClient : Game() {
    override fun create() {
        Logger.info("Gazilla Client v$CLIENT_VERSION - Matt Young, 2022, UQRacing")
        setScreen(LoadingScreen(this))
    }
}
