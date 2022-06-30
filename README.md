# Gazilla
Gazilla stands for "Gazebo Killer", and is an experimental simulator targeting Formula SAE autonomous vehicles.

The goal of the project is to produce a reliable and performant FSAE AV simulator that supports heavy customisation
(e.g. pluggable vehicle dynamics models, graphics, etc), while remaining easy to understand. I'm also aiming to maintain
ROS compatibility - but in an "add-on" format, such that it's not required if not desired.

The project is written in Kotlin using libGDX as a rendering framework. There will be a C++ ROS 1 bridge, so that
the project can communicate with the rest of the car.

Author: Matt Young (m.young2@uqconnect.edu.au)

## Features
- TODO

## Gradle
This project uses [Gradle](http://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

## Licence
Code: Mozilla Public Licence v2.0

3D models: CC-BY-SA 4.0 (see assets/vehicles/rooster for more info)