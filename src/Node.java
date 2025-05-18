import java.util.Objects;

class Node {
    /*
     * depth will show the solving path.
     * pos_x and pos_y are the 2d coordinates of the node
     */
    private int depth;
    private boolean path;
    private String mark_Solver = null;
    private int pos_x;
    private int pos_y;


    public Node(){
        /*
         * init the node
         * depth is set by default to minus one, same as no value here
         */
        this.pos_x = -1;
        this.pos_y = -1;
        this.depth = -1;
    }

    public Node(int x, int y){
        /*
         * init the node
         * depth is set by default to minus one, same as no value here
         */
        this.pos_x = x;
        this.pos_y = y;
        this.depth = -1;
    }

    public int[] get_coordinates(){
        // return a tuplet of the two coordinates, pos_x then pos_y
        int[] pos = {this.pos_x, this.pos_y};
        return pos;
    }

    public int get_depth(){
        // self explainatory
        return this.depth;
    }

    public void set_depth(int depth){
        // self explainatory
        this.depth = depth;
    }

    public void setPath(boolean b) {
        this.path = b;
    }

    public boolean isPath() {
        return this.path;
    }


    public void setMark(String mark) {
        this.mark_Solver = mark;
    }

    public String getMark() {
        return this.mark_Solver;
    }


    public Node[] get_neighbours(Node[][] grille) {
        // on return un tableau de node avec tous les voisins dispo
        //en parametre on met node_array et on utilise a.get_...(mazetest.node_array);

        int[] coord = this.get_coordinates();
        int x = coord[0];
        int y = coord[1];

        Node[] neighbours = new Node[4];

        // Droite
        if (x + 1 < grille.length)
            neighbours[0] = grille[x + 1][y];

        // Gauche
        if (x - 1 >= 0)
            neighbours[1] = grille[x - 1][y];

        // Bas
        if (y + 1 < grille[0].length)
            neighbours[2] = grille[x][y + 1];

        // Haut
        if (y - 1 >= 0)
            neighbours[3] = grille[x][y - 1];

        return neighbours;
    }

    /*
     * Calcule la distance de Manhattan entre deux noeuds.
     * d = |x1 - x2| + |y1 - y2|
     * On va utiliser la distance de Manhattan pour la methode de resolution A*
     */
    public static int manhattanDistance(Node a, Node b) {
        int[] aCoord = a.get_coordinates();
        int[] bCoord = b.get_coordinates();
        return Math.abs(aCoord[0] - bCoord[0]) + Math.abs(aCoord[1] - bCoord[1]);
    }

    /*
     * no function to set coordinates because once the coordinates
     * are set we dont want them changing
     */
}
