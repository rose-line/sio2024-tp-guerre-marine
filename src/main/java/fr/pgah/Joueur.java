package fr.pgah;

public class Joueur {
  private String nom;
  private Grille grille;
  private ServiceEntreesClavier serviceEntrees;

  public Joueur(String nom, ServiceEntreesClavier serviceEntrees) {
    this.nom = nom;
    grille = new Grille(Jeu.TailleGrille);
    this.serviceEntrees = serviceEntrees;
  }

  public String getNom() {
    return nom;
  }

  public void definirGrille() {
    int limite = Jeu.TailleGrille - 1;
    System.out.println(nom + ", entrez les coordonnées de vos navires, sous la forme \"L C\"");
    System.out.println("L représente la ligne (entre 0 et " + limite
        + ") et C la colonne (entre 0 et " + limite + ").");
    for (int nav = 1; nav <= Jeu.TailleGrille; nav++) {
      int[] coords = demanderCoordonneesPlacement(nav, grille);
      grille.placerNavireSur(coords);
      grille.afficherComplete();
    }
  }

  private int[] demanderCoordonneesPlacement(int nav, Grille grille) {
    int[] coords;
    boolean entreeDoublon;
    do {
      entreeDoublon = false;
      serviceEntrees.setMessagePrompt("Coordonnées du navire " + nav);
      serviceEntrees.setMessageErreur("Coordonnées invalides. Veuillez respecter le format.");
      coords = serviceEntrees.demanderEntrees();
      if (grille.navireEstSur(coords)) {
        System.out.println(
            "Vous avez déjà un navire à cet endroit. Choisissez des coordonnées différentes.");
        entreeDoublon = true;
      }
    } while (entreeDoublon);
    return coords;
  }

  public void afficherGrilleComplete() {
    grille.afficherComplete();
  }

  public void afficherGrilleSansNavires() {
    grille.afficherSansNavire();
  }

  public boolean aPerdu() {
    return grille.toutEstDetruit();
  }

  public boolean aDejaRecuTirSur(int[] coords) {
    return grille.aRecuTirSur(coords);
  }

  public boolean recevoirTir(int[] coords) {
    return grille.recevoirTir(coords);
  }

  public int getNbNaviresRestants() {
    return grille.getNbNaviresRestants();
  }

  int[] demanderCoordonneesTir(Joueur joueurAdverse) {
    int[] coordonnees;
    boolean coordonneesInterdites;
    do {
      coordonneesInterdites = false;
      serviceEntrees.setMessagePrompt(getNom() + ", entrez les coordonnées de votre tir");
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
