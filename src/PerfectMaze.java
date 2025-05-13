

class PerfectMaze extends Maze {
    private Node last_node;
    // do the rest
    public PerfectMaze(int x, int y, int seed){
        /* 
         * init the perfect maze
         * same as the maze class but with a different seed
         */
        super(x, y, seed);
        this.last_node = null;
    }
    public Node get_last_node(){
        // self explainatory
        return this.last_node;
    }
}
