class Main {
    public static void main(String[] args) {
        Node startNode = new Node(0, 0);
        Node endNode = new Node(4, 4);
        PerfectMaze mazetest = new PerfectMaze(5, 5, 0, startNode, endNode);

        mazetest.displayTextMaze();
        System.out.println();
        
        /*
        Edge edge = new Edge(mazetest.get_node(0, 0),mazetest.get_node(1, 0));
        Edge edge1 = new Edge(mazetest.get_node(1, 0),mazetest.get_node(1, 1));
        mazetest.add_edge(edge);
        mazetest.add_edge(edge1);
        System.out.println("_______________");
        */
        
        while (!(mazetest.bfs_next_step())) {
            
            mazetest.displayTextMaze();
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

        mazetest.displayTextMaze();
    }
}