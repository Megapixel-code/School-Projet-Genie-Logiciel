class Edge {
    /**
     * This class represents an edge between two nodes in the maze.
     *
     * An edge means there is a path between these two nodes.
     * If there is no edge, it means there is a wall or the nodes are too far.
     */
    private Node node_1;
    private Node node_2;

    /**
     * Create an edge between two nodes.
     *
     * The constructor checks if the two nodes are next to each other
     * (one case difference in X or Y but not diagonal).
     * If not, it throws an exception because it is not allowed.
     *
     * @param n_1 first node to connect
     * @param n_2 second node to connect
     * @throws IllegalArgumentException if nodes are not next to each other
     */
    public Edge(Node n_1, Node n_2){
        int[] coords_n_1 = n_1.get_coordinates();
        int[] coords_n_2 = n_2.get_coordinates();
        if ((coords_n_1[0] == coords_n_2[0] && coords_n_1[1] == coords_n_2[1] + 1)||
                (coords_n_1[0] == coords_n_2[0] && coords_n_1[1] == coords_n_2[1] - 1)||
                (coords_n_1[1] == coords_n_2[1] && coords_n_1[0] == coords_n_2[0] + 1)||
                (coords_n_1[1] == coords_n_2[1] && coords_n_1[0] == coords_n_2[0] - 1)){

            this.node_1 = n_1;
            this.node_2 = n_2;
        }
        else {
            throw new IllegalArgumentException("ERROR, YOU TRIED TO LINK INCOMPATIBLE NODES, SEE EDGE CLASS");
        }
    }

    /**
     * Return the two nodes connected by this edge.
     *
     * @return array of the two nodes
     */
    public Node[] get_nodes(){
        Node[] nodes = {this.node_1, this.node_2};
        return nodes;
    }
    /**
     * Check if this edge connects the two nodes given.
     *
     * It returns true if these two nodes are connected by this edge.
     * The order of nodes does not matter.
     *
     * @param a first node to check
     * @param b second node to check
     * @return true if this edge connects a and b, false otherwise
     */
    public boolean connected(Node a, Node b) {
        if ((node_1 == a && node_2 == b) || (node_1 == b && node_2 == a)) {
            return true;
        }
        return false;
    }

}