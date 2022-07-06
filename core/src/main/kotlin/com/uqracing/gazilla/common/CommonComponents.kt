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
class TransformComponent : Component {
    val position = Vector3()
    val orientation = Quaternion()
    @Transient val transform = Matrix4()

    fun calculateTransformMatrix() {
        transform.set(position, orientation)
    }
}

/**
 * Component which holds a reference to a mgsx gltf ModelInstance
 */
class ModelComponent : Component {
    lateinit var instance: ModelInstance
}