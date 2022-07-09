/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.server.ecs

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem

/**
 * This system updates the vehicle model for all vehicles in the world
 */
class PhysicsSystem : IteratingSystem(Family.one(PhysicsComponent::class.java).get()) {
    private val pm = ComponentMapper.getFor(PhysicsComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        // update vehicle physics
        pm.get(entity).model.update(deltaTime.toDouble(), engine)
    }
}