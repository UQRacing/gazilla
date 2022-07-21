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
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

object Utils {
//    /** Private object used for YAML serialisation */
//    val yamlMapper = ObjectMapper(YAMLFactory()).apply {
//        findAndRegisterModules()
//    }

    /**
     * Generic method to read YAML config
     * @param path path to load YAML from
     * @param T type of object to deserialise YAML document to
     * @return deserialised YAML
     */
    inline fun <reified T> readYaml(path: String): T {
        val configFile = Gdx.files.local(path)
        return YamlReader(configFile.readString()).read(T::class.java)
        //return yamlMapper.readValue(configFile.reader(), T::class.java)
    }
}