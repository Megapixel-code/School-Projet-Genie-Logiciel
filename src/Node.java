class Node {
    /**
     * This class represents a node (or cell) in the maze.
     *
     * Each node has 2D coordinates (pos_x, pos_y),
     * a depth value used to show solving path,
     * a boolean to mark if it is part of the path,
     * and a string mark used by the solver.
     */
    private int depth;
    private boolean path;
    private String mark_Solver = null;
    private final int pos_x;
    private final int pos_y;

    /**
     * Default constructor creates a node with invalid coordinates (-1, -1)
     * and depth set to -1 (meaning no depth assigned).
     */
    public Node(){
        this.pos_x = -1;
        this.pos_y = -1;
        this.depth = -1;
    }

    /**
     * Constructor creates a node with given coordinates.
     * Depth is set to -1 by default.
     *
     * @param x x-coordinate of the node
     * @param y y-coordinate of the node
     */
    public Node(int x, int y){
        this.pos_x = x;
        this.pos_y = y;
        this.depth = -1;
    }

    /**
     * Returns the coordinates of the node as an int array [x, y].
     *
     * @return array of two integers representing the node coordinates
     */
    public int[] get_coordinates(){
        int[] pos = {this.pos_x, this.pos_y};
        return pos;
    }

    /**
     * Returns the depth value of the node.
     * Used to mark the solving path depth.
     *
     * @return depth value
     */
    public int get_depth(){
        return this.depth;
    }

    /**
     * Sets the depth value of the node.
     *
     * @param depth new depth to set
     */
    public void set_depth(int depth){
        this.depth = depth;
    }

    /**
     * Sets whether this node is part of the solving path.
     *
     * @param b true if node is part of path, false otherwise
     */
    public void setPath(boolean b) {
        this.path = b;
    }

    /**
     * Returns whether this node is part of the solving path.
     *
     * @return true if part of path, false otherwise
     */
    public boolean isPath() {
        return this.path;
    }

    /**
     * Returns true if this node has been visited by the solver,
     * meaning it has a mark set.
     *
     * @return true if visited, false otherwise
     */
    public boolean is_visited(){
        return (this.mark_Solver != null);
    }

    /**
     * Sets a mark string for the solver.
     *
     * @param mark string mark to set
     */
    public void setMark(String mark) {
        this.mark_Solver = mark;
    }

    /**
     * Resets the solver mark to null (unvisited).
     */
    public void resetMark(){
        this.mark_Solver = null;
    }

    /**
     * Returns the current solver mark string.
     *
     * @return mark string or null if none
     */
    public String getMark() {
        return this.mark_Solver;
    }

    /**
     * Returns an array of neighbor nodes (up to 4) around this node in the grid.
     * Order is: right, left, down, up.
     * Neighbors outside grid bounds are set to null.
     *
     * @param grille 2D Node array representing the maze grid
     * @return array of neighbor nodes [right, left, down, up]
     */
    public Node[] get_neighbours(Node[][] grille) {

        int[] coord = this.get_coordinates();
        int x = coord[0];
        int y = coord[1];

        Node[] neighbours = new Node[4];

        // Right
        if (x + 1 < grille.length)
            neighbours[0] = grille[x + 1][y];

        // Left
        if (x - 1 >= 0)
            neighbours[1] = grille[x - 1][y];

        // Down
        if (y + 1 < grille[0].length)
            neighbours[2] = grille[x][y + 1];

        // Up
        if (y - 1 >= 0)
            neighbours[3] = grille[x][y - 1];

        return neighbours;
    }

    /**
     * Static method to calculate Manhattan distance between two nodes.
     * Distance is |x1 - x2| + |y1 - y2|.
     * This is useful for heuristic in A* pathfinding.
     *
     * @param a first node
     * @param b second node
     * @return Manhattan distance between a and b
     */
    public static int manhattanDistance(Node a, Node b) {
        int[] aCoord = a.get_coordinates();
        int[] bCoord = b.get_coordinates();
        return Math.abs(aCoord[0] - bCoord[0]) + Math.abs(aCoord[1] - bCoord[1]);
    }
    /*
     * Note: No setter for coordinates because nodes should keep their position constant.
     */
}