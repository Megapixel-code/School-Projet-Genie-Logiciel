import java.util.Scanner;

public class MainInteract {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to CYnapse!");

        String answer;
        do {
            System.out.print("Load or New? L : N ");
            answer = scanner.nextLine().toLowerCase();
        } while (!"n".equals(answer) && !"l".equals(answer));

        if ("n".equals(answer)) {

            // size
            int sizeX, sizeY;
            do {
                System.out.print("Width (X)? ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Width (X)? ");
                    scanner.next();
                }
                sizeX = scanner.nextInt();
            } while (sizeX <= 0);

            do {
                System.out.print("Height (Y)? ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Height (Y)? ");
                    scanner.next();
                }
                sizeY = scanner.nextInt();
            } while (sizeY <= 0);

            // Start
            int startX, startY;
            System.out.println("\nStart Coordinates:");
            do {
                System.out.print("Start X? ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Start X? ");
                    scanner.next();
                }
                startX = scanner.nextInt();
            } while (startX < 0 || startX >= sizeX);

            do {
                System.out.print("Start Y? ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Start Y? ");
                    scanner.next();
                }
                startY = scanner.nextInt();
            } while (startY < 0 || startY >= sizeY);

            // End
            int endX, endY;
            System.out.println("\nEnd Coordinates:");
            do {
                System.out.print("End X? ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. End X? ");
                    scanner.next();
                }
                endX = scanner.nextInt();
            } while (endX < 0 || endX >= sizeX);

            do {
                System.out.print("End Y? ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. End Y? ");
                    scanner.next();
                }
                endY = scanner.nextInt();
            } while (endY < 0 || endY >= sizeY);

            scanner.nextLine(); // securité car apres on fais un nextline

            /*// kruskal ou bfs
            String generationAlgo;
            do {
                System.out.print("\nGeneration algorithm : Kruskal or BFS ? K : B ");
                generationAlgo = scanner.nextLine().toLowerCase();
            } while (!"k".equals(generationAlgo) && !"b".equals(generationAlgo));
*/
            // maze type
            String mazeType;
            do {
                System.out.print("Maze Type : Perfect or Imperfect ? P : I ");
                mazeType = scanner.nextLine().toLowerCase();
            } while (!"p".equals(mazeType) && !"i".equals(mazeType));

            // Seed
            int seed;
            do {
                System.out.print("Seed? ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Seed? ");
                    scanner.next();
                }
                seed = scanner.nextInt();
            } while (seed < 0);
            scanner.nextLine();

            // Generation full ou sps
            String generationMode;
            do {
                System.out.print("Generation mode : Full or Step-by-Step ? F : S ");
                generationMode = scanner.nextLine().toLowerCase();
            } while (!"f".equals(generationMode) && !"s".equals(generationMode));


            //maze
            int[] start = {startX, startY};
            int[] end = {endX, endY};

            PerfectMaze maze = new PerfectMaze();

            if ("p".equals(mazeType)) {
                System.out.println("\u001B[32mGenerating Perfect Maze...\u001B[0m");

                 maze = new PerfectMaze(sizeX, sizeY, seed, start, end);
                if ("f".equals(generationMode)) {
                    maze.generateKruskal();
                } else {
                    if ("s".equals(generationMode)) {
                        maze.generate_dfs_next_step();
                    }
                }

            } else if ("i".equals(mazeType)) {
                System.out.println("\u001B[33mGenerating Imperfect Maze...\u001B[0m");

                if ("f".equals(generationMode)) {
                    maze = new ImperfectMazeKruskal(sizeX, sizeY, seed, start, end);
                } else {
                    maze = new ImperfectMaze(sizeX, sizeY, seed, start, end);
                }
            }

            maze.displayTextMaze();

            /*ajouter modifier*/


            // resolution full or sbs
            String solvingMode;
            do {
                System.out.print("Solving mode : Full or Step-by-Step ? F : S ");
                solvingMode = scanner.nextLine().toLowerCase();
            } while (!"f".equals(solvingMode) && !"s".equals(solvingMode));

            // algo resolution
            String solvingAlgo;
            do {
                System.out.println("\nChoose solving algorithm:");
                System.out.println("b - BFS");
                System.out.println("d - DFS");
                System.out.println("k - Dijkstra");
                System.out.println("a - A*");
                System.out.println("r - Wall Follower Right");
                System.out.println("l - Wall Follower Left");
                System.out.print("Your choice? ");
                solvingAlgo = scanner.nextLine().toLowerCase();
            } while (
                    !"b".equals(solvingAlgo) &&
                            !"d".equals(solvingAlgo) &&
                            !"k".equals(solvingAlgo) &&
                            !"a".equals(solvingAlgo) &&
                            !"r".equals(solvingAlgo) &&
                            !"l".equals(solvingAlgo)
            );


            // Resolution
            System.out.println("\n");


            boolean res = false;
            Solver solver = new Solver();
            SolverSbS solversbs = new SolverSbS(maze);

            switch (solvingAlgo) {
                case "b":
                    if ("f".equals(solvingMode)) {
                        res = solver.bfs(maze);
                    }
                    System.out.print("\u001B[34mResolution BFS!\n\u001B[0m");
                    break;

                case "d":
                    if ("f".equals(solvingMode)) {
                        res = solver.dfs(maze);
                    }
                    System.out.print("\u001B[34mResolution DFS!\n\u001B[0m");
                    break;

                case "k":
                    if ("f".equals(solvingMode)) {
                        res = solver.dijkstra(maze);
                    }
                    System.out.print("\u001B[34mResolution Dijkstra!\n\u001B[0m");
                    break;

                case "a":
                    if ("f".equals(solvingMode)) {
                        res = solver.aStar(maze);
                    }
                    System.out.print("\u001B[34mResolution A*!\n\u001B[0m");
                    break;

                case "r":
                    if ("f".equals(solvingMode)) {
                        res = solver.wallFollowerRight(maze);
                    }
                    System.out.print("\u001B[34mResolution Wall Follower Right!\n\u001B[0m");
                    break;

                case "l":
                    if ("f".equals(solvingMode)) {
                        res = solver.wallFollowerLeft(maze);
                    }
                    System.out.print("\u001B[34mResolution Wall Follower Left!\n\u001B[0m");
                    break;
            }

            if (res) {
                maze.displayTextMaze();
                System.out.println("\n");
            } else {
                System.out.println("\u001B[31mAucun chemin trouvé !\u001B[0m");
            }
        }


    }
}