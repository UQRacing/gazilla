# ROS bridge
The rest of the car uses ROS Noetic, so it's important Gazilla can support ROS 1 as well. In the
future, we may suppport ROS 2 as well.

The ROS bridge essentially acts as a basic ROS client. It opens up a TCP connection to the 
ROS master, in the same way as the Python or C++ clients would do, and parses the XMLRPC stream
into Kotlin classes that the rest of Gazilla can understand. It's also capable of doing
the reverse: turning a Kotlin class into a suitable XMLRPC message and sending that back off
to the ROS master.

TODO rosjava???