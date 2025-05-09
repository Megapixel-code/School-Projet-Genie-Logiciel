package Labyrinthe;

import java.util.Arrays;

// lien entre deux cases
public class Edge {
    /*
     * an edge is a link between two nodes, in the maze
     * it represent a valid path between two cases
     * no edge between two nodes means there is a wall or the nodes
     * are too far apart from each other
     */
    private Node node_1;
    private Node node_2;

    public Edge(Node n_1, Node n_2){
        /*
         * init the edge
         * makes sure you arent doing anything wrong
         * and creates the edge
         */
        int[] coords_n_1 = n_1.get_coordinates();
        int[] coords_n_2 = n_2.get_coordinates();
        if ((Math.abs(coords_n_1[0] - coords_n_2[0]) <= 1) &&
                (Math.abs(coords_n_1[1] - coords_n_2[1]) <= 1) &&
                (Arrays.compare(coords_n_1, coords_n_2) != 0)){
            /*
             * true if the node are next to each other and
             * the two node have diferent coordinates
             */
            this.node_1 = n_1;
            this.node_2 = n_2;
        }
        else {
            System.out.println("ERROR, YOU TRIED TO LINK INCOMPATIBLE NODES, SEE EDGE CLASS");
        }
    }

    public Node[] get_nodes(){
        // returns a tuplet of the two connected nodes
        Node[] nodes = {this.node_1, this.node_2};
        return nodes;
    }
}
