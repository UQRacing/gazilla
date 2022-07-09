/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.client.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.uqracing.gazilla.client.GazillaClient
import com.uqracing.gazilla.client.utils.ASSETS
import com.uqracing.gazilla.client.utils.Assets
import ktx.app.clearScreen
import org.tinylog.kotlin.Logger
import kotlin.math.roundToInt

class LoadingScreen(private val game: GazillaClient) : ScreenAdapter() {
    private lateinit var skin: Skin
    private lateinit var stage: Stage
    private lateinit var label: Label

    override fun show() {
        skin = Skin(Gdx.files.local("assets/uiskin/cloud-form-ui.json"))
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage

        val window = Window("Notice", skin)
        window.align(Align.center)
        window.center()

        label = Label("Loading assets (0%)", skin)
        label.pack()
        window.add(label)
        window.pack()

        val container = Container(window)
        container.setFillParent(true)
        container.center()

        stage.addActor(container)

        Assets.load(ASSETS)
    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f, 1f)
        stage.act(delta)
        stage.draw()

        if (ASSETS.update()) {
            // finished loading assets
            Logger.debug("Finished loading assets")
            label.setText("Initialising renderer...")
            // make sure the text gets updated
            stage.act(delta)
            stage.draw()
            game.screen = SimulationScreen()
        } else {
            // not yet done
            val completion = (ASSETS.progress * 100.0).roundToInt()
            label.setText("Loading assets ($completion%)")
        }
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        skin.dispose()
        stage.dispose()
    }
}