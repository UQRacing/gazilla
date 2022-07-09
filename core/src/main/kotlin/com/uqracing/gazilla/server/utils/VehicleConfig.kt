/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.server.utils

data class VehicleConfigMetadata(
    var name: String = "",
    var year: Int = 0,
    var author: String = "",
    var copyright: String = "",
)

/** Generic interface for all vehicle configuration */
interface VDConfig

/** Required for a default instance of VDConfig in the VehicleConfig class */
class NullModelConfig : VDConfig

/** Configuration for [com.uqracing.gazilla.server.physics.FssimModel] */
data class FssimModelConfig(
    var mass: Float = 0.0f,
) : VDConfig

/**
 * Vehicle config JavaBeans that is deserialised from YAML
 */
data class VehicleConfig(
    var metadata: VehicleConfigMetadata = VehicleConfigMetadata(),
    // pass the generic VDConfig interface here, we can use
    var dynamics: VDConfig = NullModelConfig(),
)