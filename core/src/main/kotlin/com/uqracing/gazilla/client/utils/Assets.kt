/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.client.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import net.mgsx.gltf.loaders.glb.GLBAssetLoader
import net.mgsx.gltf.loaders.gltf.GLTFAssetLoader
import net.mgsx.gltf.scene3d.scene.SceneAsset
import org.tinylog.kotlin.Logger

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

        assets.load("assets/uiskin/cloud-form-ui.json", Skin::class.java)
        assets.load("net/mgsx/gltf/shaders/brdfLUT.png", Texture::class.java)

        // load vehicle 3D models
        loadFromDir(assets, Gdx.files.internal("assets/vehicles"), SceneAsset::class.java)
        // load background cubemap, currently only using background_miramar
        loadFromDir(assets, Gdx.files.internal("assets/environment/background_miramar/"), Texture::class.java)
    }
}