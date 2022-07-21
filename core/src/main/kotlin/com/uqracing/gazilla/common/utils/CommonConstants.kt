/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.common.utils

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.util.ListReferenceResolver
import com.uqracing.gazilla.common.ecs.TransformComponent
import com.uqracing.gazilla.common.ecs.UUIDComponent
import com.uqracing.gazilla.common.network.NetworkEntity

/**
 * Different entity types in the simulation
 */
enum class EntityType {
    UNKNOWN,
    /** The AV body */
    VEHICLE,
    /** AV front left wheel */
    WHEEL_FL,
    /** AV front right wheel */
    WHEEL_FR,
    /** AV back left wheel */
    WHEEL_BL,
    /** AV back right wheel */
    WHEEL_BR,
    /** Blue cone (left) */
    CONE_BLUE,
    /** Yellow cone (right) */
    CONE_YELLOW,
}

/**
 * Where a transform is relative to.
 */
enum class TransformParent {
    /** Transform is relative to the origin (0,0,0). Default. */
    ORIGIN,
    /** Transform is relative to the centre of the vehicle's bounding box. Useful for wheels. */
    VEHICLE
}

enum class DrawCommandType {
    UNKNOWN,

    /** Clear all drawn shapes */
    CLEAR_DISPLAY,

    // 2D shapes (draw to screen directly)
    DRAW_POINT_2D,
    DRAW_LINE_2D,
    DRAW_TEXT_2D,
    DRAW_RECT,

    // 3D shapes (draw to world)
    DRAW_POINT_3D,
    DRAW_LINE_3D,
    DRAW_TEXT_3D,
    DRAW_CUBE_WIREFRAME,
    DRAW_CUBE_FILLED,
    DRAW_PLANE,
    DRAW_AXIS,
    DRAW_SPHERE,
}

enum class SerialisationMethod {
    JSON,
    MESSAGEPACK
}

/**
 * Shared client/server Kryo instance. Registration is not required (see inline comment).
 */
val KRYO = Kryo(ListReferenceResolver()).apply {
    // adds a few more bytes per message, and possibly impacts serialisation performance negatively, but:
    // a) we can gzip/zstd it if we want
    // b) we end up writing out a huge amount of classes, so it saves quite a lengthy and problematic registration process
    isRegistrationRequired = false
}

/**
 * Instance of common config, shared between client and server code
 */
var COMMON_CONFIG = CommonConfig()