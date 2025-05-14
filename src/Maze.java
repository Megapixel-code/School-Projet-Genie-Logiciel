
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
                Node node = new Node(i,j);
                this.node_array[i][j] = node;
            }
        }
    }

    public int[] get_size(){
        // retruns tuple representing the size of maze
        int[] size = {this.size_x, this.size_y};
        return size;
    }

    public int get_seed(){
        // self explainatory
        return this.seed;
    }

    public Node get_node(int x, int y){
        /*
         * returns the node at the given coordinates
         * used for generating the maze
         */
        return this.node_array[x][y];
    }

    public ArrayList<Edge> get_edge_list(){
        // self explainatory
        return this.edge_list;
    }

    public void add_edge(Edge edge){
        /*
         * add an edge to the list of edges
         * used for generating the maze
         */
        this.edge_list.add(edge);
    }

    public void displayTextMaze() {
        int rows = this.size_y * 2 + 1;
        int cols = this.size_x * 2 + 1;
        String[][] display = new String[rows][cols];
    
        // Initialise tout en mur
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                display[i][j] = "///";
    
        // Place les cellules
        for (int y = 0; y < this.size_y; y++) {
            for (int x = 0; x < this.size_x; x++) {
                display[y * 2 + 1][x * 2 + 1] = "   ";
            }
        }
    
        // Ouvre les murs selon les edges
        for (Edge e : edge_list) {
            Node[] ab = e.get_nodes();
            Node a = ab[0];
            Node b = ab[1];
            int[] coord_a = a.get_coordinates();
            int[] coord_b = b.get_coordinates();
    
            int wall_x = coord_a[0] + coord_b[0] + 1;
            int wall_y = coord_a[1] + coord_b[1] + 1;
            display[wall_y][wall_x] = "   ";
        }
    
        // Affiche la matrice
        for (String[] row : display) {
            for (String c : row)
                System.out.print(c);
            System.out.println();
        }
    }
}