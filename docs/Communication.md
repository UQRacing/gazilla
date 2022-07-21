# Communication
Gazilla, like Gazebo, operates in a client-server architecture. This makes it possible, for
example, to run the Gazilla client on an ARM Mac for better performance, while running the Gazilla
Server inside an Ubuntu 20.04 VM for ROS Noetic compatibility. Many different configurations of this
sort are supported, see the README for more info.

## Ports
Gazilla client-server uses port 41803, the Gazilla ROS bridge uses port 41802.

## Serialisation format
See Serialisation.md, we will be changing the format.

## Gazilla client-server comms
Gazilla client-server communication is done using Kryo over a ZeroMQ socket.
This is lightweight and performant, and since both applications are Java, they use Kryo
which is a Java-internal serialisation framework.

## Gazilla ROS bridge comms
Communication with the C++ ROS bridge is done using Protocol Buffers over a ZeroMQ socket.

The ROS bridge receives ROS messages using ROS' XMLRPC, translates them into the equivalent
Gazilla Protobuf, and sends that to the Gazilla server using ZeroMQ.

Likewise, when the Gazilla server sends a message to the ROS bridge, it sends a Protobuf message
that is decoded and relayed as the appropriate ROS XMLRPC message by the bridge.
