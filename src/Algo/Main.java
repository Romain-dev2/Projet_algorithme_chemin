package Algo;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Extraction extraction = new Extraction();
        System.out.println(extraction.getMembresVilles());
        System.out.println("Nombre total de scénarios détectés : " + extraction.getScenarios().size());

        for (int i = 0; i < extraction.getScenarios().size(); i++) {
            System.out.println("=== SCÉNARIO " + i + " ===");
            try {
                CheminTest chemin = new CheminTest(i);  // constructeur avec numéro scénario
                var commandes = chemin.getCommandes();

                if (commandes.isEmpty()) {
                    System.out.println("Aucune commande trouvée dans ce scénario. Fichier vide ou mal formaté ?\n");
                    continue;
                }

                System.out.println("Commandes :");
                for (Commande c : commandes) {
                    System.out.println(" - " + c);
                }

                var parcours = chemin.parcoursGlouton();
                System.out.print("Parcours : ");
                for (String ville : parcours) {
                    System.out.print(ville + " -> ");
                }
                System.out.println("FIN");

                int distance = chemin.calculDistance(parcours);
                System.out.println("Distance totale : " + distance + " km\n");

            } catch (Exception e) {
                System.out.println("⚠️ Erreur dans le scénario " + i + " : " + e.getMessage());
                e.printStackTrace();
                System.out.println();
            }
            System.out.println("--------\n");
        }
    }
}
