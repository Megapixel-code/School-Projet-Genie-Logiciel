package Labyrinthe;

public class ImperfectMaze extends Maze  {

    public ImperfectMaze(int size_x, int size_y, int seed, String generation_mode) {
        super(size_x, size_y, seed, generation_mode);
    }

    @Override
    public void generateMaze() {
        System.out.println("Generating imperfect maze...");
        // algo ici
    }
}
