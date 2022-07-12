/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.client.utils

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver

/**
 * Version history:
 * v0.0.1: initial development version
 */
const val CLIENT_VERSION = "0.0.1"

/**
 * Global shared assets resource
 */
val ASSETS = AssetManager(LocalFileHandleResolver())

/**
 * Global client config
 */
var CLIENT_CONFIG = ClientConfig()