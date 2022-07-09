# Vehicle definition
Gazilla supports highly flexible, yet (hopefully) easy to understand vehicle definitions. This allows
you to rapidly prototype different vehicle and sensor configurations.

A vehicle is defined by the configuration parameters in the vehicle.yaml file, and some 3D models to 
render the car on the client side.

## YAML config
### Vehicle dynamics
Gazilla supports pluggable vehicle dynamics. In the vehicle.yaml config, you can define what
VD model you want the car to use, and the associated parameters.

## 3D assets
All 3D assets in Gazilla must be in the glTF 2.0 binary format, i.e. *.glb files. You can export
these easily from Blender.

Here's a list of the required assets to make a vehicle, which must be named exactly as is below:

- whole_car.glb: a textured model of the whole car, _without_ the wheels
- wheel.glb: a textured model of a single wheel of the car

## Additional notes
The 3D assets for _all_ vehicles will be automatically loaded by the Gazilla client on launch,
so you don't have to do anything else to get the vehicle in.