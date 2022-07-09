/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.common.utils

import com.badlogic.gdx.Gdx
import com.esotericsoftware.yamlbeans.YamlReader

object Utils {
    /**
     * Generic method to read YAML config
     * @param path path to load YAML from
     * @param T type of object to deserialise YAML document to
     * @param destination variable to store YAML in (TODO will this work with java, we need a pointer?)
     */
    private inline fun <reified T> readYaml(path: String, destination: T) {
        val configFile = Gdx.files.local(path)
        // FIXME
        //destination = YamlReader(configFile.readString()).read(T::class.java)
    }
}