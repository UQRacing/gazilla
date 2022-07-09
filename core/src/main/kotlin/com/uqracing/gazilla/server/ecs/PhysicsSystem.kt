/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.server.ecs

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.uqracing.gazilla.server.utils.PhysicsComponent

/**
 * This system updates the vehicle model for all vehicles in the world
 */
class PhysicsSystem : EntitySystem() {
    private val vehicles = Family.one(PhysicsComponent::class.java).get()
    private val pm = ComponentMapper.getFor(PhysicsComponent::class.java)

    override fun update(deltaTime: Float) {
        val vehicles = engine.getEntitiesFor(vehicles)
        for (vehicle in vehicles) {
            pm.get(vehicle).model.update(deltaTime.toDouble(), engine)
        }
    }
}