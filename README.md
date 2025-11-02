# 🧭 Projet Chemin Optimisé

**BUT Informatique – 2ᵉ année**  
Projet universitaire réalisé en Java.

---

## 📌 Description

Ce projet calcule un **chemin optimisé entre plusieurs villes Pokémon** en respectant les contraintes vendeur → acheteur.  
Il utilise un **algorithme glouton** pour minimiser la distance totale parcourue et propose une analyse des commandes pour chaque scénario.

---

## ⚙️ Fonctionnalités principales

- Lecture automatique des scénarios depuis `src/Ressources/scenario_X.txt`
- Calcul du **parcours glouton** entre les villes
- Filtrage des commandes pour exclure celles où le vendeur et l’acheteur sont dans la même ville
- Calcul de la **distance totale** du parcours
- Affichage des **commandes** et du **parcours final**
- Structure orientée objet avec séparation des responsabilités (`CheminTest`, `Commande`, `Extraction`, etc.)

---

## 🧩 Technologies utilisées

- **Langage :** Java 17
- **IDE :** IntelliJ IDEA
- **Outils :** Git / GitHub
- **Paradigme :** Programmation orientée objet (POO)

---

## 🗂️ Structure du projet

Projet_chemin_optimisé/
│
├── src/
│   ├── Algo/
│   │   ├── CheminTest.java
│   │   ├── Commande.java
│   │   ├── Extraction.java
│   │   └── Main.java
│   └── Ressources/
│       ├── scenario_0.txt
│       └── scenario_1.txt
│
├── README.md
├── .gitignore
└── SAEJAVA.iml

---

## 📈 Améliorations possibles

- Ajouter des heuristiques avancées (A*, Dijkstra) pour comparer les résultats
- Interface graphique pour visualiser le parcours
- Gestion dynamique de plusieurs scénarios simultanément
- Optimisation des performances pour grands nombres de villes

---

## 👨‍💻 Auteur

**Romain MESSAGER**  
Étudiant en 2ᵉ année de BUT Informatique – IUT de Vélizy  
🔗 [GitHub – Romain-dev2](https://github.com/Romain-dev2)