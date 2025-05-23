class Main {
    public static void main(String[] args) {

       //Initialisation parfait
        Solver solver = new Solver();
        int[] start = {0, 0};
        int[] end = {14, 14};
        PerfectMaze mazePerfect = new PerfectMaze(25, 25, 5, start, end);
        mazePerfect.generateBFS();
        System.out.print("\u001B[33mGeneration Perfect !\n\u001B[0m");
        mazePerfect.displayTextMaze();
        System.out.println("\n");

        //Resolution BFS
       solver.bfs(mazePerfect);
        System.out.print("\u001B[34mResolution Perfect BFS!\n\u001B[0m");
        mazePerfect.displayTextMaze();
        System.out.println("\n");


       //Resolution DFS
      solver.dfs(mazePerfect);
        System.out.print("\u001B[34mResolution Perfect DFS!\n\u001B[0m");
        mazePerfect.displayTextMaze();
        System.out.println("\n");


         //Resolution A*
        solver.aStar(mazePerfect);
        System.out.print("\u001B[34mResolution Perfect aStar!\n\u001B[0m");
        mazePerfect.displayTextMaze();
        System.out.println("\n");


      //Resolution DIJKSTRA
        solver.dijkstra(mazePerfect);
        System.out.print("\u001B[34mResolution Perfect Dijkstra!\n\u001B[0m");
        mazePerfect.displayTextMaze();System.out.println("\n");


        //Resolution WALL FOLLOWER RIGHT
        solver.wallFollowerRight(mazePerfect);
        System.out.print("\u001B[34mResolution Perfect Wall Follower Right !\n\u001B[0m");
        mazePerfect.displayTextMaze();
        System.out.println("\n");


       //Resolution WALL FOLLOWER Left
        solver.wallFollowerLeft(mazePerfect);
        System.out.print("\u001B[34mResolution Perfect Wall Follower Left !\n\u001B[0m");
        mazePerfect.displayTextMaze();System.out.println("\n");

    /* // Ajout manuel de mur
        Node a = mazePerfect.get_node(1, 1);
        Node b = mazePerfect.get_node(2, 1);

        if (mazePerfect.edgeExists(a, b)) {

            mazePerfect.removeEdgeBetween(a,b);
            System.out.print("Mur ajouté entre les deux cases !");
            mazePerfect.displayTextMaze();
            System.out.println("\n");

        } else {
            System.out.print("\u001B[31mAucun lien entre a et b\u001B[0m");
            System.out.println("\n");
        }

        /* /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
         */

        // Initialisation Imparfait
     /*   System.out.print("\u001B[33mGeneration Imperfect !\n\u001B[0m");
        ImperfectMaze mazeImperfect = new ImperfectMaze(20, 8, 600, start, end);
        mazeImperfect.displayTextMaze();
        System.out.println("\n");


        //Resolution BFS
        boolean res = solver.bfs(mazeImperfect);
        if (res){

            System.out.print("\u001B[34mResolution Imperfect BFS!\n \u001B[0m");
            mazeImperfect.displayTextMaze();
            System.out.println("\n");
        } else {
            System.out.println("\u001B[31mAucun chemin trouvé avec BFS\u001B[0m");
        }


        //Resolution DFS
        res = solver.dfs(mazeImperfect);
        if (res){

            System.out.print("\u001B[34mResolution Imperfect DFS!\n \u001B[0m");
            mazeImperfect.displayTextMaze();
            System.out.println("\n");

        } else {
            System.out.println("\u001B[31mAucun chemin trouvé avec DFS\u001B[0m");
        }


        //Resolution A*
        res = solver.aStar(mazeImperfect);
        if (res){

            System.out.print("\u001B[34mResolution Imperfect aStar!\n \u001B[0m");
            mazeImperfect.displayTextMaze();
            System.out.println("\n");
        } else {
            System.out.println("\u001B[31mAucun chemin trouvé avec aStar\u001B[0m");
        }


        //Resolution DIJKSTRA
        res = solver.dijkstra(mazeImperfect);
        if (res){

            System.out.print("\u001B[34mResolution Imperfect Dijkstra!\n \u001B[0m");
            mazeImperfect.displayTextMaze();
            System.out.println("\n");
        } else {
            System.out.println("\u001B[31mAucun chemin trouvé avec Dijkstra\u001B[0m");
        }


        // Resolution Wallf Follower Right
        res = solver.wallFollowerRight(mazeImperfect);
        if (res) {

            System.out.print("\u001B[34mResolution Perfect Wall Follower Right !\n\u001B[0m");
            mazeImperfect.displayTextMaze();
            System.out.println("\n");
        } else {
            System.out.println("\u001B[31mAucun chemin trouvé avec Wall Follower Right\u001B[0m ");
        }


        // Resolution Wallf Follower Left
        res = solver.wallFollowerLeft(mazeImperfect);
        if (res) {

            System.out.print("\u001B[34mResolution Perfect Wall Follower Left !\n\u001B[0m");
            mazeImperfect.displayTextMaze();
            System.out.println("\n");
        } else {
            System.out.println("\u001B[31mAucun chemin trouvé avec Wall Follower Left\u001B[0m");
        }


        // Ajout manuel de mur
        a = mazeImperfect.get_node(1, 1);
        b = mazeImperfect.get_node(2, 1);

        if (mazeImperfect.edgeExists(a, b)) {

            mazeImperfect.removeEdgeBetween(a,b);

            System.out.print("Mur ajouté entre les deux cases !");
            mazeImperfect.displayTextMaze();
            System.out.println("\n");
        } else {
            System.out.println("\u001B[31mAucun lien entre a et b\u001B[0m");
        }



        // STEP BY STEP
        /*mazePerfect.displayTextMaze();
        while (!(mazePerfect.bfs_next_step())) {

            mazePerfect.displayTextMaze();
            System.out.println();

            try {
                // to sleep 1 second
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // recommended because catching InterruptedException clears interrupt flag
                Thread.currentThread().interrupt();
                // you probably want to quit if the thread is interrupted
                return;
            }
        }
        mazePerfect.displayTextMaze();*/ // affichage quand la gene est finis donc quand on return true
    }
}