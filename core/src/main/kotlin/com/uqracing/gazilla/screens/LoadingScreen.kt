package com.uqracing.gazilla.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.uqracing.gazilla.Gazilla
import com.uqracing.gazilla.utils.ASSETS
import com.uqracing.gazilla.utils.Assets
import ktx.app.clearScreen
import org.tinylog.kotlin.Logger
import kotlin.math.roundToInt

class LoadingScreen(private val game: Gazilla) : Screen {
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

        Logger.debug("Loading assets")
        Assets.load(ASSETS)
    }

    override fun render(delta: Float) {
        clearScreen(0f, 0f, 0f, 1f)
        stage.act(delta)
        stage.draw()

        if (ASSETS.update()) {
            // finished loading assets
            Logger.debug("Finished loading assets")
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

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
    }

    override fun dispose() {
        skin.dispose()
        stage.dispose()
    }
}