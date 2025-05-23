import java.util.*;

/**
 * PerfectMaze extends Maze and generates a perfect maze,
 * meaning no loops and exactly one path between any two nodes.
 *
 * It provides two main algorithms for generation:
 * - DFS based (depth-first search)
 * - Kruskal's algorithm based
 */
class PerfectMaze extends Maze {
    private Node last_node;

    /**
     * Default constructor calls Maze default constructor,
     * initializes last_node to null.
     */
    public PerfectMaze() {
        super();
        this.last_node = null;
    }

    /**
     * Constructor with size, seed, start and end coordinates.
     * Sets last_node to start node and sets its depth to 0.
     *
     * @param x maze width
     * @param y maze height
     * @param seed random seed
     * @param start start node coordinates
     * @param end end node coordinates
     */
    public PerfectMaze(int x, int y, int seed, int[] start, int[] end) {
        super(x, y, seed, start, end);
        this.last_node = super.get_node(start[0], start[1]);
        this.last_node.set_depth(0);
    }

    /**
     * Returns the last node visited during DFS generation.
     *
     * @return last node object
     */
    public Node get_last_node(){
        return this.last_node;
    }

    /**
     * Performs one step of DFS maze generation.
     * Returns true if generation is finished.
     * Finished means we are back to depth 0 and no unvisited neighbors.
     *
     * @return true if maze generation finished, false otherwise
     */
    public boolean generate_dfs_next_step(){
        int[] current_pos = last_node.get_coordinates();
        int current_depth = last_node.get_depth();
        Node[] nodes_around = new Node[4];
        nodes_around[0] = super.get_node(current_pos[0]+1, current_pos[1]);
        nodes_around[1] = super.get_node(current_pos[0]-1, current_pos[1]);
        nodes_around[2] = super.get_node(current_pos[0], current_pos[1]+1);
        nodes_around[3] = super.get_node(current_pos[0], current_pos[1]-1);

        int nb_available_nodes = 0;
        for (int k = 0; k < 4; k++){
            if ((nodes_around[k] != null) && (nodes_around[k].get_depth() == -1)){
                nodes_around[nb_available_nodes] = nodes_around[k];
                nb_available_nodes++;
            }
        }
        Node[] availables_nodes = Arrays.copyOf(nodes_around, nb_available_nodes);

        if (nb_available_nodes == 0){
            if (current_depth == 0){
                // Maze generation finished
                return true;
            }
            else {
                // Backtrack to node with depth one less
                for (Node n : nodes_around) {
                    if (n != null){
                        if (n.get_depth() == current_depth-1){
                            this.last_node = n;
                        }
                    }
                }
            }
        }
        else {
            // Choose random unvisited neighbor and create edge
            int random_nb = this.get_rng().nextInt(nb_available_nodes);
            Edge new_edge = new Edge(this.last_node, availables_nodes[random_nb]);
            this.add_edge(new_edge);
            this.last_node = availables_nodes[random_nb];
            this.last_node.set_depth(current_depth + 1);
        }
        return false;
    }
    /**
     * Run the full DFS maze generation until finished.
     */
    public void generateBFS() {
        while (!this.generate_dfs_next_step()) {
            // continue until maze is finished
        }
    }

    /**
     * Generates the maze using Kruskal's algorithm.
     *
     * Each node is initially in its own set with a unique depth.
     * Then randomly connects nodes with different depths and
     * merges their sets by updating depth.
     * Repeats until all nodes have same depth (one connected set).
     */
    public void generateKruskal() {
        Node[][] grid = get_node_array();
        int sizeX = get_size()[0];
        int sizeY = get_size()[1];
        Random rng = get_rng();

        // Initialize each node with unique depth
        int ind = 0;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                Node node = grid[x][y];
                node.set_depth(ind++);
            }
        }

        boolean mazeComplete = false;
        while (!mazeComplete) {
            int x = rng.nextInt(sizeX);
            int y = rng.nextInt(sizeY);
            Node current = grid[x][y];
            int currentDepth = current.get_depth();

            Node[] neighbors = current.get_neighbours(grid);
            List<Node> neighborList = new ArrayList<>();
            for (Node n : neighbors) {
                if (n != null && n.get_depth() != currentDepth) {
                    neighborList.add(n);
                }
            }

            if (!neighborList.isEmpty()) {
                Node neighbor = neighborList.get(rng.nextInt(neighborList.size()));
                int neighborDepth = neighbor.get_depth();

                add_edge(new Edge(current, neighbor));

                // Merge sets by updating depth
                for (int i = 0; i < sizeX; i++) {
                    for (int j = 0; j < sizeY; j++) {
                        if (grid[i][j].get_depth() == neighborDepth) {
                            grid[i][j].set_depth(currentDepth);
                        }
                    }
                }
            }

            // Check if all nodes have same depth (one set)
            int firstDepth = grid[0][0].get_depth();
            mazeComplete = true;
            for (int i = 0; i < sizeX; i++) {
                for (int j = 0; j < sizeY; j++) {
                    if (grid[i][j].get_depth() != firstDepth) {
                        mazeComplete = false;
                        break;
                    }
                }
                if (!mazeComplete) break;
            }
        }
    }

}