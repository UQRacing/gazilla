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
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.crashinvaders.vfx.VfxManager
import com.crashinvaders.vfx.effects.FxaaEffect
import com.esotericsoftware.yamlbeans.YamlReader
import com.uqracing.gazilla.client.utils.ASSETS
import com.uqracing.gazilla.common.Track
import com.uqracing.gazilla.common.utils.COMMON_CONFIG
import com.uqracing.gazilla.common.utils.CommonConfig
import ktx.app.clearScreen
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


/**
 * Main class for the graphical simulation on the client
 */
class SimulationScreen : ScreenAdapter() {
    private val multiplexer = InputMultiplexer()
    private val camera = PerspectiveCamera().apply {
        fieldOfView = 75f
        near = 0.01f
        far = 50f
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
    private lateinit var environmentCubemap: Cubemap
    private lateinit var diffuseCubemap: Cubemap
    private lateinit var specularCubemap: Cubemap
    private val engine = Engine()
    private lateinit var vfx: VfxManager
    private lateinit var fxaaEffect: FxaaEffect
    private lateinit var cubeModel: ModelInstance

    private fun loadConfig() {
        Logger.debug("Loading config")
        // load client config YAML
        // TODO implement this once client YAML is more well defined

        // load shared config
        val commonConfigFile = Gdx.files.local("assets/common.yaml")
        COMMON_CONFIG = YamlReader(commonConfigFile.readString()).read(CommonConfig::class.java)
        Logger.debug(COMMON_CONFIG)
    }

    private fun initialiseEcs() {
        Logger.debug("Initialising world")
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
        camera.rotateAround(carCentre, Vector3(0f, 1f, 0f), 180f)

        // setup the sun light
        val light = DirectionalLightEx()
        light.direction.set(1f, -3f, 1f).nor()
        light.color.set(Color.WHITE)
        manager.environment.add(light)

        // setup quick IBL (image based lighting)
        val iblBuilder = IBLBuilder.createOutdoor(light)
        environmentCubemap = makeCubemap()
        diffuseCubemap = iblBuilder.buildIrradianceMap(256)
        specularCubemap = iblBuilder.buildRadianceMap(10)
        iblBuilder.dispose()

        // This texture is provided by the library, no need to have it in your assets.
        val brdfLut = ASSETS["net/mgsx/gltf/shaders/brdfLUT.png", Texture::class.java]

        manager.setAmbientLight(1f)
        manager.environment.set(PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLut))
        manager.environment.set(PBRCubemapAttribute.createSpecularEnv(specularCubemap))
        manager.environment.set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap))

        // setup skybox
        val skybox = SceneSkybox(environmentCubemap)
        manager.setSkyBox(skybox)

        // setup post-processing
        vfx = VfxManager(Pixmap.Format.RGBA8888)
        fxaaEffect = FxaaEffect()
        vfx.addEffect(fxaaEffect)

        val builder = ModelBuilder()
    }

    override fun show() {
        loadConfig()
        initialise3D() // this will initialise the renderer, car model, camera, etc
        initialiseEcs() // now, we'll bind the car model and related into the ECS
        initialiseTrack() // and finally add the cones and related data

        // TODO create ground plane (preferably, a grid)

        multiplexer.addProcessor(cameraController)
        Gdx.input.inputProcessor = multiplexer
    }

    override fun render(delta: Float) {
        clearScreen(0.0f, 0.0f, 0.0f, 1.0f)
        // update
        // TODO draw bounding box around car
        cameraController.update()
        // TODO clamp camera angle so it doesn't go under the ground
        camera.update()

        wheelScene.modelInstance.transform.rotate(Vector3(0f, 0f, 1f), delta * -1000f)
        wheelScene.modelInstance.calculateTransforms()
        manager.update(delta)

        // render
        manager.render()

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
        }
    }

    override fun resize(width: Int, height: Int) {
        cameraViewport.update(width, height)
        manager.updateViewport(width.toFloat(), height.toFloat())
    }

    override fun dispose() {
        // no main menu, we just quit after this, so we can dispose the assets
        ASSETS.dispose()
        shapeRender.dispose()
        environmentCubemap.dispose()
        specularCubemap.dispose()
        diffuseCubemap.dispose()
        fxaaEffect.dispose()
        vfx.dispose()
    }

    /**
     * Generates a cubemap from the environment/cubemap/ folder of PNGs.
     */
    private fun makeCubemap(): Cubemap {
        val nx = ASSETS["assets/environment/background_miramar/nx.jpg", Texture::class.java]
        val ny = ASSETS["assets/environment/background_miramar/ny.jpg", Texture::class.java]
        val nz = ASSETS["assets/environment/background_miramar/nz.jpg", Texture::class.java]
        val px = ASSETS["assets/environment/background_miramar/px.jpg", Texture::class.java]
        val py = ASSETS["assets/environment/background_miramar/py.jpg", Texture::class.java]
        val pz = ASSETS["assets/environment/background_miramar/pz.jpg", Texture::class.java]
        val cube = Cubemap(px.textureData, nx.textureData, py.textureData, ny.textureData, pz.textureData, nz.textureData)
        cube.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        return cube
    }
}