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
import com.fasterxml.jackson.databind.ObjectMapper
import com.uqracing.gazilla.common.network.NetworkEntity
import com.uqracing.gazilla.common.ecs.TransformComponent
import com.uqracing.gazilla.common.network.ServerToClientPacket
import com.uqracing.gazilla.common.utils.KRYO
import com.uqracing.gazilla.common.utils.SerialisationMethod
import ktx.ashley.mapperFor
import org.msgpack.jackson.dataformat.MessagePackFactory
import java.nio.charset.Charset
import java.util.HexFormat

/**
 * Ashley system which serialises the relevant parts of each entity for transmission over
 * network. Sends a NetworkPacket.
 *
 * SerialiserSystem **MUST** be the last system to be updated.
 */
class SerialiserSystem() : EntitySystem() {
    private val output = Output(1024)
    private val transportFamily = Family.one(TransportIndicatorComponent::class.java).get()
    private val tm = mapperFor<TransformComponent>()
    // standard network mapper
    private val mapper = ObjectMapper(MessagePackFactory())
    // outputs JSON to debug the network protocol easier
    private val debugMapper = ObjectMapper()

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
        // construct packet, TODO send draw commands
        val packet = ServerToClientPacket(networkEntities, listOf())

        // serialise packet to byte buffer and store the truncated buffer in the class
        KRYO.writeObject(output, packet)
        output.flush()
        data = output.toBytes()

        val serialised = mapper.writeValueAsBytes(packet)
        val hex = HexFormat.ofDelimiter(" ").formatHex(serialised).uppercase()
        println(hex)
    }
}