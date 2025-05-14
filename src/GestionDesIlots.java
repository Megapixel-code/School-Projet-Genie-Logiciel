public class GestionDesIlots {

    public static void uniformiserIlots(int[][] labyrinthe) {
        int n = labyrinthe.length;
        int m = labyrinthe[0].length;
        boolean[][] visite = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (labyrinthe[i][j] != -1 && !visite[i][j]) {
                    int valeurDeReference = labyrinthe[i][j];
                    fusionnerIlot(labyrinthe, visite, i, j, valeurDeReference);
                }
            }
        }
    }

    public static void fusionnerIlot(int[][] grille, boolean[][] visite, int x, int y, int nouvelleValeur) {
        int n = grille.length;
        int m = grille[0].length;

        // Vérifie les limites
        if (x < 0 || x >= n || y < 0 || y >= m) return;
        if (grille[x][y] == -1 || visite[x][y]) return;

        visite[x][y] = true;
        grille[x][y] = nouvelleValeur;

        // Appels récursifs
        fusionnerIlot(grille, visite, x + 1, y, nouvelleValeur);
        fusionnerIlot(grille, visite, x - 1, y, nouvelleValeur);
        fusionnerIlot(grille, visite, x, y + 1, nouvelleValeur);
        fusionnerIlot(grille, visite, x, y - 1, nouvelleValeur);
    }
}
