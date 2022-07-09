/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.common

/**
 * fssim track YAML format, that can be loaded into Gazilla
 */
data class Track (
    var cones_left: Array<Array<Double>> = arrayOf(),
    var cones_right: Array<Array<Double>> = arrayOf(),
    var cones_orange: Array<Array<Double>> = arrayOf(),
    var cones_orange_big: Array<Array<Double>> = arrayOf(),
    var tk_device: Array<Array<Double>> = arrayOf(),
    // not actually sure what this is :thinking:
    var starting_pose_front_wing: Array<Double> = arrayOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Track

        if (!cones_left.contentDeepEquals(other.cones_left)) return false
        if (!cones_right.contentDeepEquals(other.cones_right)) return false
        if (!cones_orange.contentDeepEquals(other.cones_orange)) return false
        if (!cones_orange_big.contentDeepEquals(other.cones_orange_big)) return false
        if (!tk_device.contentDeepEquals(other.tk_device)) return false
        if (!starting_pose_front_wing.contentEquals(other.starting_pose_front_wing)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cones_left.contentDeepHashCode()
        result = 31 * result + cones_right.contentDeepHashCode()
        result = 31 * result + cones_orange.contentDeepHashCode()
        result = 31 * result + cones_orange_big.contentDeepHashCode()
        result = 31 * result + tk_device.contentDeepHashCode()
        result = 31 * result + starting_pose_front_wing.contentHashCode()
        return result
    }
}