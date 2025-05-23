import java.util.*;
/**
 * Solver class provides different algorithms to solve a maze:
 * - BFS (Breadth-First Search)
 * - DFS (Depth-First Search)
 * - A* algorithm
 * - Dijkstra algorithm
 * - Wall follower (left and right)
 *
 * It also tracks the time taken for each solving attempt.
 */

public class Solver {
    private long start_time;
    private long stop_time;
    private long duration;

    /**
     * Default constructor initializes timer variables.
     */
    public Solver() {
        this.start_time = 0;
        this.stop_time = 0;
        this.duration = 0;
    }

    /**
     * Solve the maze using Breadth-First Search (BFS).
     * Marks visited nodes and the final path with "V" and "B" respectively.
     *
     * @param maze maze to solve
     * @return true if path found, false otherwise
     */
    public boolean bfs(Maze maze) {
        maze.clearMarks();
        this.start_time = System.nanoTime();

        Node start = maze.getStartNode();
        Node end = maze.getEndNode();

        Queue<Node> queue = new LinkedList<>();
        Map<Node, Node> parent = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        start.setMark("V");

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            current.setMark("V");

            if (current == end) {
                Node n = end;
                while (n != start) {
                    n.setPath(true);
                    n.setMark("B");
                    n = parent.get(n);
                }
                start.setPath(true);
                start.setMark("B");
                this.stop_time = System.nanoTime();
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
        this.stop_time = System.nanoTime();
        return false;
    }

    /**
     * Solve the maze using Depth-First Search (DFS).
     * Marks visited nodes and the final path with "V" and "D" respectively.
     *
     * @param maze maze to solve
     * @return true if path found, false otherwise
     */
    public boolean dfs(Maze maze) {
        maze.clearMarks();
        this.start_time = System.nanoTime();

        Node start = maze.getStartNode();
        Node end = maze.getEndNode();

        Stack<Node> stack = new Stack<>();
        Map<Node, Node> parent = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        stack.push(start);
        visited.add(start);
        start.setMark("V");

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            current.setMark("V");

            if (current == end) {
                Node n = end;
                while (n != start) {
                    n.setPath(true);
                    n.setMark("D");
                    n = parent.get(n);
                }
                start.setPath(true);
                start.setMark("D");
                this.stop_time = System.nanoTime();
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

        this.stop_time = System.nanoTime();
        return false;
    }


    /**
     * Solve the maze using A* algorithm.
     * Combines BFS with heuristic (Manhattan distance).
     * Marks visited nodes and final path with "V" and "A" respectively.
     *
     * @param maze maze to solve
     * @return true if path found, false otherwise
     */

    public boolean aStar(Maze maze) {
        maze.clearMarks();
        this.start_time = System.nanoTime();

        Node start = maze.getStartNode();
        Node end = maze.getEndNode();

        Map<Node, Integer> gScore = new HashMap<>();
        Map<Node, Node> parent = new HashMap<>();
        Set<Node> closedSet = new HashSet<>();

        Comparator<Node> comparator = (n1, n2) -> {
            int f1 = gScore.getOrDefault(n1, Integer.MAX_VALUE) + Node.manhattanDistance(n1, end);
            int f2 = gScore.getOrDefault(n2, Integer.MAX_VALUE) + Node.manhattanDistance(n2, end);
            return Integer.compare(f1, f2);
        };

        PriorityQueue<Node> openSet = new PriorityQueue<>(comparator);

        gScore.put(start, 0);
        openSet.add(start);
        start.setMark("V");

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            current.setMark("V");

            if (current == end) {
                Node n = end;
                while (n != start) {
                    n.setPath(true);
                    n.setMark("A");
                    n = parent.get(n);
                }
                start.setPath(true);
                start.setMark("A");
                // stop timer
                this.stop_time = System.nanoTime();
                return true;
            }

            closedSet.add(current);

            for (Node neighbor : maze.get_adjacency_list().getOrDefault(current, new ArrayList<>())) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentative_gScore = gScore.getOrDefault(current, Integer.MAX_VALUE) + 1;

                if (!gScore.containsKey(neighbor) || tentative_gScore < gScore.get(neighbor)) {
                    parent.put(neighbor, current);
                    gScore.put(neighbor, tentative_gScore);
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        this.stop_time = System.nanoTime();
        return false;
    }

    /**
     * Solve the maze using Dijkstra’s algorithm.
     * Marks visited nodes and final path with "V" and "K" respectively.
     *
     * @param maze maze to solve
     * @return true if path found, false otherwise
     */
    public boolean dijkstra(Maze maze) {
        maze.clearMarks();

        this.start_time = System.nanoTime();

        Node start = maze.getStartNode();
        Node end = maze.getEndNode();

        Map<Node, Integer> dist = new HashMap<>();
        Map<Node, Node> parent = new HashMap<>();

        Comparator<Node> comparator = Comparator.comparingInt(node -> dist.get(node));

        PriorityQueue<Node> queue = new PriorityQueue<>(comparator);

        for (Node node : maze.get_adjacency_list().keySet()) {
            dist.put(node, Integer.MAX_VALUE);
        }
        dist.put(start, 0);

        queue.add(start);
        start.setMark("V");

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.equals(end)) {
                Node n = end;
                while (!n.equals(start)) {
                    n.setPath(true);
                    n.setMark("K");
                    n = parent.get(n);
                }
                start.setPath(true);
                start.setMark("K");
                this.stop_time = System.nanoTime();
                return true;
            }

            for (Node neighbor : maze.get_adjacency_list().getOrDefault(current, Collections.emptyList())) {
                int alt = dist.get(current) + 1;
                if (alt < dist.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    dist.put(neighbor, alt);
                    parent.put(neighbor, current);
                    if (queue.contains(neighbor)) {
                        queue.remove(neighbor);
                    }
                    queue.add(neighbor);

                    neighbor.setMark("V");
                }
            }
        }

        this.stop_time = System.nanoTime();
        return false;
    }

    /**
     * Solve the maze with wall follower algorithm (left-hand rule).
     * Marks path with "L" and visited nodes with "V".
     *
     * @param maze maze to solve
     * @return true if path found, false otherwise
     */
    public boolean wallFollowerLeft(Maze maze) {
        maze.clearMarks();
        this.start_time = System.nanoTime();

        Node start = maze.getStartNode();
        Node end = maze.getEndNode();

        int[][] direction = {
                {-1, 0}, //Up
                {0, 1}, //Right
                {1, 0}, //Down
                {0, -1} // Left
        };

        Node current = start;
        int dir = 3; // start looking left

        Map<Node, Node> parent = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        visited.add(current);

        current.setMark("V");

        int maxSteps = maze.get_node_array().length * maze.get_node_array()[0].length * 10; // limite de sécurité pour éviter boucle infini
        int steps = 0;

        while (!current.equals(end) && steps < maxSteps) {
            steps++;

            boolean moved = false;

            for (int i = 0; i < 4; i++) {
                int tryDirection = (dir + 1 + 4 - i) % 4; // try right, forward, left, back
                int nx = current.get_coordinates()[0] + direction[tryDirection][0];
                int ny = current.get_coordinates()[1] + direction[tryDirection][1];

                if (nx >= 0 && ny >= 0 && nx < maze.get_node_array().length && ny < maze.get_node_array()[0].length) {
                    Node neighbor = maze.get_node_array()[nx][ny];
                    if (maze.get_adjacency_list().getOrDefault(current, new ArrayList<>()).contains(neighbor)) {
                        if (!visited.contains(neighbor)) {
                            parent.put(neighbor, current);
                        }
                        current = neighbor;
                        visited.add(current);
                        dir = tryDirection;

                        current.setMark("V");

                        moved = true;
                        break;
                    }
                }
            }

            if (!moved) {
                this.stop_time = System.nanoTime();
                return false;
            }
        }

        if (current.equals(end)) {
            Node n = end;
            while (n != start) {
                n.setPath(true);
                n.setMark("L");
                n = parent.get(n);
            }
            start.setPath(true);
            start.setMark("L");
            this.stop_time = System.nanoTime();
            return true;
        }

        this.stop_time = System.nanoTime();
        return current.equals(end);
    }


    /**
     * Solve the maze with wall follower algorithm (right-hand rule).
     * Marks path with "R" and visited nodes with "V".
     *
     * @param maze maze to solve
     * @return true if path found, false otherwise
     */
    public boolean wallFollowerRight(Maze maze) {
        maze.clearMarks();

        this.start_time = System.nanoTime();

        Node start = maze.getStartNode();
        Node end = maze.getEndNode();


        int[][] direction = {
                {-1, 0}, //Up
                {0, 1}, //Right
                {1, 0}, //Down
                {0, -1} //Left
        };

        Node current = start;
        int dir = 1; // start looking right

        Map<Node, Node> parent = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        visited.add(current);

        current.setMark("V");

        int maxSteps = maze.get_node_array().length * maze.get_node_array()[0].length * 10; // limite de sécurité pour éviter boucle infini
        int steps = 0;

        while (!current.equals(end) && steps < maxSteps) {
            steps++;

            boolean moved = false;

            for (int i = 0; i < 4; i++) {
                int tryDir = (dir + 3 + i) % 4; // try left, forward, right, back
                int nx = current.get_coordinates()[0] + direction[tryDir][0];
                int ny = current.get_coordinates()[1] + direction[tryDir][1];

                if (nx >= 0 && ny >= 0 && nx < maze.get_node_array().length && ny < maze.get_node_array()[0].length) {
                    Node neighbor = maze.get_node_array()[nx][ny];
                    if (maze.get_adjacency_list().getOrDefault(current, new ArrayList<>()).contains(neighbor)) {
                        if (!visited.contains(neighbor)) {
                            parent.put(neighbor, current);
                        }
                        current = neighbor;
                        visited.add(current);
                        dir = tryDir;

                        current.setMark("V");

                        moved = true;
                        break;
                    }
                }
            }

            if (!moved) {
                this.stop_time = System.nanoTime();
                return false;
            }
        }

        if (current.equals(end)) {
            Node n = end;
            while (n != start) {
                n.setPath(true);
                n.setMark("R");
                n = parent.get(n);
            }
            start.setPath(true);
            start.setMark("R");
            this.stop_time = System.nanoTime();
            return true;
        }

        this.stop_time = System.nanoTime();
        return current.equals(end);
    }

    /**
     * Returns the duration of last solve in milliseconds.
     *
     * @return duration in ms
     */
    public long get_time_ms(){
        this.duration = (this.stop_time - this.start_time) / 1000000;
        return this.duration;
    }
}