/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.server.utils

data class ServerGeneralConfig(
    var updateRate: Int = 0,
)

data class ServerNetworkConfig(
    var port: Int = 0
)

data class ROSBridgeConfig(
    var enabled: Boolean = false,
    var port: Int = 0,
)

data class ServerConfig(
    var server: ServerGeneralConfig = ServerGeneralConfig(),
    var network: ServerNetworkConfig = ServerNetworkConfig(),
    var rosbridge: ROSBridgeConfig = ROSBridgeConfig(),
    // TODO which map is being used! and a format for storing maps!! (YAML?)
)