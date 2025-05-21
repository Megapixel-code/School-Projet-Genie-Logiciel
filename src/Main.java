class Main {
    public static void main(String[] args) {

       //Initialisation parfait
        int[] start = {0, 0};
        int[] end = {19, 7};
        PerfectMaze mazePerfect = new PerfectMaze(20, 8, 15, start, end);
        mazePerfect.generateKruskal();
        System.out.print("\u001B[33mGeneration Perfect !\n\u001B[0m");
        mazePerfect.displayTextMaze();
        System.out.println("\n");

        // SOLVER SBS DFS 
        SolverSbS solver = new SolverSbS(mazePerfect, "dfs");

        while (!(solver.next_step())) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mazePerfect.displayTextMaze();
        }

        while (!(solver.find_path_step())) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mazePerfect.displayTextMaze();
        }

        // SOLVER SBS bfs
        solver = new SolverSbS(mazePerfect, "bfs");

        while (!(solver.next_step())) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mazePerfect.displayTextMaze();
        }

        while (!(solver.find_path_step())) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mazePerfect.displayTextMaze();
        }

        // SOLVER SBS A*
        solver = new SolverSbS(mazePerfect, "astar");

        while (!(solver.next_step())) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mazePerfect.displayTextMaze();
        }

        while (!(solver.find_path_step())) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mazePerfect.displayTextMaze();
        }

        /* 
        mazePerfect.save_maze("test124");
        mazePerfect.displayTextMaze();
        int[] pos = {0, 0};
        int[] pos2 = {0, 1};
        Maze new_maze = new PerfectMaze(2, 2, 1, pos, pos2);
        new_maze.displayTextMaze();
        new_maze.restore_maze("test124");
        new_maze.displayTextMaze();
        SolverSbS new_solver = new SolverSbS(new_maze);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while (!(new_solver.next_step())) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            new_maze.displayTextMaze();
        }

        while (!(new_solver.find_path_step())) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            new_maze.displayTextMaze();
        }
        */
    }
}