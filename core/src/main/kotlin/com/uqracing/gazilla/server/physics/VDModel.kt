package com.uqracing.gazilla.server.physics

import com.badlogic.ashley.core.Engine

/**
 * Parent class for all vehicle dynamics models. Vehicle models are pluggable and tie in with
 * Ashley, the entity system.
 *
 * A vehicle model has its own internal state, which it is allowed to preserve between calls to
 * update(). After update, the vehicle model should set the position, orientation and (if supported)
 * wheel speeds for each wheel. It does this by interacting with the Ashley ECS.
 */
interface VDModel {
    /**
     * Updates the vehicle model. The updated vehicle state depends on the model, but the
     * resulting vehicle transformation (3D position & rotation) should be applied to the vehicle
     * entity in Ashley, where it can be extracted later and sent over network.
     * TODO should we serialise the entire Ashley Engine and send it over?
     * @param delta delta time in seconds
     * @param engine instance of the Ashley ECS engine
     */
    fun update(delta: Double, engine: Engine)
}