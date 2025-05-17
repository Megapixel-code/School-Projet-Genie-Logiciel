class Main {
    public static void main(String[] args) {

        Node start = new Node(0, 0);
        Node end = new Node(9, 4);
        PerfectMaze mazePerfect = new PerfectMaze(10, 5, 5,start,end );

        // INSTANT
        mazePerfect.generate();
        mazePerfect.displayTextMaze();

        System.out.println("Generation Perfect !\n");

        ImperfectMaze mazeImperfect = new ImperfectMaze(10, 5, 5,start,end );
        mazeImperfect.generate();
        mazeImperfect.displayTextMaze();
        System.out.println("Generation Imperfect !\n");

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