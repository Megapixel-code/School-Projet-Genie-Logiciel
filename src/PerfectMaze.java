import java.util.*;


class PerfectMaze extends Maze {
    private Node last_node;

    public PerfectMaze(int x, int y, int seed, int[] start, int[] end) {
        super(x, y, seed, start[0], start[1], end[0], end[1]);
        this.last_node = super.get_node(start[0], start[1]);
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

        // put available node in a array
        // available nodes exists and are not yet visited (depth == -1)
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
                // program finished
                return true;
            }
            else {
                // go back one node, the node with a current_depth-1
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
            // if there is available nodes around
            // chose a available node around randomly then create edge between the curent node and the random node
            int random_nb = this.rng.nextInt(nb_available_nodes);
            Edge new_edge = new Edge(this.last_node, availables_nodes[random_nb]);
            this.add_edge(new_edge);
            this.last_node = availables_nodes[random_nb];
            this.last_node.set_depth(current_depth + 1);
        }
        return false;
    }
    //mode complet pour bfs
    public void generateBFS() {
        while (!this.bfs_next_step()) { // seulement quand c'est true tu affiches
        }
    }

    public void generateKruskal() {
        /*
         * la methode c'est de mettre une profondeur unique a chaque node
         * apres tant qu'il existe deux valeur de profondeur différentes on fait :
         *      on prend un node on regarde un des voisins si ils ont une valeur diff on crée un edge entre eux et on uniformise la valeur des nodes de l'ilot
         * et voila !
         */
        Node[][] grid = get_node_array();
        int sizeX = getSize_x();
        int sizeY = getSize_y();
        Random rng = get_rng();

        // chaque Node reçoit une profondeur unique
        int ind = 0;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                Node node = grid[x][y];
                node.set_depth(ind++); // chaque node a une profondeur unique
            }
        }

        // Répéter tant que plusieurs profondeurs différentes existent
        boolean mazeComplete = false;
        while (!mazeComplete) {
            // Tirer un noeud aléatoire
            int x = rng.nextInt(sizeX);
            int y = rng.nextInt(sizeY);
            Node current = grid[x][y];
            int currentDepth = current.get_depth();

            // Récupérer voisins adjacents
            Node[] neighbors = current.get_neighbours(grid);
            List<Node> neighborList = new ArrayList<>();
            for (Node n : neighbors) {
                if (n != null && n.get_depth() != currentDepth) {
                    neighborList.add(n);
                }
            }

            // Si on a des voisins de profondeur différente
            if (!neighborList.isEmpty()) {
                Node neighbor = neighborList.get(rng.nextInt(neighborList.size()));
                int neighborDepth = neighbor.get_depth();

                // Connecter les deux noeuds
                add_edge(new Edge(current, neighbor));

                // Uniformiser tous les noeuds de l'ancien îlot avec la nouvelle profondeur
                for (int i = 0; i < sizeX; i++) {
                    for (int j = 0; j < sizeY; j++) {
                        if (grid[i][j].get_depth() == neighborDepth) {
                            grid[i][j].set_depth(currentDepth);
                        }
                    }
                }
            }

            // Vérifier si tous les noeuds ont la même profondeur
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
