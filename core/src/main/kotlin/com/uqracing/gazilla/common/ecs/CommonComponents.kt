/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.common

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3

// Components common to client and server (typically things that are sent across the network)

/**
 * Represents a 3D transformation of a model
 */
data class TransformComponent(
    val position: Vector3 = Vector3(),
    val orientation: Quaternion = Quaternion(),
    @Transient val transform: Matrix4 = Matrix4(),
) : Component, java.io.Serializable {
    /**
     * Calculates the transform matrix in place from the class' position and orientation
     */
    fun calculateTransformMatrix() {
        transform.set(position, orientation)
    }
}

/**
 * Represents each entity's type in the simulation, so they can be identified in the sim and
 * over the network.
 */
data class EntityTypeComponent(
    val type: EntityType = EntityType.UNKNOWN
) : Component, java.io.Serializable