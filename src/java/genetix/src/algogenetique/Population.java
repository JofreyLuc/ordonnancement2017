package algogenetique; 

import java.util.ArrayList;
import java.util.Collections;

public class Population {

	// Une population est une liste d'individu 
	private ArrayList<Individu> individus;


	// Constructeur d'une population vide
	public Population() {
		this.individus = new ArrayList<Individu>();
	}

	// constructeur d'une population intialiée aléatoirement 
	public Population(int taille) {
		this.individus = new ArrayList<Individu>();

		for (int i = 0; i < taille; i++) {
			Individu indiv = new Individu();
			indiv.aleatoire();
			this.individus.add(indiv);
		}
	}

	// retourne la pupulation
	public ArrayList<Individu> retournePopulation(){
		return this.individus ;
	}

	// Retourne la taille
	public int retourneTaille(){
		return this.individus.size() ;
	}

	// Retourne l'individu à l'index 
	public Individu retourneIndividu(int index) {
		return this.individus.get(index);
	}

	// Ajoute un individu à la population
	public void ajouter(Individu indiv){
		this.individus.add(indiv);
	}

	// Evalue tous les membres de la population
	public void evaluer(){
		for (int i=0; i<this.individus.size(); i++)
			this.individus.get(i).evaluer();
	}

	// Trie la population par ordre croissant de fitness
	// (voir compareTo dans la classe Individu)
	public void trier(){
		Collections.sort(this.individus);
	}    

	// Pour l'afichage de la population 
	public String toString() {
		String popString = "{";
		for (int i=0; i<this.individus.size(); i++)
			popString+=this.individus.get(i).toString()+"("+
					this.individus.get(i).retourneFitness()+ ") , ";
		popString+="}";
		return popString;
	}

	// Retourne Fitness moyenne
	public double retourneFitnessMoyenne(){
		double somme = 0.0;
		for (int i=0; i<this.individus.size(); i++)
			somme += this.individus.get(i).retourneFitness();
		return somme / this.individus.size();
	}

	// calcule la diversité de la population (sum des distance 2 a 2 )
	public double retourneDiversite(){

		/*
	    TODO : Ecrire le code de l'algorithme suivant.

	    Algorithme du calcul de la diversite genetique de la population 

	    La diversite est la moyene des distances 2 a 2 des individus ou

	    diversite = 1/n       Sum          dist(x,y)   
                            x,y in individus

            n : taille de la population
            x, y : deux individus de la population 
	    dist : distance d'edition entre deux genome d'individus
                   (ou le nombre de genne differents)

		 */

		double diversite = 0;
		for (Individu i1 : individus){
			for (Individu i2 : individus){
				diversite += i1.retourneDistanceAvec(i2);
			}
		}
		diversite /= retourneTaille();
		return diversite;
	}

}
