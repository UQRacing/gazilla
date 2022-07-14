# ROS bridge
**Use this: https://wiki.ros.org/rosbridge_suite**

The rest of the car uses ROS Noetic, so it's important Gazilla can support ROS 1 as well. In the
future, we may suppport ROS 2 as well.

Ideally, we would handle the ROS code from inside Kotlin. While this is possible, using the ancient
and unmaintained rosjava, it's somewhat difficult because rosjava is extremely scarcely documented
and isn't even available on Maven (afaik).

Instead, what we do is use roscpp and run an actual ROS node. This will be in the gazilla-ros
package, which is optional, and also provides launch scripts for the Gazilla client & server.

What the ROS bridge does is receive ROS messages and translate them into equivalent Protocol Buffer
messages, which are sent over a TCP socket to the Gazilla server. Gazilla decodes the Protocol Buffers 
and interprets them, for example, to send a control signal to the car. It can also do the reverse:
the Gazilla server sends a Protocol Buffers message, e.g. the state of the vehicle, to the ROS bridge,
which converts it into a ROS message and is relayed to the rest of the car.

This may introduce some latency, but I'm hoping it'll be alright.

