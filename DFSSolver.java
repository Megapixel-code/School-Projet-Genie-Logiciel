// DFSSolver.java
import java.util.*;

public class DFSSolver extends Solver {

    public DFSSolver(Labyrinthe labyrinthe) {
        super(labyrinthe);
    }

    @Override
    public LinkedList<Cellule> résoudreDFS() {
        Stack<Cellule> stack = new Stack<>();
        Cellule depart = new Cellule(1, 0);
        Cellule arrivee = new Cellule(labyrinthe.getLabyrinthe().length - 2, labyrinthe.getLabyrinthe()[0].length - 1);

        stack.push(depart);
        visite[1][0] = true;

        while (!stack.isEmpty()) {
            Cellule current = stack.pop();

            if (current.x == arrivee.x && current.y == arrivee.y) {
                marquerChemin(current);
                return reconstruireChemin(current);
            }

            for (Cellule voisin : voisins(current)) {
                if (!visite[voisin.x][voisin.y] && labyrinthe.getLabyrinthe()[voisin.x][voisin.y] != -1) {
                    voisin.parent = current;
                    stack.push(voisin);
                    visite[voisin.x][voisin.y] = true;
                }
            }
        }
        return null;
    }
}
