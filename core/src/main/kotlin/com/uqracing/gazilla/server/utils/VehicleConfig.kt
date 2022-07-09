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