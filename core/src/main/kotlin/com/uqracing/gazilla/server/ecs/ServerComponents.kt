/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.server.ecs

import com.badlogic.ashley.core.Component
import com.uqracing.gazilla.server.physics.VDModel

/**
 * Component which holds the chosen vehicle dynamics model implementation
 */
class PhysicsComponent : Component {
    lateinit var model: VDModel
}

/**
 * Indicator component for entities that should be transported over the network, i.e. those
 * entities which will be serialised
 */
class TransportIndicatorComponent : Component