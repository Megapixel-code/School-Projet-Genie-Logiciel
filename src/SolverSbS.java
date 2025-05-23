import java.util.*;
/**
 * SolverSbS class provides step-by-step maze solving with different algorithms.
 * It supports DFS, BFS, A*, and Dijkstra algorithms.
 *
 * The solver progresses one step at a time using next_step method,
 * allowing visualization or slow solving.
 */

public class SolverSbS {
    /*
     * solver_type:
     * 0 = DFS
     * 1 = BFS
     * 2 = A*
     * 3 = Dijkstra
     */
    private int solver_type;

    private Maze maze;

    private Node last_node;
    private Node start_node;
    private Node end_node;

    private Stack<Node> stack = new Stack<>();
    private LinkedList<Node> fifo = new LinkedList<>();


    /**
     * Constructor to create solver with default type DFS.
     *
     * @param m maze to solve
     */
    public SolverSbS(Maze m){
        this(m, "dfs");
    }

    /**
     * Constructor to create solver with specified type.
     *
     * @param m maze to solve
     * @param type solver type: "dfs", "bfs", "astar", or "dijkstra"
     * @throws IllegalArgumentException if invalid type is given
     */
    public SolverSbS(Maze m, String type){
        this.maze = m;
        this.last_node = m.getStartNode();
        this.start_node = m.getStartNode();
        this.end_node = m.getEndNode();

        switch (type) {
            // TODO : /!\ mettre a jour get_types, next_step, set_correct_node_mark /!\
            case "dfs" -> {
                this.solver_type = 0;
                this.stack.push(this.start_node);
            }
            case "bfs" -> {
                this.solver_type = 1;
                this.fifo.add(this.start_node);
            }
            case "astar" -> {
                this.solver_type = 2;
                this.fifo.add(this.start_node);
            }
            case "dijkstra" -> {
                this.solver_type = 3;
                this.fifo.add(this.start_node);
            }
            default -> throw new IllegalArgumentException("Invalid type for sbs solver : you entered type = \"" + type + "\"");
        }

        this.reset_nodes();
        this.start_node.set_depth(0);
    }
    /**
     * Returns all available solver types as array of strings.
     *
     * @return array of solver type names
     */
    public static String[] get_types(){
        String[] types = {"dfs", "bfs", "astar", "dijkstra"};
        return types;
    }

    /**
     * Perform the next step of solving according to the current solver type.
     *
     * @return true if solving finished, false otherwise
     */
    public boolean next_step(){
        switch (this.solver_type) {
            case 0 -> {
                return dfs_next_step();
            }
            case 1 -> {
                return bfs_next_step();
            }
            case 2 -> {
                return a_star_next_step();
            }
            case 3 -> {
                return dijkstra_next_step();
            }
            default -> throw new AssertionError();
        }
    }

    /**
     * Marks a node with correct solver mark according to solver type.
     * D = DFS, B = BFS, A = A*, K = Dijkstra.
     *
     * @param n node to mark
     */
    private void set_correct_node_mark(Node n){
        switch (this.solver_type) {
            case 0 -> n.setMark("D");
            case 1 -> n.setMark("B");
            case 2 -> n.setMark("A");
            case 3 -> n.setMark("K");
            default -> throw new AssertionError();
        }
    }

    /**
     * Reset all nodes in maze: set depth to -1, path false, mark null.
     */
    private void reset_nodes(){
        int[] size = this.maze.get_size();
        Node current_node;
        for (int i = 0; i < size[0]; i++){
            for (int j = 0; j < size[1]; j++){
                current_node = this.maze.get_node(i, j);
                current_node.set_depth(-1);
                current_node.setPath(false);
                current_node.setMark(null);
            }
        }
    }

    /**
     * Reveal the solution path one node at a time after solution found.
     *
     * @return true if finished revealing path, false if more nodes left
     */
    public boolean find_path_step(){
        Node n = this.end_node;
        while (n.isPath()) {
            n = get_node_depth_minus_one(n);
            if (n == null){
                System.out.println("The maze has no solutions or you called this function too many times");
                return true;
            }
        }

        n.setPath(true);
        this.set_correct_node_mark(n);
        return n == this.start_node;
    }

    /**
     * Get nodes around node n that are either visited or unvisited,
     * depending on the boolean parameter.
     * Only nodes connected by edges are returned.
     *
     * @param n the node to check around
     * @param visited true to get visited neighbors, false for unvisited
     * @return array of nodes or null if none found
     */
    private Node[] get_nodes_around(Node n, boolean visited){
        int[] current_pos = n.get_coordinates();

        Node[] nodes_around = new Node[4];
        nodes_around[0] = this.maze.get_node(current_pos[0]+1, current_pos[1]);
        nodes_around[1] = this.maze.get_node(current_pos[0]-1, current_pos[1]);
        nodes_around[2] = this.maze.get_node(current_pos[0], current_pos[1]+1);
        nodes_around[3] = this.maze.get_node(current_pos[0], current_pos[1]-1);

        for (int k = 0; k < 4; k++){
            if (nodes_around[k] != null){
                if (maze.in_edge_list(nodes_around[k], n) == -1){
                    nodes_around[k] = null;
                }
            }
        }

        int nb_available_nodes = 0;
        for (int k = 0; k < 4; k++){
            if (nodes_around[k] != null){
                if ((visited) && (nodes_around[k].is_visited())){
                    nodes_around[nb_available_nodes] = nodes_around[k];
                    nb_available_nodes++;
                }
                if (!(visited) && !(nodes_around[k].is_visited())){
                    nodes_around[nb_available_nodes] = nodes_around[k];
                    nb_available_nodes++;
                }
            }
        }
        Node[] nodes = Arrays.copyOf(nodes_around, nb_available_nodes);
        if (nb_available_nodes != 0){
            return nodes;
        }
        else {
            return null;
        }
    }

    /**
     * Find node around n with depth one less than n.
     * Used to backtrack the path.
     *
     * @param n current node
     * @return node with depth one less or null if none found
     */
    private Node get_node_depth_minus_one(Node n){

        int current_depth = n.get_depth();

        Node[] nodes = get_nodes_around(n, true);
        if (nodes == null){
            return null;
        }

        for (Node n_loop : nodes){
            if (n_loop.get_depth() == current_depth - 1){
                return n_loop;
            }
        }
        return null;
    }

    /**
     * Manhattan distance between two nodes.
     *
     * @param n1 first node
     * @param n2 second node
     * @return Manhattan distance
     */
    private int distance_between_nodes(Node n1, Node n2){
        int[] coords1 = n1.get_coordinates();
        int[] coords2 = n2.get_coordinates();
        return Math.abs(coords1[0] - coords2[0]) +
                Math.abs(coords1[1] - coords2[1]);
    }

    /**
     * One step of DFS solving.
     *
     * @return true if finished, false otherwise
     */
    private boolean dfs_next_step(){
        if (stack.isEmpty()){
            return true;
        }

        // gets the last unvisited node in stack
        this.last_node = stack.pop();
        while (this.last_node.is_visited()){
            // if the stack is empty, program is finished
            if (stack.isEmpty()){
                return true;
            }
            this.last_node = stack.pop();
        }

        // sets the current node to visited
        this.last_node.setMark("V");

        // if last_node is end_node, the program is finished
        if (this.last_node == this.end_node){
            return true;
        }

        // adds the nodes around to the stack with the correct depth
        int current_depth = last_node.get_depth();
        Node[] nodes_around = this.get_nodes_around(this.last_node, false);
        if (nodes_around != null){
            for (Node n : nodes_around){
                n.set_depth(current_depth + 1);
                stack.push(n);
            }
        }
        return false;
    }

    /**
     * Performs one step of BFS solving.
     * @return true if finished, false otherwise
     */
    private boolean bfs_next_step(){
        // if the list is empty, program is finished
        if (fifo.isEmpty()){
            return true;
        }

        // gets the last unvisited node in fifo
        this.last_node = fifo.removeFirst();
        while (this.last_node.is_visited()){
            // if the list is empty, program is finished
            if (fifo.isEmpty()){
                return true;
            }
            this.last_node = fifo.removeFirst();
        }

        // sets the current node to visited
        this.last_node.setMark("V");

        // if last_node is end_node, the program is finished
        if (this.last_node == this.end_node){
            return true;
        }

        // adds the nodes around to the stack with the correct depth
        int current_depth = last_node.get_depth();
        Node[] nodes_around = this.get_nodes_around(this.last_node, false);
        if (nodes_around != null){
            for (Node n : nodes_around){
                n.set_depth(current_depth + 1);
                fifo.add(n);
            }
        }
        return false;
    }

    /**
     * Performs one step of A* solving.
     * @return true if finished, false otherwise
     */
    private boolean a_star_next_step(){
        // removes all visited nodes in list
        int fifo_size = fifo.size();
        for (int i = 1; i <= fifo_size; i++){
            if (fifo.get(fifo_size - i).is_visited()){
                fifo.remove(fifo_size - i);
            }
        }

        // if the size is 0, no solutions have been found, the program ends
        fifo_size = fifo.size();
        if (fifo_size == 0){
            return true;
        }

        // gets the best node
        Node best_node = fifo.get(0);
        int best_score = this.distance_between_nodes(best_node, end_node);
        int score;
        int position_best = 0;
        for (int i = 0; i < fifo_size; i++){
            score = this.distance_between_nodes(fifo.get(i), end_node);
            if (score < best_score){
                position_best = i;
                best_node = fifo.get(i);
                best_score = score;
            }
        }
        this.last_node = best_node;
        fifo.remove(position_best);

        // sets the current node to visited
        this.last_node.setMark("V");

        // if last_node is end_node, the program is finished
        if (this.last_node == this.end_node){
            return true;
        }

        // adds the nodes around to the stack with the correct depth
        int current_depth = last_node.get_depth();
        Node[] nodes_around = this.get_nodes_around(this.last_node, false);
        if (nodes_around != null){
            for (Node n : nodes_around){
                n.set_depth(current_depth + 1);
                fifo.add(n);
            }
        }
        return false;
    }

    /**
     * Performs one step of Dijkstra solving.
     * @return true if finished, false otherwise
     */
    private boolean dijkstra_next_step(){
        // if the list is empty, program is finished
        if (fifo.isEmpty()){
            return true;
        }

        // gets the smallest depth unvisited node in fifo
        Node best_node = fifo.getLast();
        // best score is init to the worst score possible times two
        int best_score = this.maze.get_size()[0] * this.maze.get_size()[1] * 2;
        int score;
        int fifo_size = fifo.size();
        boolean unvisited = false;
        for (int i = 0; i < fifo_size; i++){
            if (!(fifo.get(i).is_visited())){
                unvisited = true;
                score = fifo.get(i).get_depth();
                if (score < best_score){
                    best_node = fifo.get(i);
                    best_score = score;
                }
            }
        }
        this.last_node = best_node;

        // sets the current node to visited
        this.last_node.setMark("V");

        // if last_node is end_node, the program is finished
        // if no nodes are unvisited, the program is finished, no solutions found
        if ((this.last_node == this.end_node) || (!unvisited)){
            return true;
        }

        // updates the depth of the visited nodes around if necessary
        int current_depth = last_node.get_depth();
        Node[] visited_nodes_around = this.get_nodes_around(this.last_node, true);
        if (visited_nodes_around != null){
            for (Node n_v : visited_nodes_around){
                for (int i = 0; i < fifo.size(); i++) {
                    if (n_v == fifo.get(i)){
                        if (n_v.get_depth() > current_depth + 1){
                            n_v.set_depth(current_depth + 1);
                        }
                    }
                }
            }
        }

        // adds the unvisited nodes around to the stack with the correct depth
        Node[] unvisited_nodes_around = this.get_nodes_around(this.last_node, false);
        if (unvisited_nodes_around != null){
            for (Node n : unvisited_nodes_around){
                n.set_depth(current_depth + 1);
                fifo.add(n);
            }
        }
        return false;
    }
}