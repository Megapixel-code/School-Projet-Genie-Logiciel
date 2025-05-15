public class Cellule {
    int x, y;
    Cellule parent;  // Lien vers la cellule précédente dans le chemin

    public Cellule(int x, int y) {
        this.x = x;
        this.y = y;
        this.parent = null;  // Initialement pas de parent
    }
}
