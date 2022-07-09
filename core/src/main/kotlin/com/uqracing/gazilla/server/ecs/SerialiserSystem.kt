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
import com.uqracing.gazilla.common.NetworkEntity
import com.uqracing.gazilla.common.TransformComponent
import com.uqracing.gazilla.common.TransportIndicatorComponent
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

/**
 * Ashley system which serialises the relevant parts of each entity for transmission over
 * network.
 *
 * SerialiserSystem **MUST** be the last system to be updated.
 *
 * TODO use Kryo instead of ObjectOutputStream
 */
class SerialiserSystem : EntitySystem() {
    private val stream = ByteArrayOutputStream(512)
    private val writer = ObjectOutputStream(stream)
    private val transportFamily = Family.one(TransportIndicatorComponent::class.java).get()
    private val tm = ComponentMapper.getFor(TransformComponent::class.java)

    /** Serialised list of entities, since the last call to [update], suitable for network transfer */
    var output = ByteArray(1)

    override fun update(deltaTime: Float) {
        val entities = engine.getEntitiesFor(transportFamily)
        val networkEntities = mutableListOf<NetworkEntity>()

        // iterate over each entity in the world and generate a NetworkEntity from it (which
        // just contains the necessary information for the client)
        for (entity in entities) {
            val transform = tm.get(entity)
            val networkEntity = NetworkEntity(transform)
            networkEntities.add(networkEntity)
        }

        // serialise the list of entities and store it in the output buffer
        stream.reset()
        writer.reset()
        writer.writeObject(networkEntities)
        output = stream.toByteArray()
    }


}