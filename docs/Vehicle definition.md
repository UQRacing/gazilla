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
**Defining your vehicle**

All 3D assets in Gazilla must be in the glTF 2.0 binary format, i.e. *.glb files. You can export
these easily from Blender.

Here's a list of the required assets to make a vehicle, which must be named exactly as is below:

- whole_car.glb: a textured model of the whole car, _without_ the wheels
- wheel.glb: a textured model of a single wheel of the car

**Modifying the environment**

You may also wish to change the environment the car is rendered in. Be warned, this is a tedious process, and is documented
[here](https://github.com/mgsx-dev/gdx-gltf/blob/master/docs/IBL.md).

I'll be starting with a cubemap for this, but you can also use an HDR (which is easier).

1. Download your cubemap.
2. Turn your cubemap into a face list if it's packed, or rename the face list to the px, py, nx, ny, pz, nz format if
it's unpacked. Currently must be JPEG. This is pretty annoying, a good way I found of doing this is using 
[this Python script](https://pypi.org/project/cubemap-splitter/) (WARNING: this script intentionally messes with your
numpy version, be _extremely_ careful!) and then renaming the files based on the table you can find in msgx's gdx-gltf
file `EnvrionmentUtil.java`.
3. Download cmftStudio.
4. Convert your cubemap to TGA format (cmftStudio only loads TGA??!), load it into cmftStudio, and then hit the process 
cubemap button to generate the radiance and irradiance maps, check they're correct, and export them using the faces list
option. You may wish to save the project as well. It might be easier to use the command line tool for this, I just used
the GUI but its pretty bad.
5. Grab the highest resolution "pmrem" (radiance map) and "iem" (irradiance map), put them into a folder, rename them
to the correct cubemap format (px, py, etc), and convert them from TGA to JPEG.

Hints:

- A good way to convert image file formats is with ImageMagick: `mogrify -format tga cubemap.jpg` converts cubemap.jpg
to cubemap.tga. BE WARNED: JPEG to TGA seems to flip the image? Use GIMP to unflip it.
- pmrem files/radiance map are colour; iem/irradiance files are somewhat greyscale looking
- Avoid doing this lol it's really annoying.


## Additional notes
The 3D assets for _all_ vehicles will be automatically loaded by the Gazilla client on launch,
so you don't have to do anything else to get the vehicle in.