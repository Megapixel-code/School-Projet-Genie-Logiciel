import java.util.*;

public class ImperfectMazeKruskal extends PerfectMaze {

    public ImperfectMazeKruskal(int x, int y, int seed, int start_x, int start_y, int end_x, int end_y) {
        super(x, y, seed, start_x, start_y, end_x, end_y);

        // Génération parfaite avec Kruskal
        this.generateKruskal();

        int maxWalls = getSize_x() * getSize_y();

        int minModif = Math.max(1, maxWalls / 30);
        int maxModif = minModif + Math.max(1, maxWalls / 30);

        Random rng = get_rng();

        int toRemove = minModif + rng.nextInt(maxModif - minModif + 1); // murs à casser = ajouter des cycles
        int toAdd = minModif + rng.nextInt(maxModif - minModif + 1);    // murs à rajouter

        System.out.println("\u001B[33mSuppression aléatoire de " + toRemove + " murs.\u001B[0m");
        removeRandomWalls(toRemove);

        System.out.println("\u001B[33mAjout aléatoire de " + toAdd + " murs.\u001B[0m");
        addRandomWalls(toAdd);
    }
}
