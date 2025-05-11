package Labyrinthe;

import java.util.*;

public class PerfectMaze extends Maze{


    protected Random random;

    public PerfectMaze(int size_x, int size_y, int seed, String generation_mode) {
        super(size_x, size_y, seed, generation_mode);
        this.random = new Random(seed);
    }

    @Override
    public void generateMaze() {
        System.out.println("🔧 Generating perfect maze (DFS)...");
        node_array = new Node[answer_sizeX][answer_sizeY];
    }

    // algo dfs à coder pour la generation
}
