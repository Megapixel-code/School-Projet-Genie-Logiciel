class Main {
    public static void main(String[] args) {
        PerfectMaze mazetest = new PerfectMaze(5, 5, 0);
        System.out.println(mazetest.get_seed());
        mazetest.displayTextMaze();
        Edge edge = new Edge(mazetest.get_node(0, 0),mazetest.get_node(1, 0));
        Edge edge1 = new Edge(mazetest.get_node(1, 0),mazetest.get_node(1, 1));
        mazetest.add_edge(edge);
        mazetest.add_edge(edge1);
        System.out.println("_______________");
        mazetest.displayTextMaze();
    }
}