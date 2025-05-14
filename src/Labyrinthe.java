import java.util.LinkedList;
import java.util.Random;

public class Labyrinthe {
    private int n;
    private int m;
    private int[][] labyrinthe;
    private Random rand;

    public Labyrinthe(int n, int m, int seed) {
        this.n = n;
        this.m = m;
        this.labyrinthe = new int[n][m];
        this.rand = new Random(seed);
    }

    public void generer() {
        // Étape 1 : Initialisation avec murs et chemins
        initialiserLabyrinthe();

        // Étape 2 : Entrée et sortie
        definirEntreeSortie();

        // Étape 3 : Numérotation des chemins
        numeroterChemins();

        // Étape 4 : Générer le labyrinthe parfait
        genererLabyrintheParfait();

        // Uniformisation des îlots
        GestionDesIlots.uniformiserIlots(labyrinthe);
    }

    private void initialiserLabyrinthe() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i % 2 == 0 || j % 2 == 0) {
                    labyrinthe[i][j] = -1; // Mur
                } else {
                    labyrinthe[i][j] = 0; // Chemin
                }
            }
        }
    }

    private void definirEntreeSortie() {
        labyrinthe[1][0] = 0; // Entrée
        labyrinthe[n - 2][m - 1] = 0; // Sortie
    }

    private void numeroterChemins() {
        int compteur = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (labyrinthe[i][j] == 0) {
                    labyrinthe[i][j] = compteur++;
                }
            }
        }
    }

    private void genererLabyrintheParfait() {
        while (!Matrice.aMaximumDeuxValeursDifferentes(labyrinthe)) {
            int x = rand.nextInt(n - 2) + 1;
            int y = (x % 2 == 0) ? (rand.nextInt((m - 1) / 2)) * 2 + 1 : (rand.nextInt((m - 2) / 2)) * 2 + 2;

            int cell_1, cell_2;

            if (labyrinthe[x - 1][y] == -1) {
                cell_1 = labyrinthe[x][y - 1];
                cell_2 = labyrinthe[x][y + 1];
            } else {
                cell_1 = labyrinthe[x - 1][y];
                cell_2 = labyrinthe[x + 1][y];
            }

            if (cell_1 != cell_2) {
                labyrinthe[x][y] = cell_1;
                for (int i = 1; i < n - 1; i += 2) {
                    for (int j = 1; j < m - 1; j += 2) {
                        if (labyrinthe[i][j] == cell_2) {
                            labyrinthe[i][j] = cell_1;
                        }
                    }
                }
            }
        GestionDesIlots.uniformiserIlots(labyrinthe);
        }
    }

    public void afficher() {
        for (int i = 0; i < labyrinthe.length; i++) {
            for (int j = 0; j < labyrinthe[i].length; j++) {
                if (i == 1 && j == 0) {
                    System.out.print(" D ");  // Départ
                } else if (i == labyrinthe.length - 2 && j == labyrinthe[0].length - 1) {
                    System.out.print(" A ");  // Arrivée
                } else if (labyrinthe[i][j] == -1) {
                    System.out.print("///");  // Mur
                } else if (labyrinthe[i][j] == 2) {
                    System.out.print(" * ");  // Chemin parcouru
                } else {
                    System.out.print(" . ");  // Chemin numéroté
                }
            }
            System.out.println();  // Nouvelle ligne après chaque rangée
        }
    }
    public void reinitialiserLabyrinthe() {
        for (int i = 0; i < labyrinthe.length; i++) {
            for (int j = 0; j < labyrinthe[i].length; j++) {
                if (labyrinthe[i][j] == 2) {
                    labyrinthe[i][j] = 1;  // Réinitialiser les cellules du chemin à 1
                }
            }
        }
    }


    public LinkedList<Cellule> résoudre() {
        Solver solver = new Solver(this);
        return solver.résoudre();
    }

    public int[][] getLabyrinthe() {
        return labyrinthe;
    }
    // Ajoute une méthode pour rendre le labyrinthe imparfait
    public void rendreImparfait(int nbMursModifies, int nbCheminsModifies) {
        LabyModifier.modifierTableau(labyrinthe, nbMursModifies, nbCheminsModifies);
    }

}
