import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        int n = 7; // Hauteur du labyrinthe
        int m = 7; // Largeur du labyrinthe
        int seed = 612; // Graine pour la reproductibilité

        // Créer le labyrinthe parfait
        Labyrinthe labyrinthe = new Labyrinthe(n, m, seed);
        labyrinthe.generer();

        // Afficher le labyrinthe parfait
        System.out.println("=== Labyrinthe Parfait ===");
        labyrinthe.afficher();

        // Résolution BFS du labyrinthe parfait
        System.out.println("\n--- Résolution BFS (Parfait) ---");
        LinkedList<Cellule> cheminBFSParfait = labyrinthe.resoudre("BFS");
        if (cheminBFSParfait != null) {
            labyrinthe.afficher();
        } else {
            System.out.println("Aucun chemin trouvé avec BFS.");
        }

        // Réinitialiser avant DFS
        labyrinthe.reinitialiserLabyrinthe();

        // Résolution DFS du labyrinthe parfait
        System.out.println("\n--- Résolution DFS (Parfait) ---");
        LinkedList<Cellule> cheminDFSParfait = labyrinthe.resoudre("DFS");
        if (cheminDFSParfait != null) {
            labyrinthe.afficher();
        } else {
            System.out.println("Aucun chemin trouvé avec DFS.");
        }

        // Réinitialiser avant transformation
        labyrinthe.reinitialiserLabyrinthe();

        // Rendre le labyrinthe imparfait
        int nbMursModifies = 50;
        int nbCheminsModifies = 30;
        labyrinthe.rendreImparfait(nbMursModifies, nbCheminsModifies);

        // Afficher le labyrinthe imparfait
        System.out.println("\n=== Labyrinthe Imparfait ===");
        labyrinthe.afficher();

        // Résolution BFS du labyrinthe imparfait
        System.out.println("\n--- Résolution BFS (Imparfait) ---");
        LinkedList<Cellule> cheminBFSImparfait = labyrinthe.resoudre("BFS");
        if (cheminBFSImparfait != null) {
            labyrinthe.afficher();
        } else {
            System.out.println("Aucun chemin trouvé avec BFS.");
        }

        // Réinitialiser avant DFS
        labyrinthe.reinitialiserLabyrinthe();

        // Résolution DFS du labyrinthe imparfait
        System.out.println("\n--- Résolution DFS (Imparfait) ---");
        LinkedList<Cellule> cheminDFSImparfait = labyrinthe.resoudre("DFS");
        if (cheminDFSImparfait != null) {
            labyrinthe.afficher();
        } else {
            System.out.println("Aucun chemin trouvé avec DFS.");
        }
    }
}

