import java.util.*;


public class SolverSbS {
    /*
     * solver_type :
     * 0 = dfs 
     */ 
    private int solver_type;
    
    private Maze maze;

    private Node last_node;
    private Node start_node;
    private Node end_node;
    
    private Stack<Node> stack = new Stack<>();


    public SolverSbS(Maze m){
        // if type is not given, will solve with dfs
        this(m, "dfs");
    }

    public SolverSbS(Maze m, String type){
        this.maze = m;
        this.last_node = m.getStartNode();
        this.start_node = m.getStartNode();
        this.end_node = m.getEndNode();
        
        switch (type) {
            // Note : /!\ mettre a jour get_types, next_step, set_correct_node_mark /!\
            case "dfs" -> {
                this.solver_type = 0;
                this.stack.push(this.start_node);
            }
            default -> throw new IllegalArgumentException("Invalid type for sbs solver : you entered type = \"" + type + "\"");
        }

        this.reset_nodes();
        this.start_node.set_depth(0);
    }

    public String[] get_types(){
        /*
         * returns the types of solve available, is used for the display
         */
        String[] types = {"dfs"};
        return types;
    }

    public boolean next_step(){
        /*
         * will call the correct next step solve depending on the solver type
         */
        switch (this.solver_type) {
            case 0 -> {
                return dfs_next_step();
            }
            default -> throw new AssertionError();
        }
    }

    private void reset_nodes(){
        /*
         * sets all nodes :
         * depths to -1
         * path to false
         * mark_Solver to null
         */
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

    public boolean find_path_step(){
        /*
         * this function should be only called if the solution has been found
         * it will reveal the path one node at a time
         * returns true if the program is finished, false if not
         */

        // we find the next node not marked as path
        Node n = this.end_node;
        while (n.isPath()) { 
            n = get_node_depth_minus_one(n);
            if (n == null){
                // if n == null, 
                // either the maze has no solutions, 
                // or we called this function after having already found the path
                System.out.println("The maze has no solutions or you called this function too many times");
                return true;
            }
        }
        
        // we mark it as path
        n.setPath(true);
        this.set_correct_node_mark(n);
        // if it is the start node, the program is finished we return true, else it is'nt we return false
        return n == this.start_node;
    }

    private Node get_node_depth_minus_one(Node n){
        /*
         * the program will return the node around the node n who has a depth of n.get_depth() - 1
         * the node needs to be connected
         */

        int[] current_pos = n.get_coordinates();
        int current_depth = n.get_depth();

        // array of the four node around, will be null if node doesnt exist
        Node[] nodes_around = new Node[4];
        nodes_around[0] = this.maze.get_node(current_pos[0]+1, current_pos[1]);
        nodes_around[1] = this.maze.get_node(current_pos[0]-1, current_pos[1]);
        nodes_around[2] = this.maze.get_node(current_pos[0], current_pos[1]+1);
        nodes_around[3] = this.maze.get_node(current_pos[0], current_pos[1]-1);

        // makes sure there is a path between the two nodes
        for (int k = 0; k < 4; k++){
            if (nodes_around[k] != null){
                if (maze.in_edge_list(nodes_around[k], n) == -1){
                    nodes_around[k] = null;
                }
            }
        }

        // put available node in a array
        // available nodes exists and are already visited
        int nb_available_nodes = 0;
        for (int k = 0; k < 4; k++){
            if ((nodes_around[k] != null) && (nodes_around[k].is_visited())){
                nodes_around[nb_available_nodes] = nodes_around[k];
                nb_available_nodes++;
            }
        }
        // nodes is a array of the nodes who are already visited and are connected to the node n
        Node[] nodes = Arrays.copyOf(nodes_around, nb_available_nodes);

        // returns the node that has a depth of current_depth - 1
        for (Node n_loop : nodes){
            if (n_loop.get_depth() == current_depth - 1){
                return n_loop;
            }
        }
        // or null if there isnt
        return null;
    }

    private void set_correct_node_mark(Node n){
        switch (this.solver_type) {
            case 0 -> n.setMark("D");
            default -> throw new AssertionError();
        }
    }

    private Node[] get_unvisited_nodes_arround(Node n){
        /*
         * will return unvisited nodes around
         * if no node are unvisited, returns null
         */
        int[] current_pos = n.get_coordinates();

        // array of the four node around, will be null if node doesnt exist
        Node[] nodes_around = new Node[4];
        nodes_around[0] = this.maze.get_node(current_pos[0]+1, current_pos[1]);
        nodes_around[1] = this.maze.get_node(current_pos[0]-1, current_pos[1]);
        nodes_around[2] = this.maze.get_node(current_pos[0], current_pos[1]+1);
        nodes_around[3] = this.maze.get_node(current_pos[0], current_pos[1]-1);

        // makes sure there is a path between the two nodes
        for (int k = 0; k < 4; k++){
            if (nodes_around[k] != null){
                if (maze.in_edge_list(nodes_around[k], n) == -1){
                    nodes_around[k] = null;
                }
            }
        }

        // put available node in a array
        // available nodes exists and are not yet visited
        int nb_available_nodes = 0;
        for (int k = 0; k < 4; k++){
            if ((nodes_around[k] != null) && !(nodes_around[k].is_visited())){
                nodes_around[nb_available_nodes] = nodes_around[k];
                nb_available_nodes++;
            }
        }
        Node[] unvisited_nodes = Arrays.copyOf(nodes_around, nb_available_nodes);

        if (nb_available_nodes != 0){
            return unvisited_nodes;
        }
        else {
            return null;
        }
    }

    private boolean dfs_next_step(){
        /*
         * dfs next step
         */

        // gets the last unvisited node in stack
        this.last_node = stack.pop();
        while (this.last_node.is_visited()){
            // if the stack is empty, program is finished
            if (stack.isEmpty()){
                return true;
            }
            this.last_node = stack.pop();
        }

        // if last_node is end_node, the program is finished
        if (this.last_node == this.end_node){
            return true;
        }

        // set the current node to visited and adds the nodes around to the stack
        // with the correct depth
        this.last_node.setMark("V");
        int current_depth = last_node.get_depth();
        Node[] nodes_around = this.get_unvisited_nodes_arround(this.last_node);
        if (nodes_around != null){
            for (Node n : nodes_around){
                n.set_depth(current_depth + 1);
                stack.push(n);
            }
        }
        return false;
    }
}
