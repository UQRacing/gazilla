/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.server.physics

import com.badlogic.ashley.core.Engine

/*
 * AMZ-Driverless
 * Copyright (c) 2018 Authors:
 *   - Juraj Kabzan <kabzanj@gmail.com>
 *   - Miguel de la Iglesia Valls <dmiguel@ethz.ch>
 *   - Manuel Dangel <mdangel@student.ethz.ch>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Additional authors/information:
 * @author Riley Bowyer (riley.d.bowyer@gmail.com)
 * @date February 2021
 */

private data class FssimState (
    val x: Double = 0.0,
    val y: Double = 0.0,
    val theta: Double = 0.0,
    val v_x: Double = 0.0,
    val v_y: Double = 0.0,
    val v_theta: Double = 0.0,
    val a_x: Double = 0.0,
    val a_y: Double = 0.0,
    val a_theta: Double = 0.0,
)

/**
 * Vehicle model ported from fssim, under fssim/fssim_gazebo_plugins/fssim_gazebo_racecar
 */
class FssimModel : VDModel {
    /**
     * Calculate the downforce provided by the vehicle aero.
     * @param state Current vehicle state
     */
    private fun getFdown(state: FssimState): Double {
        // TODO vehicle config struct, use the downforce parameter
        return 0.0 * state.v_x * state.v_x
    }

    /**
     * Calculate the vehicle normal force.
     * @param x The current vehicle state
     * @return Double representing the current normal force
     */
    private fun getNormalForce(state: FssimState) {
        // TODO
        //  return params.gravity * params.mass * getFdown(state)
    }

    override fun update(delta: Double, engine: Engine) {

    }
}