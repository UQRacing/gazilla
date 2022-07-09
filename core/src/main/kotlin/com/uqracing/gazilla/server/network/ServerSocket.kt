/*
 * Copyright (c) 2022 Matt Young (UQ Racing Formula SAE Team).
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.uqracing.gazilla.server.network

import org.tinylog.kotlin.Logger
import org.zeromq.SocketType
import org.zeromq.ZContext

/**
 * Socket for the Gazilla server to send data to the Gazilla client.
 * The Gazilla client will have its own ClientSocket.
 */
class ServerSocket(port: Int) {
    private val context = ZContext()
    private val socket = context.createSocket(SocketType.PUSH)

    init {
        if (!socket.bind("tcp://*:$port")) {
            Logger.error("Unable to bind ZMQ socket to port $port")
        }
    }

    /**
     * Transmits the given byte array down a zeromq socket to the Gazilla client.
     * @param data byte array to transmit
     */
    fun transmit(data: ByteArray) {
        socket.send(data)
    }
}