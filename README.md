# simple-demo

## Introduction

This project showcase assorted features of the XYZ game engine.

Among other things it showcase:

* Entities
* Cameras
* Game States
* Input System
* Lights and Shadows
* Sound
* Terrains
* Skybox
* Water
* Particle systems
* Translucent entities
* 2D elements (Text, Images, Animations)
* Ray casting
* Swept sphere in AABB
* 3D look at
* Post processing pipeline
* Crush detection

## Controls

wasd or arrows: forward, strafe left, backward, strafe right  
mouse movement: aim  
z: crouch  
space: jump  
mouse wheel: move camera ahead or behind the character swept sphere  
shiftL + shiftR: restore camera position  

## Run from eclipse

[Download lwjgl native libs](https://sourceforge.net/projects/java-game-lib/files/Official%20Releases/LWJGL%202.9.1/lwjgl-2.9.1.zip/download)

Right click on XYZDemoMainGameLoop, and go to Run As -> Run Configurations ...  
Go to Arguments -> VM arguments: and type "-Djava.library.path=<path to lwjgl native libs>" ie. "-Djava.library.path=/opt/lwjgl/lwjgl-2.9.1/native/linux"

## Run from command line

[Download lwjgl native libs](https://sourceforge.net/projects/java-game-lib/files/Official%20Releases/LWJGL%202.9.1/lwjgl-2.9.1.zip/download)

Point library.path in pom.xml to the lwjgl native libs

mvn clean install exec:exec

