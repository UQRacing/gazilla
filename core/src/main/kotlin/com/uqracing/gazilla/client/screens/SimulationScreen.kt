package com.uqracing.gazilla.client.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.uqracing.gazilla.client.utils.ASSETS
import ktx.app.clearScreen
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx
import net.mgsx.gltf.scene3d.scene.Scene
import net.mgsx.gltf.scene3d.scene.SceneAsset
import net.mgsx.gltf.scene3d.scene.SceneManager
import net.mgsx.gltf.scene3d.scene.SceneSkybox
import net.mgsx.gltf.scene3d.utils.IBLBuilder
import kotlin.random.Random

/**
 * Main class for the graphical simulation on the client
 */
class SimulationScreen : Screen {
    private val multiplexer = InputMultiplexer()
    private val camera = PerspectiveCamera()
    private val viewport = ExtendViewport(1600f, 900f, camera)
    private val cameraController = CameraInputController(camera).apply {
        translateButton = Input.Buttons.MIDDLE
        translateUnits = 7f
        scrollFactor = -0.01f
    }
    private val manager = SceneManager()
    private val shapeRender = ShapeRenderer()
    private var bbox = BoundingBox()
    private lateinit var wheelScene: Scene

    override fun show() {
        camera.fieldOfView = 75f
        camera.near = 0.01f
        camera.far = 50f

        // TODO all temporary! we need to load car name, etc, from YAML (or cmd line)
        // source: https://github.com/mgsx-dev/gdx-gltf/blob/master/demo/core/src/net/mgsx/gltf/examples/GLTFQuickStartExample.java

        val vehicle = ASSETS["assets/vehicles/rooster/whole_car.glb", SceneAsset::class.java]
        val wheel = ASSETS["assets/vehicles/rooster/wheel.glb", SceneAsset::class.java]
        val vehicleScene = Scene(vehicle.scene)
        wheelScene = Scene(wheel.scene)

        manager.addScene(vehicleScene)
        manager.addScene(wheelScene)
        manager.setCamera(camera)

        // setup camera
        vehicleScene.modelInstance.calculateBoundingBox(bbox)
        val carCentre = Vector3(bbox.centerX, bbox.centerY, bbox.centerZ)
        cameraController.target = carCentre
        cameraController.autoUpdate = false
        camera.lookAt(carCentre)
        cameraController.zoom(-2f)
        camera.rotateAround(carCentre, Vector3(0f, 0f, 1f), 90f)
        camera.rotateAround(carCentre, Vector3(0f, 1f, 0f), 180f)

        // setup light
        val light = DirectionalLightEx()
        light.direction.set(1f, -3f, 1f).nor()
        light.color.set(Color.WHITE)
        manager.environment.add(light)

        // setup quick IBL (image based lighting)
        val iblBuilder = IBLBuilder.createOutdoor(light)
        val environmentCubemap = iblBuilder.buildEnvMap(1024)
        val diffuseCubemap = iblBuilder.buildIrradianceMap(256)
        val specularCubemap = iblBuilder.buildRadianceMap(10)
        iblBuilder.dispose()

        // This texture is provided by the library, no need to have it in your assets.
        val brdfLUT = ASSETS["net/mgsx/gltf/shaders/brdfLUT.png", Texture::class.java]

        manager.setAmbientLight(1f)
        manager.environment.set(PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLUT))
        manager.environment.set(PBRCubemapAttribute.createSpecularEnv(specularCubemap))
        manager.environment.set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap))

        // setup skybox
        val skybox = SceneSkybox(environmentCubemap)
        manager.setSkyBox(skybox)

        // TODO create ground plane (preferably, a grid)

        multiplexer.addProcessor(cameraController)
        Gdx.input.inputProcessor = multiplexer
    }

    var target = Vector3()
    var progress = 0.0f

    override fun render(delta: Float) {
        clearScreen(0.0f, 0.0f, 0.0f, 1.0f)
        // update
        val carCentre = Vector3(bbox.centerX, bbox.centerY, bbox.centerZ)
        val baseLink = Vector3(bbox.centerX, 0f, bbox.centerZ)
        val frontLeft = baseLink.cpy().add(0.8109f, 0.475f, 0.230f)
        // TODO draw bounding box around car

        cameraController.update()
        // TODO clamp camera angle so it doesn't go around
        camera.update()

        wheelScene.modelInstance.transform.rotate(Vector3(0f, 0f, 1f), delta * -1000f)
        wheelScene.modelInstance.calculateTransforms()

        // render
        manager.update(delta)
        manager.render()

        shapeRender.color = Color.RED
        shapeRender.begin(ShapeRenderer.ShapeType.Filled)
        val baseLinkScreen = camera.project(baseLink.cpy())
        shapeRender.circle(baseLinkScreen.x, baseLinkScreen.y, 8f)
        val frontLeftScreen = camera.project(frontLeft.cpy())
        shapeRender.circle(frontLeftScreen.x, frontLeftScreen.y, 8f)

        // TODO draw bounding box for car (wireframe style)
        shapeRender.end()

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        manager.updateViewport(width.toFloat(), height.toFloat())
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
    }

    override fun dispose() {
        // no main menu, we just quit after this, so we can dispose the assets
        ASSETS.dispose()
        shapeRender.dispose()
    }
}