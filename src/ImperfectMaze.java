import java.util.*;

public class ImperfectMaze extends PerfectMaze {

    public ImperfectMaze(int x, int y, int seed, int start_x, int start_y, int end_x, int end_y) {
        super(x, y, seed, start_x, start_y, end_x, end_y);;
        this.generate();

        int maxWalls = getSize_x() * getSize_y(); // on casse 10% des murs du laby

        boolean break_build = get_rng().nextBoolean(); // true on casse false on ajoute
        System.out.println("\u001B[33mBreak or Build walls ? => " + (break_build ? "break" : "add" + "\u001B[0m"));

        if (break_build) {
            this.breakRandomWalls(maxWalls /10); // crée des cycles
        } else {
            this.addRandomWalls(maxWalls/30);   // peut bloquer des chemins
        }
    }

    private void breakRandomWalls(int max) {
        int broken = 0;

        while (broken < max) {
            int x = get_rng().nextInt(getSize_x());
            int y = get_rng().nextInt(getSize_y());
            Node node = get_node(x, y);

            Node[] neighbors = node.get_neighbours(get_node_array());
            List<Node> neighborList = Arrays.asList(neighbors);
            Collections.shuffle(neighborList, get_rng());

            for (Node neighbor : neighborList) {
                if (neighbor != null && !edgeExists(node, neighbor)) {
                    this.add_edge(new Edge(node, neighbor));
                    broken++;
                    break;
                }
            }
        }
    }

    private void addRandomWalls(int max) {
        int build = 0;

        while (build < max) {
            int x = get_rng().nextInt(getSize_x());
            int y = get_rng().nextInt(getSize_y());
            Node node = get_node(x, y);

            Node[] neighbors = node.get_neighbours(get_node_array());
            List<Node> neighborList = Arrays.asList(neighbors);
            Collections.shuffle(neighborList, get_rng());

            for (Node neighbor : neighborList) {
                if (neighbor != null && edgeExists(node, neighbor)) {
                    // Supprimer l'arête
                    get_edge_list().removeIf(e -> e.connected(node, neighbor));

                    get_adjacency_list().get(node).remove(neighbor);
                    get_adjacency_list().get(neighbor).remove(node);

                    build++;
                    break;
                }
            }
        }
    }



    private boolean edgeExists(Node a, Node b) {
        for (Edge e : get_edge_list()) {
            if (e.connected(a,b)) {
                return true;
            }
        }
        return false;
    }

}
