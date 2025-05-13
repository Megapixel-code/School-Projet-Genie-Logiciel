
import java.util.ArrayList;

abstract class Maze {
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
    public Maze(int x, int y, int seed){
        /*
         * init the maze
         * size_x and size_y are the dimensions of the maze
         * seed is used for generating the maze
         */
        this.size_x = x;
        this.size_y = y;
        this.seed = seed;
        this.node_array = new Node[x][y];
        for (int i = 0; i < x; i++){
            for (int j = 0; j < y; j++){
                Node node = new Node();
                node.node(i, j);
                this.node_array[i][j] = node;
            }
        }
    }


    public int get_size_x(){
        // self explainatory
        return this.size_x;
    }
    public int get_size_y(){
        // self explainatory
        return this.size_y;
    }
    public int get_seed(){
        // self explainatory
        return this.seed;
    }
    public Node[][] get_node_array(){
        // self explainatory
        return this.node_array;
    }
    public ArrayList<Edge> get_edge_list(){
        // self explainatory
        return this.edge_list;
    }
}