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

        SolverSbS solver = new SolverSbS(mazePerfect, "dfs");

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

        mazePerfect.displayTextMaze();
    }
}