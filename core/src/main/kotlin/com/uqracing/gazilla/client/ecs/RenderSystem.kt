/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.client.ecs

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute
import com.badlogic.gdx.graphics.g3d.attributes.SpotLightsAttribute
import com.badlogic.gdx.utils.Disposable
import net.mgsx.gltf.scene3d.scene.SceneRenderableSorter
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider
import net.mgsx.gltf.scene3d.utils.EnvironmentCache

/**
 * System which handles rendering entities with a SceneComponent. Heavily borrowing code from gdx-gltf
 * [net.mgsx.gltf.scene3d.scene.SceneManager]
 */
class RenderSystem : IteratingSystem(Family.one(SceneComponent::class.java).get()), Disposable {
    private val renderableSorter = SceneRenderableSorter()
    val camera = PerspectiveCamera()
    val environment = Environment()
    val environmentCache = EnvironmentCache()
    val pointLights = PointLightsAttribute()
    val spotLights = SpotLightsAttribute()

    private val batch = ModelBatch(PBRShaderProvider.createDefault(0), renderableSorter)

    override fun processEntity(entity: Entity, deltaTime: Float) {

    }

    override fun dispose() {
        batch.dispose()
    }
}