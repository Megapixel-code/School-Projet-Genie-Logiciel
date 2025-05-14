import java.util.Random;

public class LabyModifier {

    // Méthode pour modifier aléatoirement un nombre de murs (-1) et de chemins (1) dans un tableau de tableau
    public static void modifierTableau(int[][] tableau, int nbMursModifies, int nbCheminsModifies) {
        Random rand = new Random();
        int rows = tableau.length;
        int cols = tableau[0].length;

        // Effectuer les modifications aléatoires sans toucher aux bords
        int mursModifies = 0;
        int cheminsModifies = 0;

        while (mursModifies < nbMursModifies || cheminsModifies < nbCheminsModifies) {
            // Générer des indices aléatoires (en excluant les bords)
            int i = rand.nextInt(rows - 2) + 1;  // Exclut la première et la dernière ligne
            int j = rand.nextInt(cols - 2) + 1;  // Exclut la première et la dernière colonne

            // Si on doit modifier un mur en chemin
            if (mursModifies < nbMursModifies && tableau[i][j] == -1) {
                tableau[i][j] = 1; // Transformer un mur en chemin
                mursModifies++;
            }
            // Si on doit modifier un chemin en mur
            else if (cheminsModifies < nbCheminsModifies && tableau[i][j] == 1) {
                tableau[i][j] = -1; // Transformer un chemin en mur
                cheminsModifies++;
            }
        }
    }

    public static void ajouterMur(int[][] tableau, int x, int y) {
        if (x >= 0 && x < tableau.length && y >= 0 && y < tableau[0].length) {
            // Si l'élément est 1, le remplacer par -1
            if (tableau[x][y] == 1) {
                tableau[x][y] = -1;
            }
        }
    }

    public static void ajouterChemin(int[][] tableau, int x, int y) {
        if (x >= 0 && x < tableau.length && y >= 0 && y < tableau[0].length) {
            // Si l'élément est -1, le remplacer par 1
            if (tableau[x][y] == -1) {
                tableau[x][y] = 1;
            }
        }
    }
}
