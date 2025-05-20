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

            // kruskal ou bfs
            String generationAlgo;
            do {
                System.out.print("\nGeneration algorithm : Kruskal or BFS ? K : B ");
                generationAlgo = scanner.nextLine().toLowerCase();
            } while (!"k".equals(generationAlgo) && !"b".equals(generationAlgo));

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


            int[] start = {startX, startY};
            int[] end = {endX, endY};

            // Création du labyrinthe
            if ("p".equals(mazeType)) {
                System.out.println("\u001B[32mGenerating Perfect Maze...\u001B[0m");

                PerfectMaze mazePerfect = new PerfectMaze(sizeX, sizeY, seed, start, end);
                if ("k".equals(generationAlgo)) {
                    mazePerfect.generateKruskal();
                } else {
                    if ("s".equals(generationMode)) {
                        mazePerfect.bfs_next_step();
                    } else {
                        mazePerfect.generateBFS();
                    }
                }

                mazePerfect.displayTextMaze();

                switch (solvingAlgo) {
                    case "b":
                        Solver.bfs(mazePerfect);
                        System.out.print("\u001B[34mResolution Perfect BFS!\n\u001B[0m");
                        break;
                    case "d":
                        Solver.dfs(mazePerfect);
                        System.out.print("\u001B[34mResolution Perfect DFS!\n\u001B[0m");
                        break;
                    case "k":
                        Solver.dijkstra(mazePerfect);
                        System.out.print("\u001B[34mResolution Perfect Dijkstra!\n\u001B[0m");
                        break;
                    case "a":
                        Solver.aStar(mazePerfect);
                        System.out.print("\u001B[34mResolution Perfect A*!\n\u001B[0m");
                        break;
                    case "r":
                        Solver.wallFollowerRight(mazePerfect);
                        System.out.print("\u001B[34mResolution Perfect Wall Follower Right!\n\u001B[0m");
                        break;
                    case "l":
                        Solver.wallFollowerLeft(mazePerfect);
                        System.out.print("\u001B[34mResolution Perfect Wall Follower Left!\n\u001B[0m");
                        break;
                }

                mazePerfect.displayTextMaze();
                System.out.println("\n");

            } else if ("i".equals(mazeType)) {
                System.out.println("\u001B[33mGenerating Imperfect Maze...\u001B[0m");

                Maze mazeImperfect;
                if ("k".equals(generationAlgo)) {
                    mazeImperfect = new ImperfectMazeKruskal(sizeX, sizeY, seed, start, end);
                } else {
                    mazeImperfect = new ImperfectMaze(sizeX, sizeY, seed, start, end);
                }

                mazeImperfect.displayTextMaze();

                boolean res = false;
                switch (solvingAlgo) {
                    case "b":
                        res = Solver.bfs(mazeImperfect);
                        System.out.print("\u001B[34mResolution Imperfect BFS!\n\u001B[0m");
                        break;
                    case "d":
                        res = Solver.dfs(mazeImperfect);
                        System.out.print("\u001B[34mResolution Imperfect DFS!\n\u001B[0m");
                        break;
                    case "k":
                        res = Solver.dijkstra(mazeImperfect);
                        System.out.print("\u001B[34mResolution Imperfect Dijkstra!\n\u001B[0m");
                        break;
                    case "a":
                        res = Solver.aStar(mazeImperfect);
                        System.out.print("\u001B[34mResolution Imperfect A*!\n\u001B[0m");
                        break;
                    case "r":
                        res = Solver.wallFollowerRight(mazeImperfect);
                        System.out.print("\u001B[34mResolution Imperfect Wall Follower Right!\n\u001B[0m");
                        break;
                    case "l":
                        res = Solver.wallFollowerLeft(mazeImperfect);
                        System.out.print("\u001B[34mResolution Imperfect Wall Follower Left!\n\u001B[0m");
                        break;
                }

                if (res) {
                    mazeImperfect.displayTextMaze();
                    System.out.println("\n");
                } else {
                    System.out.println("\u001B[31mAucun chemin trouvé !\u001B[0m");
                }
            }


        }
    }
}
