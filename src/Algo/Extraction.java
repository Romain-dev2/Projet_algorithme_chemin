package Algo;

import java.io.*;
import java.util.*;

public class Extraction {
    private final HashMap<Integer, HashMap<String, String>> scenarios;
    private final TreeMap<String, ArrayList<Integer>> distanceVilles;
    private final HashMap<String, String> membresVilles;
    private Map<String, String> correspondances;

    public Extraction() throws FileNotFoundException {
        File ressources = new File("src" + File.separator + "Ressources");
        this.membresVilles = new HashMap<>();
        this.scenarios = new HashMap<>();
        this.distanceVilles = new TreeMap<>();
        correspondances = new HashMap<>();

        int Nscenario = 0;

        for (File f : Objects.requireNonNull(ressources.listFiles())) {
            if (f.getName().equals("distances.txt")) {
                try (Scanner lecteur = new Scanner(f)) {
                    while (lecteur.hasNextLine()) {
                        String data = lecteur.nextLine().trim();
                        if (data.isEmpty()) continue;

                        String[] parts = data.split(" ");
                        if (parts.length == 0) continue;

                        ArrayList<Integer> distances = new ArrayList<>();
                        // La première partie est la clé (ville), les suivantes les distances
                        for (int i = 1; i < parts.length; i++) {
                            try {
                                int distance = Integer.parseInt(parts[i]);
                                distances.add(distance);
                            } catch (NumberFormatException e) {
                                // Ignorer les non-entiers
                            }
                        }
                        distanceVilles.put(parts[0], distances);
                    }
                }
            }

            if (f.getName().startsWith("scenario")) {
                try (Scanner lecteur = new Scanner(f)) {
                    HashMap<String, String> echanges = new HashMap<>();
                    while (lecteur.hasNextLine()) {
                        String data = lecteur.nextLine().trim();
                        if (!data.contains("->")) continue;
                        String[] parts = data.split("->");
                        if (parts.length != 2) continue;
                        echanges.put(parts[0].trim(), parts[1].trim());
                    }
                    scenarios.put(Nscenario, echanges);
                    Nscenario++;
                }
            }

            if (f.getName().startsWith("membres")) {
                try (Scanner lecteur = new Scanner(f)) {
                    while (lecteur.hasNextLine()) {
                        String data = lecteur.nextLine().trim();
                        String[] parts = data.split(" ");
                        if (parts.length < 2) continue;
                        membresVilles.put(parts[0], parts[1]);
                    }
                }
            }
        }

        // correspondances = membresVilles pour simplifier
        correspondances = membresVilles;
    }

    public HashMap<Integer, HashMap<String, String>> getScenarios() {
        return scenarios;
    }

    public HashMap<String, String> getMembresVilles() {
        return membresVilles;
    }

    /**
     * Lit les commandes dans un fichier scenario donné (chemin complet en String).
     */
    public List<Commande> lireCommandesScenario(String fichierScenario) throws IOException {
        List<Commande> commandes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fichierScenario))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || !ligne.contains("->")) continue;

                String[] parties = ligne.split("->");
                if (parties.length != 2) continue;

                String vendeur = parties[0].trim();
                String acheteur = parties[1].trim();

                String villeVendeur = correspondances.get(vendeur);
                String villeAcheteur = correspondances.get(acheteur);

                if (villeVendeur == null || villeAcheteur == null) continue;
                if (villeVendeur.equals(villeAcheteur)) continue;

                commandes.add(new Commande(vendeur, acheteur));
            }
        }
        return commandes;
    }

    /**
     * Retourne la map des villes liées par les commandes dans le scénario.
     * Gestion de l'IOException en interne.
     */
    public Map<String, List<String>> getVilles(int scenario) {
        Map<String, List<String>> villes = new HashMap<>();
        String fichierScenario = "src" + File.separator + "Ressources" + File.separator + "scenario_" + scenario + ".txt";
        try {
            List<Commande> commandes = lireCommandesScenario(fichierScenario);
            for (Commande commande : commandes) {
                String villeVendeur = correspondances.get(commande.getVendeur());
                String villeAcheteur = correspondances.get(commande.getAcheteur());

                if (villeVendeur != null && villeAcheteur != null && !villeVendeur.equals(villeAcheteur)) {
                    villes.putIfAbsent(villeVendeur, new ArrayList<>());
                    villes.get(villeVendeur).add(villeAcheteur);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du scénario " + scenario + " : " + e.getMessage());
        }
        return villes;
    }

    /**
     * Renvoie la distance entre deux villes.
     */
    public int distanceVilleToVille(String villeDepart, String villeArriver) {
        List<String> keys = new ArrayList<>(distanceVilles.keySet());
        int idxArriver = keys.indexOf(villeArriver);
        ArrayList<Integer> distances = distanceVilles.get(villeDepart);
        if (distances == null || idxArriver == -1) {
            return -1; // distance inconnue
        }
        return distances.get(idxArriver);
    }

    public Map<String, String> getCorrespondances() {
        return correspondances;
    }
}