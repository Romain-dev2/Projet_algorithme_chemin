package Algo;

import java.io.*;
import java.util.*;
import Algo.Commande.*;

import java.util.ArrayList;
import java.util.List;

public class CheminTest {
    private final Extraction extraction;
    private final List<String> sommets;
    private final List<Commande> commandes;
    private int numeroScenario;
    private String scenarioFichier;

    public CheminTest(int scenarioChoisi) throws FileNotFoundException {
        numeroScenario = numeroScenario;
        scenarioFichier = "src" + File.separator + "Ressources" + File.separator + "scenario_" + numeroScenario + ".txt";
        extraction = new Extraction();
        sommets = new ArrayList<>();
        sommets.add("Velizy");
        sommets.addAll(extraction.getVilles(scenarioChoisi).keySet());

        commandes = new ArrayList<>();
        for (Map.Entry<String, String> entry : extraction.getScenarios().get(scenarioChoisi).entrySet()) {
            String vendeur = extraction.getMembresVilles().get(entry.getKey());
            String acheteur = extraction.getMembresVilles().get(entry.getValue());
            commandes.add(new Commande(vendeur, acheteur));
        }
    }

    public List<String> parcoursGlouton() {
        Set<String> villesAVisiter = new HashSet<>();
        Map<String, List<String>> livraisons = new HashMap<>();

        for (Commande c : commandes) {
            villesAVisiter.add(c.vendeur);
            villesAVisiter.add(c.acheteur);
            livraisons.computeIfAbsent(c.vendeur, k -> new ArrayList<>()).add(c.acheteur);
        }

        List<String> parcours = new ArrayList<>();
        Set<String> cartesRamassees = new HashSet<>();
        Set<Commande> commandesLivrees = new HashSet<>();

        String villeActuelle = "Velizy";
        parcours.add(villeActuelle);

        while (commandesLivrees.size() < commandes.size()) {
            String prochaineVille = null;
            int minDistance = Integer.MAX_VALUE;

            for (Commande c : commandes) {
                if (!cartesRamassees.contains(c.vendeur)) {
                    int d = extraction.distanceVilleToVille(villeActuelle, c.vendeur);
                    if (d < minDistance) {
                        minDistance = d;
                        prochaineVille = c.vendeur;
                    }
                } else if (cartesRamassees.contains(c.vendeur) && !commandesLivrees.contains(c)) {
                    int d = extraction.distanceVilleToVille(villeActuelle, c.acheteur);
                    if (d < minDistance) {
                        minDistance = d;
                        prochaineVille = c.acheteur;
                    }
                }
            }

            if (prochaineVille == null) break;
            villeActuelle = prochaineVille;
            parcours.add(villeActuelle);

            for (Commande c : commandes) {
                if (villeActuelle.equals(c.vendeur)) {
                    cartesRamassees.add(c.vendeur);
                } else if (villeActuelle.equals(c.acheteur) && cartesRamassees.contains(c.vendeur)) {
                    commandesLivrees.add(c);
                }
            }
        }

        if (!villeActuelle.equals("Velizy")) parcours.add("Velizy");
        return parcours;
    }

    public int calculDistance(List<String> parcours) {
        int distance = 0;
        for (int i = 0; i < parcours.size() - 1; i++) {
            String from = parcours.get(i);
            String to = parcours.get(i + 1);
            distance += extraction.distanceVilleToVille(from, to);
        }
        return distance;
    }

    public List<Commande> getCommandes() throws IOException {
        List<Commande> commandesBrutes = extraction.lireCommandesScenario(scenarioFichier);
        List<Commande> commandesFiltres = new ArrayList<>();

        for (Commande c : commandesBrutes) {
            String villeVendeur = extraction.getCorrespondances().get(c.getVendeur());
            String villeAcheteur = extraction.getCorrespondances().get(c.getAcheteur());

            if (villeVendeur == null || villeAcheteur == null) continue;

            // Exclure commandes où vendeur et acheteur sont dans la même ville
            if (!villeVendeur.equals(villeAcheteur)) {
                commandesFiltres.add(c);
            }
        }
        return commandesFiltres;
    }

    public List<String> getSommets() {
        return sommets;
    }
}