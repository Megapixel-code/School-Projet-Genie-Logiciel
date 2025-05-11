package Labyrinthe;

import java.util.*;

// Labyrinthe Général
public abstract class Maze {
    /*
     * a maze is rectangular, has a seed for generating paths
     * has a node array, and a list of all connected edges
     */

    //Interactions / Informations

    protected static Scanner input = new Scanner(System.in);
    protected static int answer_seed = -1;
    protected static int answer_sizeX = -1;
    protected static int answer_sizeY = -1;
    protected static String answer_type;
    protected static String answer_generation;

    //Generation
    protected Node[][] node_array = new Node[answer_sizeX][answer_sizeY];;
    protected ArrayList<Edge> edge_list = new ArrayList<Edge>();

    public Maze(int size_x, int size_y, int seed, String generation_mode) {
        this.answer_sizeX = size_x;
        this.answer_sizeY = size_y;
        this.answer_seed = seed;
    }

    public static Maze initialize(){

        // SEED
        do{
            try {
                System.out.println("Seed ?");
                answer_seed = input.nextInt();
                input.nextLine(); // input clear
            } catch ( Exception e){
                System.out.println("Invalid input! Please type a positive number !");
                input.nextLine(); // input clear
                answer_seed = -1;
            }

            if (answer_seed<0){
                System.out.println("Invalid input! Please type a positive integer !");
            }
        }while(answer_seed<0);

        // SIZE X
        do {
            try {
                System.out.println("Size X ?");
                answer_sizeX = input.nextInt();
                input.nextLine(); // input clear
            } catch (Exception e) {
                System.out.println("Invalid input! Please type a number !");
                input.nextLine(); // input clear
                answer_sizeX = -1;
            }

            if (answer_sizeX <= 0) {
                System.out.println("Invalid input! Please type a positive integer !");
            }
        } while (answer_sizeX <= 0);

        // SIZE Y
        do {
            try {
                System.out.println("Size Y ?");
                answer_sizeY = input.nextInt();
                input.nextLine(); // input clear
            } catch (Exception e) {
                System.out.println("Invalid input! Please type a number !");
                input.nextLine(); // input clear
                answer_sizeY = -1;
            }

            if (answer_sizeY <= 0) {
                System.out.println("Invalid input! Please type a positive integer !");
            }
        } while (answer_sizeY <= 0);

        // TYPE (Perfect or Imperfect)
        do {
            System.out.println("Perfect maze? Y : N ");
            answer_type = input.nextLine().toLowerCase();

            if ("y".equals(answer_type)) {
                answer_type = "Perfect";
            } else if ("n".equals(answer_type)) {
                answer_type = "Imperfect";
            } else {
                System.out.println("Invalid input! Please type Y or N.");
                answer_type = null;
            }
        } while (answer_type == null);

        // GENERATION MODE (Full or Step-by-step)
        do {
            System.out.println("Generation mode : Full or Step-By-Step ? F : S");
            answer_generation = input.nextLine().toLowerCase();

            if ("f".equals(answer_generation)) {
                answer_generation = "Full";
            } else if ("s".equals(answer_generation)) {
                answer_generation = "Step-by-step";
            } else {
                System.out.println("Invalid input! Please type F or S.");
                answer_generation = null;
            }
        } while (answer_generation == null);


        // Summary
        System.out.println("- Seed: " + answer_seed);
        System.out.println("- Size: " + answer_sizeX + "x " + answer_sizeY + "y");
        System.out.println("- Type: " + answer_type);
        System.out.println("- Generation: " + answer_generation);

        Maze maze;
        if ("Perfect".equals(answer_type)) {
            maze = new PerfectMaze(answer_sizeX, answer_sizeY, answer_seed, answer_generation);
            maze.generateMaze();
        } else {
            maze = new ImperfectMaze(answer_sizeX, answer_sizeY, answer_seed, answer_generation);
            maze.generateMaze();
        }

        return maze;
    }

    public abstract void generateMaze();
}
