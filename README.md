# simple-demo

## Introduction
XYZ game engine basic demo.

Uses an FPS camera with basic functions to walk around a simple environment

Features:

* FPS camera
* SOLID_STATIC collision type entities (ie. the level)
* SWEPT_SPHERE collision type entites (ie. the player)
* NONE collision type entities (ie. the moving boxes)
* SweepSphereInAABBHandler
* Simple HUD demo
* Adding and removing NONE collision type entities dinamically from the game state
* Enabling and disabling graphic AABB debug on NONE collision type entities

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
