package com.uqracing.gazilla.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import net.mgsx.gltf.loaders.glb.GLBAssetLoader
import net.mgsx.gltf.loaders.gltf.GLTFAssetLoader
import net.mgsx.gltf.scene3d.scene.SceneAsset
import org.tinylog.kotlin.Logger

/**
 * Global shared assets resource
 */
val ASSETS = AssetManager(LocalFileHandleResolver())

object Assets {
    private val allowedExtensions = listOf("glb", "atlas", "png", "jpg", "fnt")

    private fun <T> loadFromDir(assets: AssetManager, path: FileHandle, clazz: Class<T>) {
        val files = path.list()

        for (file in files) {
            if (file.isDirectory) {
                loadFromDir(assets, file, clazz)
            } else if (file.extension() in allowedExtensions) {
                Logger.debug("Loading asset: ${file.path()}")
                assets.load(file.path(), clazz)
            }
        }
    }

    /**
     * Loads all assets required by Gazilla into the specified AssetManager
     */
    fun load(assets: AssetManager) {
        assets.setLoader(SceneAsset::class.java, ".gltf", GLTFAssetLoader())
        assets.setLoader(SceneAsset::class.java, ".glb", GLBAssetLoader())

        // load UI skin
        assets.load("assets/uiskin/cloud-form-ui.json", Skin::class.java)

        // load vehicle 3D models
        loadFromDir(assets, Gdx.files.internal("assets/vehicles"), SceneAsset::class.java)
    }
}