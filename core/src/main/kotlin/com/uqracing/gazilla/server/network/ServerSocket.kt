package com.uqracing.gazilla.server.network

import com.uqracing.gazilla.common.NetworkPacket
import org.tinylog.kotlin.Logger
import org.zeromq.SocketType
import org.zeromq.ZContext
import java.util.concurrent.LinkedBlockingQueue

/**
 * Socket for the Gazilla server to send data to the Gazilla client.
 * The Gazilla client will have its own ClientSocket.
 */
class ServerSocket : Thread() {
    private val context = ZContext()
    private val socket = context.createSocket(SocketType.PUSH)
    /** Push network packets onto this queue for them to be sent to the Gazilla client */
    val queue = LinkedBlockingQueue<NetworkPacket>()

    // TODO Kryo serialiser

    init {
        // TODO load server port from YAML
        val port = 41803
        if (!socket.bind("tcp://*:$port")) {
            Logger.error("Unable to bind ZMQ ServerSocket on port $port")
        }
    }

    override fun run() {
        try {
            val packet = queue.take()
        } catch (e: InterruptedException) {
            Logger.debug("ServerSocket interrupted")
            context.destroy()
            return
        }
    }
}