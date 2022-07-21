/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.common.network

/**
 * Wrapper class to contain packets send by the server to the client. To be serialised with Kryo.
 */
data class ServerToClientPacket(
    val entities: List<NetworkEntity>,
    val drawCommands: List<DrawCommand>,
) : java.io.Serializable