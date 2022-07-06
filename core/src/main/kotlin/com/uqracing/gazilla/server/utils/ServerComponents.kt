package com.uqracing.gazilla.server.utils

import com.badlogic.ashley.core.Component
import com.uqracing.gazilla.server.physics.VDModel

/**
 * Component which holds the chosen vehicle dynamics model implementation
 */
class PhysicsComponent : Component {
    lateinit var model: VDModel
}