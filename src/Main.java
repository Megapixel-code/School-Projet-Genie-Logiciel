class Main {
    public static void main(String[] args) {

        PerfectMaze mazePerfect = new PerfectMaze(20, 8, 5543, 0, 0, 19, 7);
        mazePerfect.generateKruskal();
        mazePerfect.displayTextMaze();
        System.out.println("\u001B[33mGeneration Perfect !\n\u001B[0m");

        Solver.bfs(mazePerfect);
        mazePerfect.displayTextMaze();
        System.out.println("\u001B[34mResolution Perfect BFS!\n\u001B[0m");
        mazePerfect.clearMarks();

        Solver.dfs(mazePerfect);
        mazePerfect.displayTextMaze();
        System.out.println("\u001B[34mResolution Perfect DFS!\n\u001B[0m");
        mazePerfect.clearMarks();

        Solver.aStar(mazePerfect);
        mazePerfect.displayTextMaze();
        System.out.println("\u001B[34mResolution Perfect aStar!\n\u001B[0m");
        mazePerfect.clearMarks();

        Solver.dijkstra(mazePerfect);
        mazePerfect.displayTextMaze();
        System.out.println("\u001B[34mResolution Perfect Dijkstra!\n\u001B[0m");
        mazePerfect.clearMarks();

        ImperfectMaze mazeImperfect = new ImperfectMaze(20, 8, 5543, 0, 0, 19, 7);
        mazeImperfect.generateBFS();
        mazeImperfect.displayTextMaze();
        System.out.println("\u001B[33mGeneration Imperfect !\n\u001B[0m");

        boolean res = Solver.bfs(mazeImperfect);
        if (res){
            mazeImperfect.displayTextMaze();
            System.out.println("\u001B[34mResolution Imperfect BFS!\n \u001B[0m");
            mazeImperfect.clearMarks();
        } else {
            System.out.println("\u001B[31mAucun chemin trouvé avec cet algo \u001B[0m");
        }

        res = Solver.dfs(mazeImperfect);
        if (res){
            mazeImperfect.displayTextMaze();
            System.out.println("\u001B[34mResolution Imperfect DFS!\n \u001B[0m");
            mazeImperfect.clearMarks();
        } else {
            System.out.println("\u001B[31mAucun chemin trouvé avec cet algo\u001B[0m");
        }

        res = Solver.aStar(mazeImperfect);
        if (res){
            mazeImperfect.displayTextMaze();
            System.out.println("\u001B[34mResolution Imperfect aStar!\n \u001B[0m");
            mazeImperfect.clearMarks();
        } else {
            System.out.println("\u001B[31mAucun chemin trouvé avec cet algo\u001B[0m");
        }

        res = Solver.dijkstra(mazeImperfect);
        if (res){
            mazeImperfect.displayTextMaze();
            System.out.println("\u001B[34mResolution Imperfect Dijkstra!\n \u001B[0m");
            mazeImperfect.clearMarks();
        } else {
            System.out.println("\u001B[31mAucun chemin trouvé avec cet algo\u001B[0m");
        }

        /*
        Edge edge = new Edge(mazePerfect.get_node(0, 0),mazePerfect.get_node(1, 0));
        Edge edge1 = new Edge(mazePerfect.get_node(1, 0),mazePerfect.get_node(1, 1));
        mazePerfect.add_edge(edge);
        mazePerfect.add_edge(edge1);
        System.out.println("_______________");
        */


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