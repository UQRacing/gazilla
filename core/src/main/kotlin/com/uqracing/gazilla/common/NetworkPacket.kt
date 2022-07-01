package com.uqracing.gazilla.common

import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3

data class NetworkPacket(
    val vehiclePos: Vector3,
    val vehicleTransform: Quaternion
)