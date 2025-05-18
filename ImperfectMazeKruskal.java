import java.util.*;

public class ImperfectMazeKruskal extends PerfectMaze {

    public ImperfectMazeKruskal(int x, int y, int seed, int start_x, int start_y, int end_x, int end_y) {
        super(x, y, seed, start_x, start_y, end_x, end_y);

        // Génération parfaite avec Kruskal
        this.generateKruskal();

        int maxWalls = getSize_x() * getSize_y();

        int minModif = Math.max(1, maxWalls / 30);
        int maxModif = minModif + Math.max(1, maxWalls / 30);

        Random rng = get_rng();

        int toRemove = minModif + rng.nextInt(maxModif - minModif + 1); // murs à casser = ajouter des cycles
        int toAdd = minModif + rng.nextInt(maxModif - minModif + 1);    // murs à rajouter

        System.out.println("\u001B[33mSuppression aléatoire de " + toRemove + " murs.\u001B[0m");
        removeRandomWalls(toRemove);

        System.out.println("\u001B[33mAjout aléatoire de " + toAdd + " murs.\u001B[0m");
        addRandomWalls(toAdd);
    }

    // Supprime des murs (ajoute des arêtes) uniquement entre voisins non connectés
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

            // On ne garde que les voisins adjacents sans arête (mur existant)
            for (Node n : neighbors) {
                if (n != null && !edgeExists(node, n)) {
                    neighborList.add(n);
                }
            }

            if (neighborList.isEmpty()) {
                continue; // pas de mur à casser ici
            }

            Collections.shuffle(neighborList, rng);

            for (Node neighbor : neighborList) {
                addEdgeBetween(node, neighbor); // casse un mur
                removed++;
                break;
            }
        }
    }

    // Ajoute des murs (supprime des arêtes) uniquement entre voisins connectés
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

            // On ne garde que les voisins adjacents avec arête (passage)
            for (Node n : neighbors) {
                if (n != null && edgeExists(node, n)) {
                    neighborList.add(n);
                }
            }

            if (neighborList.isEmpty()) {
                continue; // pas de mur à ajouter ici
            }

            Collections.shuffle(neighborList, rng);

            for (Node neighbor : neighborList) {
                removeEdgeBetween(node, neighbor); // pose un mur
                added++;
                break;
            }
        }
    }

    // Supprime une arête (mur)
    public void removeEdgeBetween(Node a, Node b) {
        get_edge_list().removeIf(e -> e.connected(a, b));

        if (get_adjacency_list().containsKey(a)) {
            get_adjacency_list().get(a).remove(b);
        }
        if (get_adjacency_list().containsKey(b)) {
            get_adjacency_list().get(b).remove(a);
        }
    }

    // Ajoute une arête (casser un mur)
    public void addEdgeBetween(Node a, Node b) {
        int dx = Math.abs(a.get_coordinates()[0] - b.get_coordinates()[0]);
        int dy = Math.abs(a.get_coordinates()[1] - b.get_coordinates()[1]);
        if (dx + dy != 1) {
            System.out.println("Erreur : Les noeuds ne sont pas adjacents, impossible d'ajouter un mur.");
            return;
        }
        if (edgeExists(a, b)) {
            return; // arête déjà existante
        }
        add_edge(new Edge(a, b));
    }

    // Vérifie si une arête existe entre deux noeuds
    private boolean edgeExists(Node a, Node b) {
        for (Edge e : get_edge_list()) {
            if (e.connected(a, b)) {
                return true;
            }
        }
        return false;
    }
}
