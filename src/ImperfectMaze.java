import java.util.*;

/**
 * This class ImperfectMaze extends PerfectMaze and creates a maze
 * that is not perfect, meaning it can have some loops or extra walls.
 *
 * The constructor generates the maze using BFS method from PerfectMaze,
 * then randomly removes and adds some walls to make the maze imperfect.
 */

public class ImperfectMaze extends PerfectMaze {

    /**
     * Create an ImperfectMaze with given size, random seed, start and end points.
     *
     * It calls the superclass constructor to generate a perfect maze first,
     * then removes and adds some random walls to make the maze imperfect.
     * The number of walls to add and remove is calculated based on the maze size.
     *
     * @param x width of the maze
     * @param y height of the maze
     * @param seed seed for random generator
     * @param start coordinates of the start position
     * @param end coordinates of the end position
     */
    public ImperfectMaze(int x, int y, int seed, int[] start, int[] end) {
        super(x, y, seed, start, end);
        this.generateBFS();

        // Calculate the total number of cells
        int maxWalls = get_size()[0] * get_size()[1];

        // Define a range for modification
        int minModif = Math.max(1, maxWalls / 30);

        Random rng = get_rng();

        int toRemove = minModif + rng.nextInt(minModif + 1);
        int toAdd = minModif + rng.nextInt(minModif + 1);

        System.out.println("\u001B[33mSuppression aléatoire de " + toRemove + " murs.\u001B[0m");
        this.removeRandomWalls(toRemove);

        System.out.println("\u001B[33mAjout aléatoire de " + toAdd + " murs.\u001B[0m");
        this.addRandomWalls(toAdd);
    }
}