package com.uqracing.gazilla.server.utils

data class VehicleConfigMetadata(
    var name: String = "",
    var year: Int = 0,
    var author: String = "",
    var copyright: String = "",
)

/**
 * Part of vehhicle configuration that is deserialised from YAML, this is the VD part
 */
data class DynamicsConfig(var model: String = "")

/**
 * Vehicle config JavaBeans that is deserialised from YAML
 */
data class VehicleConfig(
    var metadata: VehicleConfigMetadata = VehicleConfigMetadata(),
    var dynamics: DynamicsConfig = DynamicsConfig()
)