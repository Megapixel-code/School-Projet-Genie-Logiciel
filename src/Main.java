class Main {
    public static void main(String[] args) {

       //Initialisation parfait
        int[] start = {0, 0};
        int[] end = {19, 7};
        ImperfectMaze maze = new ImperfectMaze(20, 8, 2, start, end);
        maze.restore_maze("crashbfs");
        System.out.print("\u001B[33mGeneration Perfect !\n\u001B[0m");
        maze.displayTextMaze();
        System.out.println("\n");

        // SOLVER SBS DFS 
        SolverSbS solver = new SolverSbS(maze, "dfs");

        while (!(solver.next_step())) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            maze.displayTextMaze();
        }

        while (!(solver.find_path_step())) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            maze.displayTextMaze();
        }

        // SOLVER SBS bfs
        solver = new SolverSbS(maze, "bfs");

        while (!(solver.next_step())) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            maze.displayTextMaze();
        }

        while (!(solver.find_path_step())) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            maze.displayTextMaze();
        }

        // SOLVER SBS A*
        solver = new SolverSbS(maze, "astar");

        while (!(solver.next_step())) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            maze.displayTextMaze();
        }

        while (!(solver.find_path_step())) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            maze.displayTextMaze();
        }

        /* 
        maze.save_maze("test124");
        maze.displayTextMaze();
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