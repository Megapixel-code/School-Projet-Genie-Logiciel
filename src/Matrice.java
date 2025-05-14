import java.util.HashSet;
import java.util.Set;

public class Matrice {
    public static boolean aMaximumDeuxValeursDifferentes(int[][] tableau) {
        Set<Integer> valeurs = new HashSet<>();

        for (int[] ligne : tableau) {
            for (int valeur : ligne) {
                if (valeur != -1) {
                    valeurs.add(valeur);
                    if (valeurs.size() > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
