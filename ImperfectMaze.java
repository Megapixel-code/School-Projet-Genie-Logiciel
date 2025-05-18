import java.util.*;

public class ImperfectMaze extends PerfectMaze {

    public ImperfectMaze(int x, int y, int seed, int start_x, int start_y, int end_x, int end_y) {
        super(x, y, seed, start_x, start_y, end_x, end_y);
        this.generateBFS();

        // Calcule nombre total de cases
        int maxWalls = getSize_x() * getSize_y();

// Définir une plage avec un minimum et un maximum différent
        int minModif = Math.max(1, maxWalls / 30);
        int maxModif = minModif + Math.max(1, maxWalls / 30); // ici maxModif > minModif

        Random rng = get_rng();

        int toRemove = minModif + rng.nextInt(maxModif - minModif + 1);
        int toAdd = minModif + rng.nextInt(maxModif - minModif + 1);

        System.out.println("\u001B[33mSuppression aléatoire de " + toRemove + " murs.\u001B[0m");
        removeRandomWalls(toRemove);

        System.out.println("\u001B[33mAjout aléatoire de " + toAdd + " murs.\u001B[0m");
        addRandomWalls(toAdd);
    }

    public void removeRandomWalls(int max) {
        int removed = 0;
        Random rng = get_rng();
        int sizeX = getSize_x();
        int sizeY = getSize_y();

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

            boolean addedEdge = false;
            for (Node neighbor : neighborList) {
                if (!edgeExists(node, neighbor)) {
                    addEdgeBetween(node, neighbor);
                    removed++;
                    addedEdge = true;
                    break;
                }
            }
            if (!addedEdge) {
                // continue
            }
        }
    }

    public void addRandomWalls(int max) {
        int added = 0;
        Random rng = get_rng();
        int sizeX = getSize_x();
        int sizeY = getSize_y();

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

            boolean removedEdge = false;
            for (Node neighbor : neighborList) {
                if (edgeExists(node, neighbor)) {
                    removeEdgeBetween(node, neighbor);
                    added++;
                    removedEdge = true;
                    break;
                }
            }
            if (!removedEdge) {
                // continue
            }
        }
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
    }

    private boolean edgeExists(Node a, Node b) {
        for (Edge e : get_edge_list()) {
            if (e.connected(a, b)) {
                return true;
            }
        }
        return false;
    }
}
