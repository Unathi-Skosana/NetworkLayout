# README

### Author: 19790457, Unathi Koketso Skosana

This repository will mainly host the second CS214 project, and the tutorials associated with the project.

### Execution

javac GraphLayout.java

java GraphLayout inputfile

### Command line arguments

The command line argument is the name of the file containing the input for the graph to be drawn. This holds the assumption that file name specified is a valid file and it contains valid input. Specifically the input is specified in the format "v -> w".

### Output
The program outputs a file name output.txt which contains the coordinates of the positions of the nodes in format
v -> (x, y), which means that the node number v is at position (x,y).

### Dependencies
JUnit

### Example
The following input produces the graph below.
        0 -> 9
        1 -> 0
        2 -> 0
        3 -> 5
        4 -> 9
        5 -> 4
        6 -> 8
        7 -> 5
        8 -> 4
        0 -> 9
        1 -> 0
        2 -> 0
        2 -> 0
        2 -> 0
        2 -> 0
        2 -> 0
        2 -> 0
        2 -> 0
        2 -> 0
        2 -> 0
        2 -> 0
        3 -> 5
        4 -> 9
        5 -> 4
        6 -> 8
        7 -> 5
        8 -> 4
        8 -> 4
        8 -> 4
        8 -> 4

![Alt text](NetworkLayout/BinaryTree.png?raw=true "One specific test case")
