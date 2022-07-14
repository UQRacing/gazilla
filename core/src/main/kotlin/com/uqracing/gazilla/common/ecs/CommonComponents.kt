/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.common.ecs

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import com.uqracing.gazilla.common.utils.EntityType
import com.uqracing.gazilla.common.utils.TransformParent

// Components common to client and server (typically things that are sent across the network)

/**
 * Represents a 3D transformation of a model
 */
data class TransformComponent(
    var position: Vector3 = Vector3(),
    var orientation: Quaternion = Quaternion(),
    var parent: TransformParent = TransformParent.ORIGIN,
    @Transient val transform: Matrix4 = Matrix4(),
) : Component, java.io.Serializable {
    /**
     * Calculates the transform matrix in place from the class' position and orientation.
     * **Important: Does not factor in parent. You must do this yourself.**
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
    var entityType: EntityType = EntityType.UNKNOWN
) : Component, java.io.Serializable