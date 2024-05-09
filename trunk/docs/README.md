# Depth-first search in directed graphs

This a sample Java program that demonstrates how to perform Depth-first search (DFS) in directed graphs. The program is constructed using multiple Java files, and the program compilation and execution are automated using an Ant script.

## Files

- **Graph.java**: This file contains two main functionalities:
    - Generating a graph.
        - Generating a graph involves encoding the graph's nodes and edges within the method. You may need to manually modify the code to encode a different graph or adjust the method to accept user input.
    - Running the Depth-first Search (DFS) algorithm on the generated graph.

- **Node.java**: This file is a data structure utilized by `Graph.java` to construct the graph.

- **Runner.java**: This is the main class responsible for executing the program.

- **build.xml**: This is the Ant script used to automate the compilation and execution of the program.

## Requirements

## Requirements

- **Java JDK**: The program requires Java JDK to compile and run. Make sure you have it installed on your machine.
- **Apache Ant**: The program is automated using an Ant script, so you need to have Apache Ant installed.
    - To install Apache Ant, follow the instructions in the [official Apache Ant installation guide](https://ant.apache.org/manual/install.html).
    - For a tutorial on getting started with Apache Ant, check out the [Hello World with Apache Ant guide](https://ant.apache.org/manual/tutorial-HelloWorldWithAnt.html).

## Installation

1. Clone this repository to your local machine:

    ```bash
    git clone <repository-url>
    ```

2. Navigate to the project directory:

    ```bash
    cd <repository-name>
    ```

3. Ensure you have the required dependencies (Java JDK and Apache Ant) installed.

## Usage

To execute the program, simply run the following command in the terminal/console from the project directory:

```bash
ant
