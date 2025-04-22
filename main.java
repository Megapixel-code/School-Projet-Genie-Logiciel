import java.util.Arrays;
import java.util.ArrayList;

class Node {
    /* 
     * depth will show the solving path.
     * pos_x and pos_y are the 2d coordinates of the node
     */
    private int depth;
    private int pos_x;
    private int pos_y;

    public void Node(int x, int y){
        /*
         * init the node
         * depth is set by default to minus one, same as no value here
         */
        this.pos_x = x;
        this.pos_y = y;
        this.depth = -1;
    }

    public int[] get_coordinates(){
        // return a tuplet of the two coordinates, pos_x then pos_y
        int[] pos = {this.pos_x, this.pos_y};
        return pos;
    }

    public void get_depth(){
        // self explainatory
        return this.depth
    }

    public void set_depth(int depth){
        // self explainatory
        this.depth = depth;
    }

    /* 
     * no function to set coordinates because once the coordinates 
     * are set we dont want them changing
     */
}



class Edge {
    /*
     * an edge is a link between two nodes, in the maze
     * it represent a valid path between two cases
     * no edge between two nodes means there is a wall or the nodes
     * are too far apart from each other
     */
    private Node node_1;
    private Node node_2;

    public void Edge(Node n_1, Node n_2){
        /*
         * init the edge
         * makes sure you arent doing anything wrong 
         * and creates the edge
         */
        int[] coords_n_1 = n_1.get_coordinates()
        int[] coords_n_2 = n_2.get_coordinates()
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
        Node[] nodes = {this.node_1, this.node_2}
        return nodes;
    }
}



class Maze {
    /*
     * a maze is rectangular, has a seed for generating paths 
     * has a node array, and a list of all connected edges
     */
    private int size_x;
    private int size_y;
    private int seed;
    private Node[][] node_array;
    private ArrayList<Edge> edge_list = new ArrayList<Edge>();
    // do the rest
}



class perfect_maze extends Maze {
    private Node last_node;
    // do the rest
}



class imperfect_maze extends Maze {
    // do the rest
}



public class Main {
    public static void main(){
        pass;
    }
}