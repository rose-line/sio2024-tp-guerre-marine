package fr.pgah;

public class Jeu {
  private final int NB_LIGNES_SEPARATION = 20;
  public static int TailleGrille = 5;
  private Joueur joueur1;
  private Joueur joueur2;
  private Joueur joueurCourant;
  private ServiceEntreesClavier serviceEntrees;

  public Jeu(int tailleGrille) {
    TailleGrille = tailleGrille;
    serviceEntrees = new ServiceEntreesClavier(2, " ", 0, tailleGrille - 1);
    joueur1 = new Joueur("J1", serviceEntrees);
    joueur2 = new Joueur("J2", serviceEntrees);
  }

  public void lancer() {
    System.out.println("DÉFINITION DES GRILLES DES JOUEURS\n");
    definirGrillesJoueurs();
    System.out.println("DÉBUT DE LA PARTIE !\n");
    effectuerLesToursDeJeu();
    afficherResultat();
  }

  private void effectuerLesToursDeJeu() {
    joueurCourant = joueur2;
    do {
      System.out.println("Voici l'état actuel de l'attaque :\n");
      joueurCourant.afficherGrilleSansNavires();
      joueurCourant = autreJoueur();
      faireJouerJoueurCourant();
    } while (pasDeGagnant());
  }

  private void definirGrillesJoueurs() {
    joueur1.definirGrille();
    passerLignes();
    joueur2.definirGrille();
    passerLignes();
  }

  private void passerLignes() {
    for (int i = 0; i < NB_LIGNES_SEPARATION; i++) {
      System.out.println();
    }
  }

  private void afficherResultat() {
    System.out.print(joueurCourant.getNom() + ", VOUS AVEZ GAGNÉ ! ");
    System.out.println("NAVIRES RESTANTS : " + joueurCourant.getNbNaviresRestants());
    System.out.println();
    System.out.println("Grille du joueur 1 :\n");
    joueur1.afficherGrilleComplete();
    System.out.println("Grille du joueur 2 :\n");
    joueur2.afficherGrilleComplete();
    System.out.println("MERCI D'AVOIR JOUÉ !\n");
  }

  private void faireJouerJoueurCourant() {
    int[] coordsTir = demanderCoordonneesTir(joueurCourant, autreJoueur());
    boolean touche = autreJoueur().recevoirTir(coordsTir);
    if (touche) {
      System.out.println(joueurCourant.getNom() + ", VOUS AVEZ COULÉ UN NAVIRE ADVERSE !");
    } else {
      System.out.println(joueurCourant.getNom() + ", VOUS AVEZ FAIT UN COUP DANS L'EAU !");
    }
    System.out.println("_____________________________________________________\n");
  }

  private Joueur autreJoueur() {
    return joueurCourant == joueur1 ? joueur2 : joueur1;
  }

  private boolean pasDeGagnant() {
    return !joueur1.aPerdu() && !joueur2.aPerdu();
  }

  private int[] demanderCoordonneesTir(Joueur joueur, Joueur joueurAdverse) {
    int[] coordonnees;
    boolean coordonneesInterdites;
    do {
      coordonneesInterdites = false;
      serviceEntrees.setMessagePrompt(joueur.getNom() + ", entrez les coordonnées de votre tir");
      serviceEntrees.setMessageErreur("Coordonnées invalides. Veuillez respecter le format.");
      coordonnees = serviceEntrees.demanderEntrees();
      if (joueurAdverse.aDejaRecuTirSur(coordonnees)) {
        System.out
            .println("Vous avez déjà tiré à cet endroit. Choisissez des coordonnées différentes.");
        coordonneesInterdites = true;
      }
    } while (coordonneesInterdites);
    return coordonnees;
  }
}
