import java.util.*;

public class ImperfectMazeKruskal extends PerfectMaze {

    public ImperfectMazeKruskal(int x, int y, int seed, int[] start, int[] end) {
        super(x, y, seed, start, end);

        // Génération parfaite avec Kruskal
        this.generateKruskal();

        int maxWalls = get_size()[0] * get_size()[1];

        Random rng = get_rng();

        int Modif = Math.max(1, maxWalls / 30);

        int toRemove = Modif + rng.nextInt(Modif + 1); // murs à casser = ajouter des cycles
        int toAdd = Modif + rng.nextInt(Modif + 1);    // murs à rajouter

        System.out.println("\u001B[33mSuppression aléatoire de " + toRemove + " murs.\u001B[0m");
        removeRandomWalls(toRemove);

        System.out.println("\u001B[33mAjout aléatoire de " + toAdd + " murs.\u001B[0m");
        addRandomWalls(toAdd);
    }
}