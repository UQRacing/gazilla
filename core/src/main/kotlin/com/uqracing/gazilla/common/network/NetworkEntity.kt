/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.common.network

import com.uqracing.gazilla.common.TransformComponent

/**
 * Class for an entity that has been ripped out of the server's Ashley instance, will be serialised,
 * and sent over the network to the client.
 *
 * This class therefore contains only the necessary information for the client to render the entity.
 */
data class NetworkEntity (
    val transform: TransformComponent
): java.io.Serializable