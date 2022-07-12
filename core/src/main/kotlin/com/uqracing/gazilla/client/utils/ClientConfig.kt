/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.client.utils

/**
 * Client config options from the YAML
 */
data class ClientConfig (
    var msaaSamples: Int = 0,
    var farClipDist: Float = 0f,
)