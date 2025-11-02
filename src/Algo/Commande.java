package Algo;

public class Commande {
    public final String vendeur;
    public final String acheteur;

    public Commande(String vendeur, String acheteur) {
        this.vendeur = vendeur;
        this.acheteur = acheteur;
    }

    public String toString() {
        return vendeur + " -> " + acheteur;
    }

    public String getVendeur() {
        return vendeur;
    }

    public String getAcheteur() {
        return acheteur;
    }
}