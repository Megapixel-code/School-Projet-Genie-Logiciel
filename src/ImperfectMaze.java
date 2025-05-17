import java.util.*;

public class ImperfectMaze extends PerfectMaze {

    public ImperfectMaze(int x, int y, int seed, Node start, Node end) {
        super(x, y, seed, start, end);
        this.generate();

        int maxBreaks = (getSize_x() * getSize_y()) / 10; // on casse 10% des murs du laby
        this.breakRandomWalls(maxBreaks);
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

    private boolean edgeExists(Node a, Node b) {
        for (Edge e : get_edge_list()) {
            if (e.connected(a,b)) {
                return true;
            }
        }
        return false;
    }

}
