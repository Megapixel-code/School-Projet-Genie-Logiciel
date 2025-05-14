import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        int n = 37; // Hauteur du labyrinthe
        int m = 37; // Largeur du labyrinthe
        int seed = 6112; // Graine pour la reproductibilité des résultats

        // Créer le labyrinthe parfait
        Labyrinthe labyrinthe = new Labyrinthe(n, m, seed);
        labyrinthe.generer();

        // Afficher le labyrinthe parfait
        System.out.println("Labyrinthe Parfait");
        labyrinthe.afficher();  // Affichage du labyrinthe parfait

        // Résoudre le labyrinthe parfait et afficher le chemin parcouru
        System.out.println("Résolution du Labyrinthe Parfait");
        LinkedList<Cellule> cheminParfait = labyrinthe.résoudre(); // Résolution du labyrinthe parfait
        if (cheminParfait != null) {
            labyrinthe.afficher();  // Affichage avec le chemin parcouru
        } else {
            System.out.println("Aucun chemin trouvé pour le labyrinthe parfait.");
        }

        // Réinitialiser le labyrinthe avant d'afficher l'imparfait
        labyrinthe.reinitialiserLabyrinthe();

        // Rendre le labyrinthe imparfait en modifiant des murs et des chemins
        int nbMursModifies = 50;  // Exemple de nombre de murs à modifier
        int nbCheminsModifies = 30;  // Exemple de nombre de chemins à modifier
        labyrinthe.rendreImparfait(nbMursModifies, nbCheminsModifies);

        // Afficher le labyrinthe imparfait sans la solution
        System.out.println("Labyrinthe Imparfait");
        labyrinthe.afficher();  // Affichage du labyrinthe imparfait sans chemin

        // Résoudre le labyrinthe imparfait et afficher le chemin parcouru
        System.out.println("Résolution du Labyrinthe Imparfait");
        LinkedList<Cellule> cheminImparfait = labyrinthe.résoudre(); // Résolution du labyrinthe imparfait
        if (cheminImparfait != null) {
            labyrinthe.afficher();  // Affichage avec le chemin parcouru
        } else {
            System.out.println("Aucun chemin trouvé pour le labyrinthe imparfait.");
        }
    }
}

