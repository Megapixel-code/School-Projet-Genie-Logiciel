
import java.util.Arrays;
import java.util.Random;



class PerfectMaze extends Maze {
    private Node last_node;

    public PerfectMaze(int x, int y, int seed){
        /* 
         * init the perfect maze
         * same as the maze class but with a different seed
         */
        super(x, y, seed);
        this.last_node = super.get_node(0, 0);
        this.last_node.set_depth(0);
    }

    public PerfectMaze(int x, int y, int seed, int start_x, int start_y){
        super(x, y, seed);
        this.last_node = super.get_node(start_x, start_y);
        this.last_node.set_depth(0);
    }

    public Node get_last_node(){
        // self explainatory
        return this.last_node;
    }

    public boolean bfs_next_step(){
        /*
         * returns true if finished,
         * finished if depth equals zero and no unvisited node around
         */ 
        int[] current_pos = last_node.get_coordinates();
        int current_depth = last_node.get_depth();
        // array of the four node around, will be null if node doesnt exist
        Node[] nodes_around = new Node[4];
        nodes_around[0] = super.get_node(current_pos[0]+1, current_pos[1]);
        nodes_around[1] = super.get_node(current_pos[0]-1, current_pos[1]);
        nodes_around[2] = super.get_node(current_pos[0], current_pos[1]+1);
        nodes_around[3] = super.get_node(current_pos[0], current_pos[1]-1);

        // put available node in a array, available nodes exists and are not yet visited (depth == -1)
        int nb_available_nodes = 0;
        for (int k = 0; k < 4; k++){
            if ((nodes_around[k] != null) && (nodes_around[k].get_depth() == -1)){
                nodes_around[nb_available_nodes++] = nodes_around[k];
            }
        }
        Node[] availables_nodes = Arrays.copyOf(nodes_around, nb_available_nodes);

        if (nb_available_nodes == 0){
            if (current_depth == 0){
                return true;
                // program finished
            }
            else {
                for (Node n : nodes_around) {
                    if (n != null){
                        if (n.get_depth() == current_depth-1){
                            this.last_node = n;
                        }
                    }
                }
                // go back one node
            }
        }
        else {
            // chose a available node around randomly then create edge between the curent node and the random node
            int random_nb = new Random().nextInt(nb_available_nodes);
            Edge new_edge = new Edge(this.last_node, availables_nodes[random_nb]);
            this.add_edge(new_edge);
            this.last_node = availables_nodes[random_nb];
            this.last_node.set_depth(current_depth + 1);
        }
        return false;
    }
}
