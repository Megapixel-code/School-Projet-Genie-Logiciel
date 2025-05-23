
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Abstract class Maze represents a rectangular maze with nodes and edges.
 *
 * It has a size (width and height), a random seed for generation,
 * a 2D array of nodes, and a list of edges connecting nodes.
 * It also keeps track of start and end nodes, and adjacency between nodes.
 */

abstract class Maze {

    private int size_x;
    private int size_y;
    private int seed;
    private Random rng;

    private Node[][] node_array;
    private ArrayList<Edge> edge_list = new ArrayList<Edge>();

    private Node startNode;
    private Node endNode;

    private Map<Node, List<Node>> adjacencyList = new HashMap<>();

    /**
     * Default constructor: create a 2x2 maze with seed 0.
     */
    public Maze(){
        this(2, 2, 0);
    }

    /**
     * Constructor with size and seed.
     * Initializes the nodes in the maze and sets default start at (0,0) and end at (x-1,y-1).
     *
     * @param x width of the maze
     * @param y height of the maze
     * @param seed random seed for maze generation
     */
    public Maze(int x, int y, int seed){
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

    /**
     * Constructor with size, seed, start, and end coordinates.
     * Validates start and end positions are inside maze and different.
     *
     * @param x width of the maze
     * @param y height of the maze
     * @param seed random seed
     * @param start coordinates of the start node
     * @param end coordinates of the end node
     * @throws IllegalArgumentException if start or end are invalid or the same
     */
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

    /**
     * Returns the adjacency list map.
     *
     * @return map of node to list of adjacent nodes
     */
    public Map<Node, List<Node>> get_adjacency_list() {
        return this.adjacencyList;
    }

    /**
     * Adds adjacency between two nodes (undirected).
     *
     * @param a first node
     * @param b second node
     */
    public void add_adjacency(Node a, Node b) {
        adjacencyList.putIfAbsent(a, new ArrayList<>());
        adjacencyList.putIfAbsent(b, new ArrayList<>());
        if (!adjacencyList.get(a).contains(b)) adjacencyList.get(a).add(b);
        if (!adjacencyList.get(b).contains(a)) adjacencyList.get(b).add(a);
    }

    /**
     * Removes adjacency between two nodes.
     *
     * @param a first node
     * @param b second node
     */
    public void remove_adjacency(Node a, Node b) {
        if (adjacencyList.containsKey(a)) adjacencyList.get(a).remove(b);
        if (adjacencyList.containsKey(b)) adjacencyList.get(b).remove(a);
    }

    /**
     * Returns the random number generator used in maze.
     *
     * @return Random object seeded with maze seed
     */
    public Random get_rng(){
        return this.rng;
    }

    /**
     * Set the start node by coordinates.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void set_StartNode(int x, int y) {
        this.startNode = get_node(x, y);
    }

    /**
     * Set the end node by coordinates.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void set_EndNode(int x, int y) {
        this.endNode = get_node(x, y);
    }

    /**
     * Get the start node.
     *
     * @return start node
     */
    public Node getStartNode() {
        return this.startNode;
    }

    /**
     * Get the end node.
     *
     * @return end node
     */
    public Node getEndNode() {
        return this.endNode;
    }

    /**
     * Get the 2D array of nodes.
     *
     * @return 2D Node array
     */
    public Node[][] get_node_array() {
        return this.node_array;
    }

    /**
     * Get size of the maze as an array [width, height].
     *
     * @return int array with size_x and size_y
     */
    public int[] get_size(){
        int[] size = {this.size_x, this.size_y};
        return size;
    }

    /**
     * Get the seed used to generate maze.
     *
     * @return seed integer
     */
    public int get_seed(){
        return this.seed;
    }

    /**
     * Return node at given coordinates or null if out of bounds.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return node at (x,y) or null if invalid
     */
    public Node get_node(int x, int y){
        if ((x >= 0 && x < this.size_x) && (y >= 0 && y < this.size_y)){
            return this.node_array[x][y];
        }
        else{
            return null;
        }
    }

    /**
     * Returns the list of edges in the maze.
     *
     * @return ArrayList of edges
     */
    public ArrayList<Edge> get_edge_list(){
        return this.edge_list;
    }

    /**
     * Checks if edge between nodes a and b is in the edge list.
     * Returns index if found, else -1.
     *
     * @param a first node
     * @param b second node
     * @return index of edge in edge_list or -1 if not found
     */
    public int in_edge_list(Node a, Node b){

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

    /**
     * Adds an edge to the maze, updating the edge list and adjacency list.
     *
     * @param edge the Edge to add
     */
    public void add_edge(Edge edge) {
        this.edge_list.add(edge);

        Node a = edge.get_nodes()[0];
        Node b = edge.get_nodes()[1];

        adjacencyList.putIfAbsent(a, new ArrayList<>());
        adjacencyList.get(a).add(b);

        adjacencyList.putIfAbsent(b, new ArrayList<>());
        adjacencyList.get(b).add(a);
    }

    /**
     * Clear all marks and path flags on nodes.
     * Useful before running pathfinding or maze algorithms.
     */
    public void clearMarks() {
        for (int x = 0; x < size_x; x++) {
            for (int y = 0; y < size_y; y++) {
                Node node = node_array[x][y];
                node.setPath(false);
                node.setMark(null);
            }
        }
    }

    /**
     * Returns the edge connecting nodes a and b if exists, else null.
     *
     * @param a first node
     * @param b second node
     * @return Edge object or null if not found
     */
    public Edge get_edge(Node a, Node b){
        int i = in_edge_list(a, b);
        if (i == -1){
            return null;
        }
        else if (i >= this.edge_list.size()){
            return null;
        }
        return this.edge_list.get(i);
    }

    /**
     * Remove an edge from the maze's edge list.
     *
     * @param edge the edge to remove
     */
    public void remove_edge(Edge edge){
        this.edge_list.remove(edge);
    }

    /**
     * Prints a text representation of the maze to the console.
     * Uses colors for start (green), end (red), path (purple), visited nodes (white).
     * Walls are represented by ///.
     */
    public void displayTextMaze() {
        int rows = this.size_y * 2 + 1;
        int cols = this.size_x * 2 + 1;
        String[][] display = new String[rows][cols];

        // Initialize all cells as walls
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                display[i][j] = "///";

        // Place nodes and special markers
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

        // Remove walls between connected nodes (edges)
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

        // Print the maze to console
        for (String[] row : display) {
            for (String c : row)
                System.out.print(c);
            System.out.println();
        }
    }

    /**
     * Checks if an edge exists between two nodes.
     *
     * @param a first node
     * @param b second node
     * @return true if edge exists, false otherwise
     */
    public boolean edgeExists(Node a, Node b) {
        for (Edge e : get_edge_list()) {
            if (e.connected(a, b)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the edge between two nodes if it exists.
     * Also updates adjacency list.
     *
     * @param a first node
     * @param b second node
     */
    public void removeEdgeBetween(Node a, Node b) {
        get_edge_list().removeIf(e -> e.connected(a, b));

        if (get_adjacency_list().containsKey(a)) {
            get_adjacency_list().get(a).remove(b);
        }
        if (get_adjacency_list().containsKey(b)) {
            get_adjacency_list().get(b).remove(a);
        }
    }

    /**
     * Adds an edge between two adjacent nodes if possible.
     * Checks adjacency and existing edge before adding.
     *
     * @param a first node
     * @param b second node
     */
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
        // For Kruskal problem fix:
        edge_list.add(new Edge(a, b)); // add to graph
        adjacencyList.get(a).add(b); // update connectivity
        adjacencyList.get(b).add(a);
    }

    /**
     * Randomly removes up to max walls by adding edges between unconnected nodes.
     *
     * @param max maximum number of walls to remove
     */
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

    /**
     * Randomly adds up to max walls by removing edges between connected nodes.
     *
     * @param max maximum number of walls to add
     */
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

    /**
     * Saves the maze data into a file in folder "backup".
     * Format:
     * - First line: size_x size_y
     * - Second line: seed
     * - Third line: startX startY endX endY
     * - Following lines: edges as "x1 y1 x2 y2"
     *
     * @param save_name file name to save the maze data
     */
    public void save_maze(String save_name){

        int[] size = this.get_size();

        List<String> buffer = new ArrayList<String>();
        String pre_buffer = "";
        // Add size line
        buffer.add(size[0]+" "+size[1]+"\n");
        // Add seed line
        buffer.add(this.seed + "\n");
        // Add start and end coords
        Node[] n_array_temp = {this.startNode, this.endNode};
        for (Node n : n_array_temp){
            int[] coord = n.get_coordinates();
            pre_buffer += coord[0] + " " + coord[1] + " ";
        }
        pre_buffer += "\n";
        buffer.add(pre_buffer);

        // Add edges lines
        for (Edge e : this.edge_list) {
            Node[] tuple_node = e.get_nodes();
            for (int i = 0; i < 2; i++){
                int[] coords = tuple_node[i].get_coordinates();
                if (i == 0){
                    pre_buffer = coords[0] + " " + coords[1] + " ";
                }
                else {
                    pre_buffer = coords[0] + " " + coords[1] + "\n";
                }
                buffer.add(pre_buffer);
            }
        }

        // Create file and directories if needed
        try {
            File file = new File("backup/"+save_name);
            file.getParentFile().mkdirs();
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists, the data will be overwritten");
            }
        } catch (IOException e) {
            System.out.println("Error while creating the file");
            e.printStackTrace();
        }

        // Write buffer to file
        try {
            FileWriter my_file = new FileWriter("backup/" + save_name);
            // empties the buffer in the file
            for (String s : buffer){
                my_file.write(s);
            }
            my_file.close();
        } catch (IOException e) {
            System.out.println("error while saving the file");
            e.printStackTrace();
        }
    }

    /**
     * Restore maze data from a saved file in folder "backup".
     * Reads size, seed, start/end, and edges.
     *
     * @param save_name file name to restore from
     */
    public void restore_maze(String save_name){
        try {
            File my_file = new File("backup/" + save_name);
            Scanner my_scanner = new Scanner(my_file);

            // Read size line
            String data = my_scanner.nextLine();
            String[] s = data.split(" ");
            int[] out = new int[s.length];
            for (int i = 0; i < s.length; i++){
                out[i] = Integer.parseInt(s[i]);
            }
            if (s.length != 2){
                throw new IllegalArgumentException("invalid save file");
            }
            // then saves the size and node_array in the maze object
            this.size_x = out[0];
            this.size_y = out[1];
            this.node_array = new Node[size_x][size_y];
            for (int i = 0; i < this.size_x; i++){
                for (int j = 0; j < this.size_y; j++){
                    Node node = new Node(i,j);
                    this.node_array[i][j] = node;
                }
            }

            // Read seed line
            data = my_scanner.nextLine();
            this.seed = Integer.parseInt(data);
            this.rng = new Random(this.seed);

            // Read start/end line
            data = my_scanner.nextLine();
            s = data.split(" ");
            out = new int[s.length];
            for (int i = 0; i < s.length; i++){
                out[i] = Integer.parseInt(s[i]);
            }
            if (s.length != 4){
                throw new IllegalArgumentException("invalid save file");
            }
            this.startNode = get_node(out[0], out[1]);
            this.endNode = get_node(out[2], out[3]);

            // Read edges
            Node n_1;
            Node n_2;
            Edge e;
            // reset the edge_list
            this.edge_list = new ArrayList<Edge>();
            // loop through all of the data
            while (my_scanner.hasNextLine()){
                // parses the line to get the two nodes positions
                data = my_scanner.nextLine();
                s = data.split(" ");
                out = new int[s.length];
                for (int i = 0; i < s.length; i++){
                    out[i] = Integer.parseInt(s[i]);
                }
                if (s.length != 4){
                    throw new IllegalArgumentException("invalid save file");
                }
                // saves the edge
                n_1 = get_node(out[0], out[1]);
                n_2 = get_node(out[2], out[3]);
                e = new Edge(n_1, n_2);
                edge_list.add(e);
            }
        } catch (FileNotFoundException e){
            System.out.println("Error occured while restoring the maze :");
            e.printStackTrace();
        }
    }

    /**
     * Returns all save file names inside the "backup" folder.
     *
     * @return array of file names or null if no files found
     */
    static public String[] get_backup_names(){

        File folder = new File("backup");
        File[] list_of_files = folder.listFiles();
        int iterator = 0;
        if (list_of_files != null){
            String[] buffer = new String[list_of_files.length];
            for (File f : list_of_files){
                if (f.isFile()){
                    buffer[iterator] = f.getName();
                    iterator++;
                }
            }
            String[] result = Arrays.copyOf(buffer, iterator);
            return result;
        }
        else {
            return null;
        }
    }

    /**
     * Deletes a backup file in "backup" folder.
     *
     * @param save_name file name to delete
     */
    public void delete_backup(String save_name){
        File my_file = new File("backup/" + save_name);
        if (my_file.delete()){
            System.out.println("file deleted sucessfully");
        }
        else {
            System.out.println("problem trying to delete the file");
        }
    }
}
