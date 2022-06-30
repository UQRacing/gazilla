package com.uqracing.gazilla

import com.badlogic.gdx.Game
import com.uqracing.gazilla.screens.LoadingScreen
import com.uqracing.gazilla.utils.GAZILLA_VERSION
import org.tinylog.kotlin.Logger

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
class Gazilla : Game() {
    override fun create() {
        Logger.info("Gazilla v${GAZILLA_VERSION} - Matt Young, 2022, UQRacing")
        setScreen(LoadingScreen(this))
    }
}
