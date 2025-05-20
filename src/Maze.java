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
        this.startNode = this.node_array[0][0];
        this.endNode = this.node_array[x-1][y-1];
    }

    public Maze(int x, int y, int seed, int[] start, int[] end) {
        this(x, y, seed);
        if (start[0] < 0 || start[0] >= this.size_x || start[1] < 0 || start[1] >= this.size_y) {
            throw new IllegalArgumentException("Start invalid !");
        }

        if (end[0] < 0 || end[0] >= this.size_x || end[1] < 0 || end[1] >= this.size_y) {
            throw new IllegalArgumentException("End invalid !");
        }


        if (start[0] == end[0] && start[1] == end[1]) {
            throw new IllegalArgumentException("Start and end = same positions !");
        }

        this.startNode = this.node_array[start[0]][start[1]];
        this.endNode = this.node_array[end[0]][end[1]];
    }


    public Map<Node, List<Node>> get_adjacency_list() {
        return this.adjacencyList;
    }

    public Random get_rng(){
        return this.rng;
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
                return i;
            }
            else if (this.edge_list.get(i).get_nodes()[0].equals(b) && this.edge_list.get(i).get_nodes()[1].equals(a)){
                return i;
            }
        }
        return -1;
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
        for (int x = 0; x < size_x; x++) {
            for (int y = 0; y < size_y; y++) {
                Node node = node_array[x][y];
                node.setPath(false);
                node.setMark(null);
            }
        }
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

        // Place les noeuds
        for (int y = 0; y < this.size_y; y++) {
            for (int x = 0; x < this.size_x; x++) {
                Node sommet = this.node_array[x][y];
                int disp_y = y * 2 + 1;
                int disp_x = x * 2 + 1;

                if (sommet.equals(this.startNode)) {
                    display[disp_y][disp_x] = "\u001B[32m S \u001B[0m";
                } else if (sommet.equals(this.endNode)) {
                    display[disp_y][disp_x] = "\u001B[31m E \u001B[0m";
                } else if (sommet.isPath()) {
                    display[disp_y][disp_x] = "\u001B[35m " + sommet.getMark() + " \u001B[0m";
                } else if ("V".equals(sommet.getMark())){
                        display[disp_y][disp_x] = "\u001B[37m " + sommet.getMark() + " \u001B[0m";
                }else {
                        display[disp_y][disp_x] = " . ";
                }

            }
        }

        // Ouvre les murs selon les edges (corrigé)
        for (Edge e : edge_list) {
            Node[] ab = e.get_nodes();
            Node a = ab[0];
            Node b = ab[1];
            int[] coord_a = a.get_coordinates();
            int[] coord_b = b.get_coordinates();

            int wall_x = Math.min(coord_a[0], coord_b[0]) * 2 + 1 + (coord_a[0] == coord_b[0] ? 0 : 1);
            int wall_y = Math.min(coord_a[1], coord_b[1]) * 2 + 1 + (coord_a[1] == coord_b[1] ? 0 : 1);

            display[wall_y][wall_x] = "   ";
        }
        // Affiche la matrice
        for (String[] row : display) {
            for (String c : row)
                System.out.print(c);
            System.out.println();
        }
        clearMarks();
    }

    // pour génération du labyrinthe imparfait ET modification locale du laby
    public boolean edgeExists(Node a, Node b) {
        for (Edge e : get_edge_list()) {
            if (e.connected(a, b)) {
                return true;
            }
        }
        return false;
    }

    public void removeEdgeBetween(Node a, Node b) {
        get_edge_list().removeIf(e -> e.connected(a, b));

        if (get_adjacency_list().containsKey(a)) {
            get_adjacency_list().get(a).remove(b);
        }
        if (get_adjacency_list().containsKey(b)) {
            get_adjacency_list().get(b).remove(a);
        }
    }

    public void addEdgeBetween(Node a, Node b) {
        int dx = Math.abs(a.get_coordinates()[0] - b.get_coordinates()[0]);
        int dy = Math.abs(a.get_coordinates()[1] - b.get_coordinates()[1]);
        if (dx + dy != 1) {
            System.out.println("Erreur : Les noeuds ne sont pas adjacents, impossible d'ajouter un mur.");
            return;
        }
        if (edgeExists(a, b)) {
            System.out.println("Erreur : Arête déjà existante, impossible d'ajouter un mur.");
            return;
        }
        add_edge(new Edge(a, b));
        //pour resoudre pb kruskal :
        edge_list.add(new Edge(a, b)); // ajouter au graphe
        adjacencyList.get(a).add(b); // mettre à jour la connectivité
        adjacencyList.get(b).add(a);
    }

    public void removeRandomWalls(int max) {
        int removed = 0;
        Random rng = get_rng();
        int sizeX = get_size()[0];
        int sizeY = get_size()[1];

        while (removed < max) {
            int x = rng.nextInt(sizeX);
            int y = rng.nextInt(sizeY);
            Node node = get_node(x, y);

            Node[] neighbors = node.get_neighbours(get_node_array());
            List<Node> neighborList = new ArrayList<>();
            for (Node n : neighbors) {
                if (n != null) neighborList.add(n);
            }
            Collections.shuffle(neighborList, rng);

            for (Node neighbor : neighborList) {
                if (!edgeExists(node, neighbor)) {
                    addEdgeBetween(node, neighbor);
                    removed++;
                    break;
                }
            }
        }
    }

    public void addRandomWalls(int max) {
        int added = 0;
        Random rng = get_rng();
        int sizeX = get_size()[0];
        int sizeY = get_size()[1];

        while (added < max) {
            int x = rng.nextInt(sizeX);
            int y = rng.nextInt(sizeY);
            Node node = get_node(x, y);

            Node[] neighbors = node.get_neighbours(get_node_array());
            List<Node> neighborList = new ArrayList<>();
            for (Node n : neighbors) {
                if (n != null) neighborList.add(n);
            }
            Collections.shuffle(neighborList, rng);

            for (Node neighbor : neighborList) {
                if (edgeExists(node, neighbor)) {
                    removeEdgeBetween(node, neighbor);
                    added++;
                    break;
                }
            }
        }
    }
}