# README

### Execution

javac GraphLayout.java

java GraphLayout inputfile

### Command line arguments

The command line argument is the name of the file containing the input for the graph to be drawn. This holds the assumption that file name specified is a valid file and it contains valid input. Specifically the input is specified in the format "v -> w".


### Input generation

javac DigraphGenerator.java 

java DigraphGenerator V E type

e.g java DigraphGenerator 10 10 binaryTree

This will generate a digraph that has the structure of a binary tree with 10 vertices and 10 edges, input file will named binaryTree.txt and dumped into examples/generated_input/

### Input generation options

- binaryTree
- DAG
- rooted-in DAG
- rooted-out DAG
- rooted-in tree
- rooted-out tree
- tournament 

Note: Some options may generate cylic digraphs.

### Output

The program outputs a file name output.txt which contains the coordinates of the positions of the nodes in format
v -> (x, y), which means that the node number v is at position (x,y).

### Dependencies

JUnit

### Example

The following input produces the graph below.

    0 -> 4
    1 -> 6
    2 -> 9
    3 -> 6
    4 -> 7
    5 -> 9
    5 -> 0
    6 -> 4
    8 -> 5
    9 -> 0

![Alt text](rooted-in.png?raw=true "One specific test case")

### Help
Meanful suggestions would be appreciated, better yet pull in a request if you can improve the program.
