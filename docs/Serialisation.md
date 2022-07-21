# Serialisation
## Background
One of the most important things when designing a network protocol is to choose how data is serialised and deserialised
over the wire. This drastically affects performance and also how useful the protocol is to other people. 

For example, ROS 1 uses a custom serialisation framework instead of something more standard like XMLRPC or Protocol Buffers. 
This means that one cannot easily write a ROS client library: they have to read all the scattered serialisation format 
documentation, write a schema parser, emit boilerplate code, etc. All of this could have been avoided if they
used something more standard, which is we should want to do.

## Current format
As of writing, we are currently using Kryo, which is a very fast and nice format, but is JVM specific. If someone wanted, 
for example, to implement a client in C++ they would find it difficult.

One of the biggest features people seem to like about Gazilla is the idea that any client can be supported. Since the 
whole design goal of Gazilla is to be open and flexible, and people want custom clients, we should change to a more

## Alternative format evaluations
### What do we want?
- "Just works" in Kotlin: you throw objects at it, they appear in the stream
- Format should be mature, with many libraries available for encoding/decoding, especially for C and C++

### Evaluating formats
**Binary formats:**

- CBOR
  - Advantages: Good cross-language library support, has a wrapper for Jackson so we can write objects directly
  - Disadvantages: Not as well known as MessagePack

- MessagePack
  - Advantages: Good cross-language library support, also has a library for Jackson so we can write objects directly
  - Disadvantages:

- Protocol Buffers
  - Advantages: Excellent cross-language library support, basically can run anywhere. nanopb is a great C implementation.
  I'm also familiar with Protobuf.
  - Disadvantages: The schema is annoying, because we serialise a lot of data, and it has to be updated regularly.

- BSON
  - Advantages: None.
  - Disadvantages: Seems to be a MongoDB specific thing. Poor cross-language support.

- UBJSON
  - Advantages: None
  - Disadvantages: Bad library support, not updated, not commonly used.

- FlatBuffers
  - Advantages: Very fast, decent cross-language library support
  - Disadvantages: Requires a schema, API is annoying

**Text formats:**

- JSON
  - Advantages: GSON is a great library, you can throw anything at it. It's JSON, so anyone should be able to decode it.
  - Disadvantages: Inefficient encoding compared to binary formats
 
### The verdict
It's probably going to be MessagePack or CBOR, with optional JSON serialisation for debugging via GSON. So we will
have a pluggable serialisation framework.

**Final verdict:** 

MessagePack, because it supports more languages than CBOR and the C++ libraries appear better. That
being said, the Java implementation is rather crappy (I believe), but at least it should mostly go through Jackson, so
it should still be mostly ok.

That being said, if MessagePack causes issues, we'll just switch to CBOR.