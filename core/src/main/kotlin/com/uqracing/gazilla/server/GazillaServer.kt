package com.uqracing.gazilla.server

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.uqracing.gazilla.server.utils.SERVER_VERSION

class GazillaServer : ApplicationAdapter() {
    override fun create() {
        println("Gazilla Server v$SERVER_VERSION - Matt Young, 2022, UQRacing")
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime
    }
}