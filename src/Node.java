
class Node {
    /* 
     * depth will show the solving path.
     * pos_x and pos_y are the 2d coordinates of the node
     */
    private int depth;
    private int pos_x;
    private int pos_y;

    public void node(int x, int y){
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

    /* 
     * no function to set coordinates because once the coordinates 
     * are set we dont want them changing
     */
}



