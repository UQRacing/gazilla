package com.uqracing.gazilla.server.utils

data class ServerGeneralConfig(
    var updateRate: Int = 0
)

data class ServerNetworkConfig(
    var port: Int = 0
)

data class ROSBridgeConfig(
    var enabled: Boolean = false,
    var port: Int = 0,
)

data class ServerConfig(
    var server: ServerGeneralConfig = ServerGeneralConfig(),
    var network: ServerNetworkConfig = ServerNetworkConfig(),
    var rosbridge: ROSBridgeConfig = ROSBridgeConfig(),
)