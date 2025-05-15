import java.util.*;

public class BFSSolver extends Solver {

    public BFSSolver(Labyrinthe labyrinthe) {
        super(labyrinthe);
    }

    @Override
    public LinkedList<Cellule> résoudreBFS() {
        Queue<Cellule> queue = new LinkedList<>();
        Cellule depart = new Cellule(1, 0);
        Cellule arrivee = new Cellule(labyrinthe.getLabyrinthe().length - 2, labyrinthe.getLabyrinthe()[0].length - 1);

        queue.add(depart);
        visite[1][0] = true;

        while (!queue.isEmpty()) {
            Cellule current = queue.poll();

            if (current.x == arrivee.x && current.y == arrivee.y) {
                marquerChemin(current);
                return reconstruireChemin(current);
            }

            for (Cellule voisin : voisins(current)) {
                if (!visite[voisin.x][voisin.y] && labyrinthe.getLabyrinthe()[voisin.x][voisin.y] != -1) {
                    voisin.parent = current;
                    queue.add(voisin);
                    visite[voisin.x][voisin.y] = true;
                }
            }
        }

        return null;
    }
}
