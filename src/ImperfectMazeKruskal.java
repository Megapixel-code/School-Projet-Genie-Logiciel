import java.util.*;

/**
 * This class ImperfectMazeKruskal extends PerfectMaze and creates an imperfect maze
 * using Kruskal's algorithm first to generate a perfect maze.
 *
 * After generating the perfect maze, it randomly removes and adds walls
 * to create loops and more complexity, so the maze is not perfect anymore.
 */

public class ImperfectMazeKruskal extends PerfectMaze {

    /**
     * Constructor to create an imperfect maze with Kruskal algorithm.
     *
     * It calls the superclass constructor to create maze basics,
     * then generate a perfect maze with Kruskal method,
     * and finally remove and add random walls.
     *
     * @param x width of the maze
     * @param y height of the maze
     * @param seed random seed for maze generation
     * @param start start point coordinates
     * @param end end point coordinates
     */

    public ImperfectMazeKruskal(int x, int y, int seed, int[] start, int[] end) {
        super(x, y, seed, start, end);

        // Generate perfect maze with Kruskal
        this.generateKruskal();

        int maxWalls = get_size()[0] * get_size()[1];

        Random rng = get_rng();

        int Modif = Math.max(1, maxWalls / 30);

        int toRemove = Modif + rng.nextInt(Modif + 1); // walls to remove = add cycles
        int toAdd = Modif + rng.nextInt(Modif + 1);    // walls to add to create an unconnected graph

        System.out.println("\u001B[33mSuppression aléatoire de " + toRemove + " murs.\u001B[0m");
        removeRandomWalls(toRemove);

        System.out.println("\u001B[33mAjout aléatoire de " + toAdd + " murs.\u001B[0m");
        addRandomWalls(toAdd);
    }
}