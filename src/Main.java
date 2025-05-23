class Main {
    public static void main(String[] args) {

        //Initialisation parfait
        SolverSbS solver;
        int[] time_ms_wait = {100, 200};
        int[] start = {0, 0};
        int[] end = {19, 7};
        ImperfectMaze maze = new ImperfectMaze(20, 8, 2, start, end);
        maze.generateBFS();

        // call all solver one by one
        String[] solve_types = SolverSbS.get_types();
        for (String s : solve_types){
            solver = new SolverSbS(maze, s);

            // visits the maze util solution found
            while (!(solver.next_step())) {
                try {
                    Thread.sleep(time_ms_wait[0]);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                maze.displayTextMaze();
            }

            // reveals the solution
            while (!(solver.find_path_step())) {
                try {
                    Thread.sleep(time_ms_wait[1]);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                maze.displayTextMaze();
            }

            // wait before the next solve
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}