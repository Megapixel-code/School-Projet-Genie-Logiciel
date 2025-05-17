
import java.util.*;

abstract class Maze {
    /*
     * a maze is rectangular, has a seed for generating paths 
     * has a node array, and a list of all connected edges
     */
    private int size_x;
    private int size_y;
    private int seed;
    private Random rng;
    private Node[][] node_array;
    private ArrayList<Edge> edge_list = new ArrayList<Edge>();

    private Node startNode;
    private Node endNode;

    //adjacence
    private Map<Node, List<Node>> adjacencyList = new HashMap<>();



    public Maze(int x, int y, int seed){
        /*
         * init the maze
         * size_x and size_y are the dimensions of the maze
         * seed is used for generating the maze
         */
        this.size_x = x;
        this.size_y = y;
        this.seed = seed;
        this.rng = new Random(seed);
        this.node_array = new Node[x][y];
        for (int i = 0; i < x; i++){
            for (int j = 0; j < y; j++){
                Node node = new Node(i,j);
                this.node_array[i][j] = node;
            }
        }
    }
    public Maze(int x, int y, int seed, Node start, Node end) {
        this(x, y, seed); // appelle le constructeur de base
        this.startNode = start;
        this.endNode = end;
    }
    // donc on doit faire Node start = new Node (x,y); puis creer le laby

    public Map<Node, List<Node>> get_adjacency_list() {
        return this.adjacencyList;
    }

    public void set_StartNode(int x, int y) {
        this.startNode = get_node(x, y);
    }

    public void set_EndNode(int x, int y) {
        this.endNode = get_node(x, y);
    }

    public Node getStartNode() {
        return this.startNode;
    }

    public Node getEndNode() {
        return this.endNode;
    }

    public Random get_rng(){
        return this.rng;
    }

    public int getSize_x(){
        return this.size_x;
    }

    public int getSize_y(){
        return this.size_y;
    }

    public Node[][] get_node_array() {
        return this.node_array;
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
                return 0;
            }
            else if (this.edge_list.get(i).get_nodes()[0].equals(b) && this.edge_list.get(i).get_nodes()[1].equals(a)){
                return 0;
            }
        }
        return 1;
    }

    public void add_edge(Edge edge) {
        this.edge_list.add(edge);

        Node a = edge.get_nodes()[0];
        Node b = edge.get_nodes()[1];

        adjacencyList.putIfAbsent(a, new ArrayList<>());
        adjacencyList.get(a).add(b);

        adjacencyList.putIfAbsent(b, new ArrayList<>());
        adjacencyList.get(b).add(a);
    }


    public void clearMarks() {
        for (int y = 0; y < this.size_y; y++) {
            for (int x = 0; x < this.size_x; x++) {
                Node node = this.node_array[y][x]; // à corriger
                node.setPath(false);
                node.setMark(null);
            }
        }
    }

    public void displayTextMaze() { // transforme ta edge liste en visuel
        int rows = this.size_y * 2 + 1;
        int cols = this.size_x * 2 + 1;
        String[][] display = new String[rows][cols]; // [y][x]
    
        // Initialise tout en mur
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                display[i][j] = "///";
    
        // Place les noeuds
        for (int y = 0; y < this.size_y; y++) {
            for (int x = 0; x < this.size_x; x++) {

                Node sommet = this.node_array[y][x]; // à corriger
                if (sommet.hasMark()) {
                    display[y * 2 + 1][x * 2 + 1] = " " + sommet.getMark() + " ";
                } else {
                    display[y * 2 + 1][x * 2 + 1] = " . ";
                }

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

        //Affichage start et end
        int[] start = this.startNode.get_coordinates();
        int[] end = this.endNode.get_coordinates();

        int start_display_x = start[0] * 2 + 1;
        int start_display_y = start[1] * 2 + 1;

        int end_display_x = end[0] * 2 + 1;
        int end_display_y = end[1] * 2 + 1;

        display[start_display_y][start_display_x] = " E ";
        display[end_display_y][end_display_x] = " S ";

        display[start_display_y][start_display_x-1] = "   ";
        display[end_display_y][end_display_x+1] = "   ";
    
        // Affiche la matrice
        for (String[] row : display) {
            for (String c : row)
                System.out.print(c);
            System.out.println();
        }
    }
}