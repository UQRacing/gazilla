/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.client.ecs

/**
 * This system receives packets over the network, and applies them to the entities in the ECS.
 * TODO maybe this shouldn't actually be a system? since there's no need for it to update every frame
 */
class DeserialiserSystem {
}