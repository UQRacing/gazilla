/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.client.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.crashinvaders.vfx.VfxManager
import com.esotericsoftware.yamlbeans.YamlReader
import com.uqracing.gazilla.client.ecs.SceneComponent
import com.uqracing.gazilla.client.utils.ASSETS
import com.uqracing.gazilla.common.Track
import com.uqracing.gazilla.common.ecs.EntityTypeComponent
import com.uqracing.gazilla.common.ecs.TransformComponent
import com.uqracing.gazilla.common.utils.COMMON_CONFIG
import com.uqracing.gazilla.common.utils.CommonConfig
import com.uqracing.gazilla.common.utils.EntityType
import ktx.app.clearScreen
import ktx.ashley.entity
import ktx.ashley.with
import ktx.assets.DisposableContainer
import ktx.assets.disposeSafely
import ktx.scene2d.*
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx
import net.mgsx.gltf.scene3d.scene.Scene
import net.mgsx.gltf.scene3d.scene.SceneAsset
import net.mgsx.gltf.scene3d.scene.SceneManager
import net.mgsx.gltf.scene3d.scene.SceneSkybox
import net.mgsx.gltf.scene3d.utils.IBLBuilder
import org.tinylog.kotlin.Logger
import java.awt.SystemColor.window


/**
 * Main class for the graphical simulation on the client
 */
class SimulationScreen : ScreenAdapter() {
    private val multiplexer = InputMultiplexer()
    private val camera = PerspectiveCamera().apply {
        fieldOfView = 75f
        near = 0.01f
        far = 75f
    }
    private val cameraController = CameraInputController(camera).apply {
        translateButton = Input.Buttons.MIDDLE
        translateUnits = 7f
        scrollFactor = -0.01f
    }
    private val cameraViewport = ExtendViewport(1600f, 900f, camera)
    private val manager = SceneManager(0)
    private val shapeRender = ShapeRenderer()
    private lateinit var wheelScene: Scene
    private val engine = Engine()
    private lateinit var vfx: VfxManager
//    private lateinit var fxaaEffect: FxaaEffect
    private val disposables = DisposableContainer()
    private val stage = Stage(ScreenViewport())
    private val batch = SpriteBatch()
    private lateinit var bmfont: BitmapFont

    private fun loadConfig() {
        Logger.debug("Loading config")
        // client config YAML is already loaded
        // TODO load vehicle config

        // load shared config
        val commonConfigFile = Gdx.files.local("assets/common.yaml")
        COMMON_CONFIG = YamlReader(commonConfigFile.readString()).read(CommonConfig::class.java)
        Logger.debug(COMMON_CONFIG)
    }

    private fun initialiseEcs() {
        Logger.debug("Initialising world")
        val vehicle = ASSETS["assets/vehicles/rooster/whole_car.glb", SceneAsset::class.java]
        val wheel = ASSETS["assets/vehicles/rooster/wheel.glb", SceneAsset::class.java]

        // add vehicle
        engine.entity {
            with<TransformComponent>()
            with<EntityTypeComponent> { entityType = EntityType.VEHICLE }
            with<SceneComponent> { scene = Scene(vehicle.scene) }
        }

        // add vehicle wheels
        for (i in 0 until 4) {
            engine.entity {
                with<TransformComponent>() // TODO load transform
                with<EntityTypeComponent> { entityType = EntityType.WHEEL_FL }
                with<SceneComponent> { scene = Scene(wheel.scene) }
            }
        }

        // TODO add the camera to the ECS and have a method to update it to follow the car as it moves?
        // TODO how do we add scenes to the model graph? we need some type of RenderSystem
    }

    private fun initialiseTrack() {
        val trackFile = Gdx.files.local(COMMON_CONFIG.trackFile)
        Logger.debug("Loading track file $trackFile")
        val track = YamlReader(trackFile.readString()).read(Track::class.java)

        // add cone 3D models based on track data
        for (blueCone in track.cones_left) {

        }
        for (yellowCone in track.cones_right) {

        }
    }

    /** Setup 3D the base environment, car and gltf renderer. Cones will be added later, in initialiseTrack. */
    private fun initialise3D() {
        Logger.debug("Initialising 3D graphics")

        val vehicle = ASSETS["assets/vehicles/rooster/whole_car.glb", SceneAsset::class.java]
        val wheel = ASSETS["assets/vehicles/rooster/wheel.glb", SceneAsset::class.java]
        val vehicleScene = Scene(vehicle.scene)
        wheelScene = Scene(wheel.scene)
        wheelScene.modelInstance.transform.translate(0f, 0.2f, 0.4f) // TODO make this relative to vehicle centre?
        wheelScene.modelInstance.calculateTransforms()

        manager.addScene(vehicleScene)
        manager.addScene(wheelScene)
        manager.setCamera(camera)

        // adjust camera to look at the car
        val bbox = vehicleScene.modelInstance.calculateBoundingBox(BoundingBox())
        val carCentre = Vector3(bbox.centerX, bbox.centerY, bbox.centerZ)
        cameraController.target = carCentre
        cameraController.autoUpdate = false
        camera.lookAt(carCentre)
        cameraController.zoom(-2f)
        camera.rotateAround(carCentre, Vector3(0f, 0f, 1f), 90f)
        camera.rotateAround(carCentre, Vector3(0f, 1f, 0f), 180f) // TODO make this configurable?
        camera.update()

        // setup the sun light
        val light = DirectionalLightEx()
        // TODO make GUI to customise light (and align it with the sun)
        light.direction.set(1f, -3f, 1f).nor()
        light.intensity = 8f
        light.color.set(Color.WHITE)
        manager.environment.add(light)

        // setup quick IBL (image based lighting)
        val iblBuilder = IBLBuilder.createOutdoor(light)
        val environmentCubemap = makeCubemap("assets/environment/background", "jpg")
        val diffuseCubemap = makeCubemap("assets/environment/irradiance", "png") // iradiance
        val specularCubemap = makeCubemap("assets/environment/radiance", "png") // radiance
        iblBuilder.dispose()
        disposables.register(environmentCubemap)
        disposables.register(diffuseCubemap)
        disposables.register(specularCubemap)

        // This texture is provided by the library, no need to have it in your assets.
        val brdfLut = ASSETS["net/mgsx/gltf/shaders/brdfLUT.png", Texture::class.java]

        manager.setAmbientLight(1f)
        manager.environment.set(PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLut))
        manager.environment.set(PBRCubemapAttribute.createSpecularEnv(specularCubemap))
        manager.environment.set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap))

        // setup skybox
        val skybox = SceneSkybox(environmentCubemap)
        manager.skyBox = skybox

        // setup post-processing
        vfx = VfxManager(Pixmap.Format.RGBA8888)
//        fxaaEffect = FxaaEffect()
//        vfx.addEffect(fxaaEffect)

        // create ground plane thing
        val modelBuilder = ModelBuilder()
        val material = Material()
        material.set(PBRColorAttribute.createBaseColorFactor(Color(Color.WHITE)))
        val attribs = VertexAttributes.Usage.Position.toLong() or VertexAttributes.Usage.Normal.toLong()
        val model = modelBuilder.createLineGrid(100, 100, 1f, 1f, material, attribs)
        disposables.register(model)
        manager.renderableProviders.add(ModelInstance(model))
    }

    private fun initialiseGui() {
        val window = scene2d.window("Configuration") {
            label("Testing")
            slider()
            pack()
            pad(10f)
        }
        val container = Container(window)
        container.setFillParent(true)
        container.right().top()
//        container.debugAll()
        container.pad(20f)
        stage.addActor(container)
    }

    override fun show() {
        val skin = ASSETS["assets/uiskin/cloud-form-ui.json", Skin::class.java]
        Scene2DSkin.defaultSkin = skin

        loadConfig()
        initialise3D() // this will initialise the renderer, car model, grid, camera, etc
        initialiseEcs() // now, we'll bind the car model and related into the ECS
        initialiseTrack() // and finally add the cones and related data
        initialiseGui() // create GUI windows

        // misc ininitialisation not covered above
        bmfont = ASSETS["assets/uiskin/debug.fnt", BitmapFont::class.java]

        multiplexer.addProcessor(stage)
        multiplexer.addProcessor(cameraController)
        Gdx.input.inputProcessor = multiplexer
    }

    override fun render(delta: Float) {
        clearScreen(0.0f, 0.0f, 0.0f, 1.0f)

        // update
        cameraController.update()
        // TODO clamp camera angle so it doesn't go under the ground
        camera.update()

        wheelScene.modelInstance.transform.rotate(Vector3(0f, 0f, 1f), delta * -1000f)
        wheelScene.modelInstance.calculateTransforms()
        manager.update(delta)

        stage.act(delta) // update UI

        // render
        // FIXME fix that shadow-acne like thing when you zoom out
        manager.render() // render 3D

        stage.draw() // draw UI

        batch.begin()
        val totalMemory = (Gdx.app.javaHeap + Gdx.app.nativeHeap) / 1048576
        bmfont.draw(batch, "FPS: ${Gdx.graphics.framesPerSecond}    Memory: $totalMemory MB", 64f, 64f)
        batch.end()

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            // quit simulation (TODO only for debug)
            Gdx.app.exit()
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            // reset camera
            Logger.debug("Resetting camera")
            // TODO get a good method for this, need to reset both the controller and the camera
        }
    }

    override fun resize(width: Int, height: Int) {
        cameraViewport.update(width, height)
        manager.updateViewport(width.toFloat(), height.toFloat())
        stage.viewport.update(width, height)
    }

    override fun dispose() {
        // no main menu, we just quit after this, so we can dispose the assets
        ASSETS.dispose()
        shapeRender.dispose()
//        fxaaEffect.dispose()
        vfx.dispose()
        stage.dispose()
        batch.dispose()
        disposables.disposeSafely()
    }

    /**
     * Generates a cubemap from a folder loaded by the AssetManager.
     * @param path path to folder storing cubemaps WITHOUT trailing slash
     * @param extension extension WITHOUT dot, e.g. "jpg"
     * @returns generated cubemap, must be disposed
     */
    private fun makeCubemap(path: String, extension: String): Cubemap {
        val nx = ASSETS["$path/nx.$extension", Texture::class.java]
        val ny = ASSETS["$path/ny.$extension", Texture::class.java]
        val nz = ASSETS["$path/nz.$extension", Texture::class.java]
        val px = ASSETS["$path/px.$extension", Texture::class.java]
        val py = ASSETS["$path/py.$extension", Texture::class.java]
        val pz = ASSETS["$path/pz.$extension", Texture::class.java]
        val cube = Cubemap(px.textureData, nx.textureData, py.textureData, ny.textureData, pz.textureData, nz.textureData)
        cube.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        return cube
    }
}