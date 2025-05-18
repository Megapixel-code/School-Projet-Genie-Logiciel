
import java.util.*;

public class Solver {

    public static boolean bfs(Maze maze) {
        Node start = maze.getStartNode();
        Node end = maze.getEndNode();

        Queue<Node> queue = new LinkedList<>();
        Map<Node, Node> parent = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current == end) {
                // Chemin trouvé
                Node n = end;
                while (n != start) {
                    n.setPath(true); // marque les noeuds du chemin
                    n.setMark("B"); //pour affichage BFS
                    n = parent.get(n);
                }
                start.setPath(true);
                start.setMark("B");
                return true;
            }

            for (Node neighbor : maze.get_adjacency_list().getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return false; // Aucun chemin trouvé
    }

    public static boolean dfs(Maze maze) {
        Node start = maze.getStartNode();
        Node end = maze.getEndNode();

        Stack<Node> stack = new Stack<>();
        Map<Node, Node> parent = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            Node current = stack.pop();

            if (current == end) {
                // Chemin trouvé
                Node n = end;
                while (n != start) {
                    n.setPath(true);
                    n.setMark("D"); // pour affichage DFS
                    n = parent.get(n);
                }
                start.setPath(true);
                start.setMark("D");
                return true;
            }

            for (Node neighbor : maze.get_adjacency_list().getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    stack.push(neighbor);
                }
            }
        }

        return false; // Aucun chemin trouvé
    }

    /*
     * A* algo de recherche qui combine une recherche en largeur (bfs) avec une heuristique qui guide la recherche vers la cible
     * fonctionnement :
     * on a une liste avec les noeuds a explorer et une liste avec les noeuds deja explorés
     * chaque noeud a une distance g (distance depuis le depart) et une distance estimée h (distance a l'arrivée) (ca correspond a la distance de Manhattan)
     * jsp pk on utilise cette distance mais c'est comme ca alors je le fais aussi
     * on pose f = g + h et f permettera d'orienter la recherche
     * enfin on remonte le chemin en partant de l'arrivée avec les parents
     */
    // Utilise une PriorityQueue avec priorité basée sur f = g + h
    // g : distance réelle depuis le départ
    // h : heuristique (distance de Manhattan vers la cible)
    public static boolean aStar(Maze maze) {
        Node start = maze.getStartNode();
        Node end = maze.getEndNode();

        // Map pour stocker g_score : coût réel minimum depuis start à chaque noeud
        Map<Node, Integer> gScore = new HashMap<>();
        // Map pour stocker les parents afin de reconstruire le chemin
        Map<Node, Node> parent = new HashMap<>();
        // Set pour garder les noeuds déjà évalués
        Set<Node> closedSet = new HashSet<>();

        // Comparator pour la PriorityQueue basé sur f = g + h
        Comparator<Node> comparator = (n1, n2) -> {
            int f1 = gScore.getOrDefault(n1, Integer.MAX_VALUE) + Node.manhattanDistance(n1, end);
            int f2 = gScore.getOrDefault(n2, Integer.MAX_VALUE) + Node.manhattanDistance(n2, end);
            return Integer.compare(f1, f2);
        };

        PriorityQueue<Node> openSet = new PriorityQueue<>(comparator);

        gScore.put(start, 0);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current == end) {
                // Chemin trouvé, reconstruire et marquer
                Node n = end;
                while (n != start) {
                    n.setPath(true);
                    n.setMark("A"); // A* marqué par "A"
                    n = parent.get(n);
                }
                start.setPath(true);
                start.setMark("A");
                return true;
            }

            closedSet.add(current);

            for (Node neighbor : maze.get_adjacency_list().getOrDefault(current, new ArrayList<>())) {
                if (closedSet.contains(neighbor)) {
                    continue; // Déjà traité
                }

                int tentative_gScore = gScore.getOrDefault(current, Integer.MAX_VALUE) + 1; // coût = 1 pour chaque déplacement

                if (!gScore.containsKey(neighbor) || tentative_gScore < gScore.get(neighbor)) {
                    parent.put(neighbor, current);
                    gScore.put(neighbor, tentative_gScore);
                    // Si le voisin n'est pas dans openSet, on l'ajoute
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return false; // Aucun chemin trouvé
    }

    // Résolution avec l’algorithme de Dijkstra
    public static boolean dijkstra(Maze maze) {
        Node start = maze.getStartNode();
        Node end = maze.getEndNode();

        // Map pour stocker la distance minimale depuis le départ à chaque noeud
        Map<Node, Integer> dist = new HashMap<>();
        // Map pour stocker le parent de chaque noeud dans le chemin le plus court
        Map<Node, Node> parent = new HashMap<>();

        // Comparateur pour la PriorityQueue selon la distance
        Comparator<Node> comparator = Comparator.comparingInt(node -> dist.get(node));


        PriorityQueue<Node> queue = new PriorityQueue<>(comparator);

        // Initialisation : distance infinie sauf pour start
        for (Node node : maze.get_adjacency_list().keySet()) {
            dist.put(node, Integer.MAX_VALUE);
        }
        dist.put(start, 0);

        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // Si on atteint la fin, on peut reconstruire le chemin
            if (current.equals(end)) {
                Node n = end;
                while (!n.equals(start)) {
                    n.setPath(true);
                    n.setMark("K"); // D pour Dijkstra
                    n = parent.get(n);
                }
                start.setPath(true);
                start.setMark("D");
                return true;
            }

            // Pour chaque voisin du noeud courant
            for (Node neighbor : maze.get_adjacency_list().getOrDefault(current, Collections.emptyList())) {
                int alt = dist.get(current) + 1; // poids uniforme = 1 par arête
                if (alt < dist.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    dist.put(neighbor, alt);
                    parent.put(neighbor, current);
                    // Si la queue contient déjà neighbor, on doit le retirer et le réajouter pour mettre à jour sa priorité
                    if (queue.contains(neighbor)) {
                        queue.remove(neighbor);
                    }
                    queue.add(neighbor);
                }
            }
        }

        // Aucun chemin trouvé
        return false;
    }
}

