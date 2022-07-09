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
 * Indicator component for entities that should be transported over the network, i.e. those
 * entities which will be serialised
 */
class TransportIndicatorComponent : Component