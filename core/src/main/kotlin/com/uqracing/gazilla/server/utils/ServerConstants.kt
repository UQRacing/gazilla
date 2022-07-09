/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.server.utils

import com.esotericsoftware.kryo.Kryo

/**
 * Version history:
 * v0.0.1: initial version
 */
const val SERVER_VERSION = "0.0.1"

/**
 * Global vehicle config struct for server
 */
var VEHICLE_CONFIG = VehicleConfig()

/**
 * Global server config struct
 */
var SERVER_CONFIG = ServerConfig()