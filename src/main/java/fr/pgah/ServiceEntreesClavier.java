package fr.pgah;

import java.util.Scanner;

public class ServiceEntreesClavier {

  private String messagePrompt = "? ";
  private String messageErreur = "Entrée incorrecte.";
  private int nbEntrees;
  private String separation;
  private int limiteInferieure;
  private int limiteSuperieure;
  private Scanner clavier;

  public ServiceEntreesClavier(int nbEntrees, String sepRegex, int limiteInf, int limiteSup) {
    clavier = new Scanner(System.in);
    this.nbEntrees = nbEntrees;
    this.separation = sepRegex;
    this.limiteInferieure = limiteInf;
    this.limiteSuperieure = limiteSup;
  }

  public void setMessagePrompt(String message) {
    this.messagePrompt = message;
  }

  public void setMessageErreur(String message) {
    this.messageErreur = message;
  }

  public int[] demanderEntrees() {
    int[] entreesSeparees = null;
    boolean entreesInvalides = true;
    while (entreesInvalides) {
      afficherMessageEntree();
      String entrees = clavier.nextLine().trim();
      try {
        entreesSeparees = parserEntrees(entrees);
        entreesInvalides = false;
      } catch (NumberFormatException e) {
        afficherMessageErreur();
        entreesInvalides = true;
      }
    }
    return entreesSeparees;
  }


  private int[] parserEntrees(String entrees) {
    String[] entreesSplitees = entrees.split(separation);
    validerNombreDEntrees(entreesSplitees);
    int[] nombres = new int[nbEntrees];
    for (int i = 0; i < nbEntrees; i++) {
      nombres[i] = parserEntree(entreesSplitees[i]);
    }
    return nombres;
  }

  private void validerNombreDEntrees(String[] entreesSplitees) {
    if (entreesSplitees.length != nbEntrees) {
      throw new NumberFormatException();
    }
  }

  private int parserEntree(String entree) {
    int nombre = Integer.parseInt(entree);
    if (esthorsLimites(nombre)) {
      throw new NumberFormatException();
    }
    return nombre;
  }

  private boolean esthorsLimites(int nb) {
    return nb < limiteInferieure || nb > limiteSuperieure;
  }

  private void afficherMessageEntree() {
    System.out.print(messagePrompt + " : ");
  }

  private void afficherMessageErreur() {
    System.out.println(messageErreur);
  }
}


