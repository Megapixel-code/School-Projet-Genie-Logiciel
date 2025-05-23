import java.util.Scanner;

public class MainInteract {
    public static void main(String[] args) {

        PerfectMaze maze = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to CYnapse!");
        int sizeX = 0;
        int sizeY = 0;

        String answer;
        do {
            System.out.print("Load or New? L : N ");
            answer = scanner.nextLine().toLowerCase();
        } while (!"n".equals(answer) && !"l".equals(answer));

        if ("n".equals(answer)) {

            // size

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
            //seed
            int seed;
            do {
                System.out.print("Seed? ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Seed? ");
                    scanner.next();
                }
                seed = scanner.nextInt();
                scanner.nextLine();
            } while (false);


            // maze type
            String mazeType;
            do {
                System.out.print("Maze Type : Perfect or Imperfect ? P : I ");
                mazeType = scanner.nextLine().toLowerCase();
            } while (!"p".equals(mazeType) && !"i".equals(mazeType));


            // Generation full ou sbs pour parfait
            String generationMode = "";

            if ("p".equals(mazeType)) {

                do {
                    System.out.print("Generation mode : Full or Step-by-Step ? F : S ");
                    generationMode = scanner.nextLine().toLowerCase();
                } while (!"f".equals(generationMode) && !"s".equals(generationMode));
            } else {
                generationMode = "f";
            }

            // generationType
            String generationType ="";

            if ("f".equals(generationMode)) {
                do {
                    System.out.print("Kruskal or BFS generation ? K : B ");
                    generationType = scanner.nextLine().toLowerCase();
                } while (!"k".equals(generationType) && !"b".equals(generationType));
            }

            //maze
            int[] start = {startX, startY};
            int[] end = {endX, endY};



            if ("p".equals(mazeType)) {
                System.out.println("\u001B[32mGenerating Perfect Maze...\u001B[0m");

                maze = new PerfectMaze(sizeX, sizeY, seed, start, end);
                if ("f".equals(generationMode)) {
                    if ("k".equals(generationType)) {
                        maze.generateKruskal();
                    } else {
                        maze.generateBFS();
                    }

                } else {
                        maze.generate_dfs_next_step();
                }

            } else {
                System.out.println("\u001B[33mGenerating Imperfect Maze...\u001B[0m");

                if ("k".equals(generationType)) {
                    maze = new ImperfectMazeKruskal(sizeX, sizeY, seed, start, end);
                } else {
                    maze = new ImperfectMaze(sizeX, sizeY, seed, start, end);
                }
            }

            maze.displayTextMaze();

        }else {
            // Liste les fichiers disponibles dans le dossier "backup"
            String[] saves = Maze.get_backup_names();
            if (saves == null || saves.length == 0) {
                System.out.println("No saves found in the backup folder.");
                return; // quitte le programme ou revient au menu principal si souhaité
            }

            // Affiche les sauvegardes disponibles
            System.out.println("Available saves:");
            for (int i = 0; i < saves.length; i++) {
                System.out.println(i + " - " + saves[i]);
            }

            // Demande à l'utilisateur de choisir un index valide
            int choice = -1;
            do {
                System.out.print("Enter the number of the save you want to load: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Please enter a number: ");
                    scanner.next();
                }
                choice = scanner.nextInt();
            } while (choice < 0 || choice >= saves.length);
            scanner.nextLine(); // sécurité

            // Chargement effectif
            String selectedSave = saves[choice];
             maze = new PerfectMaze();
            maze.restore_maze(selectedSave);


            System.out.println("\u001B[32mMaze successfully restored!\u001B[0m");
            maze.displayTextMaze();
            sizeX = maze.get_size()[0];
            sizeY = maze.get_size()[1];


        }

        // Après avoir généré le maze
        boolean exit = false;

        do {


            // 2. Menu : modifier / résoudre / quitter
            String action;
            do {
                System.out.println("\nWhat do you want to do?");
                System.out.println("m - Modify the maze");
                System.out.println("s - Solve the maze");
                System.out.println("v - Save the maze");
                System.out.println("q - Quit");
                System.out.print("Your choice? ");
                action = scanner.nextLine().toLowerCase();
            } while (!action.equals("m") && !action.equals("s") && !action.equals("q") && !action.equals("v"));

            switch (action) {
                case "m":
                    String editChoice;
                    do {
                        System.out.print("\nDo you want to modify the maze? No (n), Add wall (a), Remove wall (r): ");
                        editChoice = scanner.nextLine().toLowerCase();
                    } while (!editChoice.equals("n") && !editChoice.equals("a") && !editChoice.equals("r"));

                    if (!editChoice.equals("n")) {
                        int x1, y1, x2, y2;

                        // Lecture coordonnées du premier noeud
                        do {
                            System.out.print("First node X? ");
                            while (!scanner.hasNextInt()) {
                                System.out.print("Invalid. First node X? ");
                                scanner.next();
                            }
                            x1 = scanner.nextInt();
                        } while (x1 < 0 || x1 >= sizeX);

                        do {
                            System.out.print("First node Y? ");
                            while (!scanner.hasNextInt()) {
                                System.out.print("Invalid. First node Y? ");
                                scanner.next();
                            }
                            y1 = scanner.nextInt();
                        } while (y1 < 0 || y1 >= sizeY);

                        // Lecture coordonnées du second noeud
                        do {
                            System.out.print("Second node X? ");
                            while (!scanner.hasNextInt()) {
                                System.out.print("Invalid. Second node X? ");
                                scanner.next();
                            }
                            x2 = scanner.nextInt();
                        } while (x2 < 0 || x2 >= sizeX);

                        do {
                            System.out.print("Second node Y? ");
                            while (!scanner.hasNextInt()) {
                                System.out.print("Invalid. Second node Y? ");
                                scanner.next();
                            }
                            y2 = scanner.nextInt();
                        } while (y2 < 0 || y2 >= sizeY);
                        scanner.nextLine(); // sécurité

                        Node node1 = maze.get_node(x1, y1);
                        Node node2 = maze.get_node(x2, y2);

                        if (editChoice.equals("r")) {
                            // Supprimer un mur = ajouter une arête
                            if (maze.in_edge_list(node1, node2) < 0) {
                                Edge edge = new Edge(node1, node2);
                                maze.add_edge(edge);
                                maze.add_adjacency(node1, node2);
                                System.out.println("Wall removed – nodes connected!");
                            } else {
                                System.out.println("Nodes are already connected.");
                            }
                        } else if (editChoice.equals("a")) {
                            // Ajouter un mur = retirer une arête
                            int index = maze.in_edge_list(node1, node2);
                            if (index >= 0) {
                                Edge edge = maze.get_edge(node1, node2);
                                maze.remove_edge(edge);
                                maze.remove_adjacency(node1, node2);
                                System.out.println("Wall added – connection removed!");
                            } else {
                                System.out.println("Nodes are not connected.");
                            }
                        }

                    }
                    maze.displayTextMaze();
                    break;

                case "s":
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

                        if ("s".equals(solvingMode) && (solvingAlgo.equals("r") || solvingAlgo.equals("l"))) {
                            System.out.println("\u001B[31mStep-by-step mode is not available for Wall Follower algorithms.\u001B[0m");
                            System.out.println("Please choose another algorithm.");
                        }
                    } while (
                            (!"b".equals(solvingAlgo) &&
                                    !"d".equals(solvingAlgo) &&
                                    !"k".equals(solvingAlgo) &&
                                    !"a".equals(solvingAlgo) &&
                                    !"r".equals(solvingAlgo) &&
                                    !"l".equals(solvingAlgo)) ||
                                    ("s".equals(solvingMode) && (solvingAlgo.equals("r") || solvingAlgo.equals("l")))
                    );


                    if (solvingAlgo.equals("b")) {
                        solvingAlgo = "bfs";
                    } else if (solvingAlgo.equals("d")) {
                        solvingAlgo = "dfs";
                    } else if (solvingAlgo.equals("a")) {
                        solvingAlgo = "astar";
                    } else if (solvingAlgo.equals("k")) {
                        solvingAlgo = "dijkstra";
                    }

                    // Resolution
                    System.out.println("\n");


                    boolean res = false;
                    Solver solver = new Solver();
                    SolverSbS solversbs = null;
                    if (!solvingAlgo.equals("r") && !solvingAlgo.equals("l")) {
                        solversbs = new SolverSbS(maze, solvingAlgo);
                    }

                    switch (solvingAlgo) {
                        case "bfs":
                            if ("f".equals(solvingMode)) {
                                System.out.println("\u001B[34mFull BFS resolution:\u001B[0m");
                                res = solver.bfs(maze);
                            } else {
                                System.out.println("\u001B[34mStep-by-step BFS resolution:\u001B[0m");
                                while (!solversbs.next_step()) {
                                    maze.displayTextMaze();
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                                res = true;
                            }
                            break;

                        case "dfs":
                            if ("f".equals(solvingMode)) {
                                System.out.println("\u001B[34mFull DFS resolution:\u001B[0m");
                                res = solver.dfs(maze);
                            } else {
                                System.out.println("\u001B[34mStep-by-step DFS resolution:\u001B[0m");
                                while (!solversbs.next_step()) {
                                    maze.displayTextMaze();
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                                res = true;
                            }
                            break;

                        case "dijkstra":
                            if ("f".equals(solvingMode)) {
                                System.out.println("\u001B[34mFull Dijkstra resolution:\u001B[0m");
                                res = solver.dijkstra(maze);
                            } else {
                                System.out.println("\u001B[34mStep-by-step Dijkstra resolution:\u001B[0m");
                                while (!solversbs.next_step()) {
                                    maze.displayTextMaze();
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                                res = true;
                            }
                            break;

                        case "astar":
                            if ("f".equals(solvingMode)) {
                                System.out.println("\u001B[34mFull A* resolution:\u001B[0m");
                                res = solver.aStar(maze);
                            } else {
                                System.out.println("\u001B[34mStep-by-step A* resolution:\u001B[0m");
                                while (!solversbs.next_step()) {
                                    maze.displayTextMaze();
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                                res = true;
                            }
                            break;

                        case "r":
                            System.out.println("\u001B[34mFull Right Wall Follower resolution:\u001B[0m");
                            res = solver.wallFollowerRight(maze);
                            break;

                        case "l":
                            System.out.println("\u001B[34mFull Left Wall Follower resolution:\u001B[0m");
                            res = solver.wallFollowerLeft(maze);
                            break;
                    }

                    if (res) {
                        maze.displayTextMaze();
                        System.out.println("\n");
                    } else {
                        System.out.println("\u001B[31mAucun chemin trouvé !\u001B[0m");
                    }
                    break;

                case "v":
                    System.out.print("Enter a name for the save file: ");
                    String saveName = scanner.nextLine();

                    maze.save_maze(saveName);
                    break;

                case "q":
                    exit = true;
                    System.out.println("Goodbye!");
                    break;
            }

        } while (!exit);
    }
}