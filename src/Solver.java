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
}
