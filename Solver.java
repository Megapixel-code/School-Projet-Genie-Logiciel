import java.util.LinkedList;

public abstract class Solver {
    protected Labyrinthe labyrinthe;
    protected boolean[][] visite;

    public Solver(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
        this.visite = new boolean[labyrinthe.getLabyrinthe().length][labyrinthe.getLabyrinthe()[0].length];
    }

    // Méthode abstraite à implémenter dans chaque classe de solveur
    public LinkedList<Cellule> résoudreBFS() {
        throw new UnsupportedOperationException("Méthode BFS non implémentée");
    }

    public LinkedList<Cellule> résoudreDFS() {
        throw new UnsupportedOperationException("Méthode DFS non implémentée");
    }

    // Méthode commune pour marquer le chemin parcouru
    protected void marquerChemin(Cellule arrivée) {
        for (Cellule current = arrivée; current != null; current = current.parent) {
            labyrinthe.getLabyrinthe()[current.x][current.y] = 2;  // Marquer comme faisant partie du chemin
        }
    }

    // Méthode pour reconstruire le chemin une fois la solution trouvée
    protected LinkedList<Cellule> reconstruireChemin(Cellule arrivée) {
        LinkedList<Cellule> chemin = new LinkedList<>();
        for (Cellule current = arrivée; current != null; current = current.parent) {
            chemin.addFirst(current);
        }
        return chemin;
    }

    // Méthode pour obtenir les voisins d'une cellule (commune à tous les solveurs)
    protected LinkedList<Cellule> voisins(Cellule current) {
        LinkedList<Cellule> voisins = new LinkedList<>();

        if (current.x > 0) voisins.add(new Cellule(current.x - 1, current.y));  // Haut
        if (current.x < labyrinthe.getLabyrinthe().length - 1) voisins.add(new Cellule(current.x + 1, current.y));  // Bas
        if (current.y > 0) voisins.add(new Cellule(current.x, current.y - 1));  // Gauche
        if (current.y < labyrinthe.getLabyrinthe()[0].length - 1) voisins.add(new Cellule(current.x, current.y + 1));  // Droite

        return voisins;
    }
}
