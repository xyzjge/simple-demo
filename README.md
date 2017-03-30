# simple-demo

## Introduction
XYZ game engine basic demo.
Uses an FPS camera with basic functions to walk around a simple environment

## Controls

wasd or arrows: forward, strafe left, backward, strafe right  
Mouse movement: aim  
z: crouch  
space: jump  

## lwjgl native lib configuration

[Download lwjgl native libs](https://sourceforge.net/projects/java-game-lib/files/Official%20Releases/LWJGL%202.9.1/lwjgl-2.9.1.zip/download)

In eclipse, Right click on XYZDemoMainGameLoop, and go to Run As -> Run Configurations ...  
Go to Arguments -> VM arguments: and type "-Djava.library.path=<path to lwjgl native libs>" ie. "-Djava.library.path=/opt/lwjgl/lwjgl-2.9.1/native/linux"

## Run from command line

Point library.path in pom.xml to the lwjgl native libs

mvn exec:exec
