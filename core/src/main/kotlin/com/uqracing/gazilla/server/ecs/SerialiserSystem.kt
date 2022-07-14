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
import com.esotericsoftware.kryo.io.Output
import com.uqracing.gazilla.common.network.NetworkEntity
import com.uqracing.gazilla.common.ecs.TransformComponent
import com.uqracing.gazilla.common.utils.KRYO
import ktx.ashley.mapperFor

/**
 * Ashley system which serialises the relevant parts of each entity for transmission over
 * network.
 *
 * SerialiserSystem **MUST** be the last system to be updated.
 */
class SerialiserSystem : EntitySystem() {
    private val output = Output(1024)
    private val transportFamily = Family.one(TransportIndicatorComponent::class.java).get()
    private val tm = mapperFor<TransformComponent>()

    /**
    * Serialised list of entities, since the last call to [update].
    * This is all the data that the client needs, so can be sent straight over network.
    */
    var data = ByteArray(1)

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
        output.reset()
        KRYO.writeObject(output, networkEntities)
        output.flush()
        data = output.toBytes()
    }
}