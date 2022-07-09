/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.common

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.util.ListReferenceResolver
import com.uqracing.gazilla.common.network.NetworkEntity

/**
 * Shared client/server Kryo instance
 */
val KRYO = Kryo(ListReferenceResolver()).apply {
    register(Vector2::class.java)
    register(Vector3::class.java)
    register(Quaternion::class.java)
    register(Matrix4::class.java)
    register(TransformComponent::class.java)
    register(NetworkEntity::class.java)
    register(ArrayList::class.java)
}

/**
 * Different entity types in the simulation
 */
enum class EntityType {
    UNKNOWN,
    /** The AV */
    VEHICLE,
    /** Blue cone (left) */
    CONE_BLUE,
    /** Yellow cone (right) */
    CONE_YELLOW,
}