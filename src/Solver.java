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

}
