import java.util.LinkedList;
import java.util.Queue;

public class Solver {
    private Labyrinthe labyrinthe;
    private boolean[][] visite;

    public Solver(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
        this.visite = new boolean[labyrinthe.getLabyrinthe().length][labyrinthe.getLabyrinthe()[0].length];
    }

    public LinkedList<Cellule> résoudre() {
        Queue<Cellule> queue = new LinkedList<>();
        Cellule depart = new Cellule(1, 0);  // Départ à (1, 0)
        Cellule arrivée = new Cellule(labyrinthe.getLabyrinthe().length - 2, labyrinthe.getLabyrinthe()[0].length - 1);  // Arrivée

        // Ajouter la cellule de départ à la queue
        queue.add(depart);
        visite[1][0] = true;  // Marquer le départ comme visité

        while (!queue.isEmpty()) {
            Cellule current = queue.poll();

            // Vérifier si on a atteint l'arrivée
            if (current.x == arrivée.x && current.y == arrivée.y) {
                // Marquer le chemin parcouru dans le labyrinthe avec des 2
                marquerChemin(current);
                return reconstruireChemin(current);
            }

            // Vérifier les voisins de la cellule actuelle (haut, bas, gauche, droite)
            for (Cellule voisin : voisins(current)) {
                if (!visite[voisin.x][voisin.y] && labyrinthe.getLabyrinthe()[voisin.x][voisin.y] != -1) {
                    voisin.parent = current;  // Lier le voisin à la cellule actuelle
                    queue.add(voisin);
                    visite[voisin.x][voisin.y] = true;  // Marquer comme visité
                }
            }
        }

        return null;  // Si aucun chemin n'a été trouvé
    }

    private void marquerChemin(Cellule arrivée) {
        // Parcourir le chemin du départ jusqu'à l'arrivée en marquant les cellules comme '2'
        for (Cellule current = arrivée; current != null; current = current.parent) {
            labyrinthe.getLabyrinthe()[current.x][current.y] = 2;  // Marquer la cellule comme faisant partie du chemin
        }
    }

    private LinkedList<Cellule> voisins(Cellule current) {
        LinkedList<Cellule> voisins = new LinkedList<>();

        // Haut
        if (current.x > 0) {
            voisins.add(new Cellule(current.x - 1, current.y));
        }
        // Bas
        if (current.x < labyrinthe.getLabyrinthe().length - 1) {
            voisins.add(new Cellule(current.x + 1, current.y));
        }
        // Gauche
        if (current.y > 0) {
            voisins.add(new Cellule(current.x, current.y - 1));
        }
        // Droite
        if (current.y < labyrinthe.getLabyrinthe()[0].length - 1) {
            voisins.add(new Cellule(current.x, current.y + 1));
        }

        return voisins;
    }

    // Méthode pour reconstruire le chemin une fois la solution trouvée
    private LinkedList<Cellule> reconstruireChemin(Cellule arrivée) {
        LinkedList<Cellule> chemin = new LinkedList<>();
        for (Cellule current = arrivée; current != null; current = current.parent) {
            chemin.addFirst(current);
        }
        return chemin;
    }
}
