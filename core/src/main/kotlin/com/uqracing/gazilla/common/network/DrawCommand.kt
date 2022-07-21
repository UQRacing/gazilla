/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.common.network

import com.badlogic.gdx.graphics.Color
import com.uqracing.gazilla.common.utils.DrawCommandType

/**
 * Request for the client to draw something (or maybe clear the canvas). Useful for debugging algorithms.
 * The GazillaServer will send a list of these to the client to instruct them to draw.
 * We'll also support ROS integration with this as well.
 */
data class DrawCommand(
    /** type of object to draw, or clear drawn objects */
    val type: DrawCommandType = DrawCommandType.UNKNOWN,
    /** Layer name. Multiple objects can belong to a layer. Used to toggle on/off different visualisers in Gazilla client. */
    val layer: String = "default",
    /** colour, if applicable */
    val colour: Color? = null,
) : java.io.Serializable