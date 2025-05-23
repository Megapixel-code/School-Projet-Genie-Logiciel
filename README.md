# CYnapse - Maze Generator and Solver

## Overview

CYnapse is a Java application that allows users to generate and solve mazes. The project includes both a command-line interface (CLI) and a graphical user interface (GUI) using JavaFX. Users can create perfect or imperfect mazes, apply different solving algorithms, modify the maze manually, and save or load their work.

---

## Requirements

- Java 17
- JavaFX SDK compatible with Java 17 (for the GUI version)

---


## Main Features

### Maze Generation

- Users can define:
    - Maze dimensions (width and height)
    - A generation seed
    - The type: perfect or imperfect
- Two generation modes:
    - Full mode: the entire maze is generated and displayed at once
    - Step-by-step mode: generation is animated with optional speed control
- Perfect mazes have a unique path between any two cells (no cycles)
- Imperfect mazes may contain loops or inaccessible areas
- Generation uses algorithms like Kruskal and BFS (Breadth-First Search)

### Maze Solving

- Available after a maze is generated
- Solving algorithms implemented:
    - BFS (Breadth-First Search)
    - DFS (Depth-First Search)
    - Dijkstra
    - A*
    - Wall Follower (Left and Right)
- Two solving modes:
    - Full solving: solution displayed instantly
    - Step-by-step: animation of visited cells and pathfinding
- Display includes:
    - Grey cells for visited positions
    - White cells for the final path
    - Green cell for the start
    - Red cell for the end
    - Statistics: path length, number of visited cells, solve time

### Manual Modification

- Users can add or remove walls manually
- The solver updates accordingly, without recomputing everything

### Save and Load

- The maze can be saved to a file in the `/backup` directory
- A previously saved maze can be loaded and reused

---

## Interfaces

### Command-Line Interface (CLI)

- Main file: `MainInteract.java`
- Full-featured version of the application accessible via text input
- The user is guided through maze generation, solving, editing, saving, and loading
- Same logic and functionalities as the GUI version, but without graphical output

### Graphical Interface (GUI)

- Main file: `LabyrinthApp.java`
- Developed using JavaFX
- Interactive interface with:
    - Buttons, combo boxes, sliders
    - Visual feedback during generation and solving
    - Real-time display of visited cells, final path, and statistics

---

## How to Run

### CLI Version

Requires Java 17:  
(((((((((((((((((((((((((((((())))))))))))))))))))))))))))))

### JavaFX GUI
Requires Java 17 and a compatible JavaFX SDK:  
(((((((((((((((((((((((((((((())))))))))))))))))))))))))))))


---

## Project Structure

/src  
├── MainInteract.java  
├── LabyrinthApp.java  
├── Maze.java  
├── PerfectMaze.java  
├── ImperfectMaze.java  
├── ImperfectMazeKruskal.java  
├── Solver.java  
├── SolverSbS.java  
├── Node.java  
├── Edge.java  


---

## Authors

CY Tech – ING1 GI (2024–2025)  
Project developed by a team of 5 students.

---

## License

This project is intended for educational use only.

