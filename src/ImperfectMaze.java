import java.util.*;

public class ImperfectMaze extends PerfectMaze {

    public ImperfectMaze(int x, int y, int seed, int start_x, int start_y, int end_x, int end_y) {
        super(x, y, seed, start_x, start_y, end_x, end_y);
        this.generateBFS();

        // Calcule nombre total de cases
        int maxWalls = getSize_x() * getSize_y();

        // Définir une plage avec un minimum et un maximum différent
        int minModif = Math.max(1, maxWalls / 30);
        int maxModif = minModif + Math.max(1, maxWalls / 30); // ici maxModif > minModif

        Random rng = get_rng();

        int toRemove = minModif + rng.nextInt(maxModif - minModif + 1);
        int toAdd = minModif + rng.nextInt(maxModif - minModif + 1);

        System.out.println("\u001B[33mSuppression aléatoire de " + toRemove + " murs.\u001B[0m");
        removeRandomWalls(toRemove);

        System.out.println("\u001B[33mAjout aléatoire de " + toAdd + " murs.\u001B[0m");
        addRandomWalls(toAdd);
    }
}
