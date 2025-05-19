import java.util.*;

public class ImperfectMaze extends PerfectMaze {

    public ImperfectMaze(int x, int y, int seed, int[] start, int[] end) {
        super(x, y, seed, start, end);
        this.generateBFS();

        // Calcule nombre total de cases
        int maxWalls = get_size()[0] * get_size()[1];

        // Définir une plage
        int minModif = Math.max(1, maxWalls / 30);

        Random rng = get_rng();

        int toRemove = minModif + rng.nextInt(minModif + 1);
        int toAdd = minModif + rng.nextInt(minModif + 1);

        System.out.println("\u001B[33mSuppression aléatoire de " + toRemove + " murs.\u001B[0m");
        this.removeRandomWalls(toRemove);

        System.out.println("\u001B[33mAjout aléatoire de " + toAdd + " murs.\u001B[0m");
        this.addRandomWalls(toAdd);
    }
}
