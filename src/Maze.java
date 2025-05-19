
import java.util.ArrayList;
import java.util.Random;

abstract class Maze {
    /*
     * a maze is rectangular, has a seed for generating paths 
     * has a node array, and a list of all connected edges
     */
    private int size_x;
    private int size_y;
    private int seed = new Random().nextInt(1000000);
    private Node[][] node_array;
    private ArrayList<Edge> edge_list = new ArrayList<Edge>();    
    private Node start_node = new Node();
    private Node end_node = new Node();


    public Maze(int x, int y, int seed, Node start_node, Node end_node){
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
        this.start_node = start_node;
        this.end_node = end_node;
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

    public Node get_start_node(){
        /*
         * returns the start node of the maze
         * used for generating the maze
         */
        return this.start_node;
    }

    public void set_start_node(Node new_start_node){
        /*
         * sets the start node of the maze
         * used for generating the maze
         */
        this.start_node = new_start_node;
    }

    public Node get_end_node(){
        /*
         * returns the end node of the maze
         * used for generating the maze
         */
        return this.end_node;
    }

    public void set_end_node(Node new_end_node){
        /*
         * sets the end node of the maze
         * used for generating the maze
         */
        this.end_node = new_end_node;
    }

    public Node get_node(int x, int y){
        /*
         * returns the node at the given coordinates
         * used for generating the maze
         */
        if ((x >= 0 && x < this.size_x) && (y >= 0 && y < this.size_y)){
            return this.node_array[x][y];    
        }
        else{
            return null;
        }
    }

    public ArrayList<Edge> get_edge_list(){
        // self explainatory
        return this.edge_list;
    }
    public int in_edge_list(Node a, Node b){
        /*
         * check if the edge between the two nodes is already in the list of edges
         * used for generating the maze
         */
        for (int i = 0; i < this.edge_list.size(); i++){
            if (this.edge_list.get(i).get_nodes()[0].equals(a) && this.edge_list.get(i).get_nodes()[1].equals(b)){
                return i;
            }
            else if (this.edge_list.get(i).get_nodes()[0].equals(b) && this.edge_list.get(i).get_nodes()[1].equals(a)){
                return i;
            }
        }
        return -1;
    }

    public Edge get_edge(Node a, Node b){
        /*
         * returns the edge at the given index
         * used for generating the maze
         */
        int i = in_edge_list(a, b);
        if (i == -1){
            return null;
        }
        else if (i >= this.edge_list.size()){
            return null;
        }
        return this.edge_list.get(i);
    }

    public void add_edge(Edge edge){
        /*
         * add an edge to the list of edges
         * used for generating the maze
         */
        this.edge_list.add(edge);
    }

    public void remove_edge(Edge edge){
        /*
         * remove an edge from the list of edges
         * used for generating the maze
         */
        this.edge_list.remove(edge);
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