package algogenetique;


import java.util.Properties;
import java.util.prefs.InvalidPreferencesFormatException;

public class Algorithme {


	// Paramètres internes de l'algorithme  modifie par le fichier parametre
	private int     taillePopulationParents = 10;
	private int     taillePopulationEnfants = 50;
	private int     tailleTournoi           = 5;
	private double  tauxMutation            = 0.4;
	private int     maxGenerations          = 50;
	private int     maxEvaluations          = 300;
	private boolean verbose                 = false;  

	// Attributs pour le deroulement de l'algo
	private int      generationsEffectuee = 0;

	private Individu meilleurSolution;
	private int      meilleurFitness  = 0xffff;
	private int      pireFitness      = 0;

	/**
	 *  Execute l'algorithme jusqu'a la fin  (estFini() retourne vrai)
	 *   
	 */
	public Individu executeJusquAFin(){

		// Création de la population intiale (aléatoire)
		Population pop = new Population(taillePopulationParents);
		pop.evaluer();
		pop.trier();

		this.meilleurSolution = pop.retourneIndividu(0);

		// Boucle générationelle 
		while ( !estFini() ){
			// compteur de génération
			this.generationsEffectuee++;

			if (this.verbose){
				System.out.println("# =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
				System.out.println("# Population parent IN : "+pop);	
			}

			// on execute une generation 		       
			pop = executerUneGeneration(pop);

			if (this.verbose)
				System.out.println("# Population parent OUT: "+pop);	

			//  Récupération de la meilleure/pire solution 
			// (pop est sensé être trié)
			this.meilleurSolution = pop.retourneIndividu(0);
			this.meilleurFitness  = this.meilleurSolution.retourneFitness();
			this.pireFitness      = pop.retourneIndividu
					(this.taillePopulationParents-1).retourneFitness();

			// On affiche le nnuméro de la generation, le nombre d'evaluation
			// la fitness du meilleur et le meuilleur indiv et la diversité

			System.out.println("" + Fitness.retourneNombreEvaluations() + 
					" " + this.meilleurFitness +
					" " + this.pireFitness +
					" " + pop.retourneFitnessMoyenne() +
					" " + pop.retourneDiversite() +
					" " + this.generationsEffectuee);
		}

		return meilleurSolution;
	}

	/**
	 * Test la fin vis a vis d'un critère d'arret, 
	 * 
	 * - nombre max d'evaluation 
	 * - nombre max d'iteration 
	 * - fitness de zero
	 */
	public boolean estFini(){
		if(Fitness.retourneNombreEvaluations() >= this.maxEvaluations )
			return true;
		if(this.generationsEffectuee >= this.maxGenerations)
			return true;
		if(this.meilleurFitness <= 0)
			return true;
		return false;
	}


	/**
	 * Exécute une génération de l'algorithme génétique.
	 * 
	 * Entree : une population de parents 
	 * Sortie : une population de survivants
	 */
	public Population executerUneGeneration(Population parents) {

		/*  
	  TODO : Ecrire le code de l'algorithme suivant.   

	  L'algorithme d'une generation

	  Entree : une population de parents d'une taille donnee  
	  Sortie : une population de survivant de la meme taille 


	  - Creer les enfants depui les parent (cf. creerEnfants) 
	  - Evaluer les enfants
	  - Selectioner les parmi les enfants et les parent ceux qui survivent (cf. remplacement) 
	  - Retourner les survivants.  


		 */


	}



	/**
	 *  Methode pour creer les enfants depuis les parents
	 *
	 */
	private Population creerEnfants(Population parents){
		/*  
	  TODO : Ecrire le code de l'algorithme suivant.   

	  Algorythme de creation des enfants 

	  Entree : une population de parents 
	  Sortie : une population d'enfant de taille this.taillePopulationEnfants

	  - Selectionner 2 individus dans la population des parents (cf. selection)
	  - Les croiser pour en creer un nouvel enfant (cf. croisement)
	  - Modifier l'enfant par mutation (cf. mutation)
	  - Ajouter l'enfant a la population des enfants 


		 */

	}




	/**
	 * Méthode qui sélectionne un individu par tournoi de taille tailleTournoi
	 *
	 * On choisi tailleTournoi individus et on retourne le meilleur selon
	 * la valeur de fitness.
	 *
	 */

	private Individu selectionTournoi(Population pop){

		/*  
	  TODO : Ecrire le code de l'algorithme suivant.   

	  L'algorithme d'une selection par tourtnoi 

	  Entree : une population de parents d'une taille donne  
	  Sortie : un individu 

	  - Tirer aleatoirment tailleTournoi individu depui la population 
	  - choisir le meilleur, et le retourner. 

		 */


	}

	/** 
	 * Méthode qui prend deux parents et qui retourne un enfant 
	 * issus du croisement.  
	 *
	 * Le croisement utilisé est le croisement uniforme.
	 *    Pour chaque gène de l'enfant on choisi aléatoirement 
	 *    avec probabilité 0.5 le gène du parent 1 ou du parent 2
	 */

	private Individu croisementUniforme(Individu indiv1, Individu indiv2) {
		/*  
	  TODO : Ecrire le code de l'algorithme suivant.   

	  L'algorithme d'un croisement uniforme 

	  Entree : deux individus parent 
	  Sortie : un individu enfant

	  - Pour chaque gene de l'enfant, tirer a pile ou face 
            le gene du parent 1 ou du parent 2

		 */


	}

	/**
	 *  Méthode qui prend un individu et le mute 
	 *
	 *  La mutation se fait en parcourant tous les gènes de l'individu 
	 *  et les modifie avec une probabilité tauxMutation. 
	 *  Si le gène est a 1 on le change à 0 et inversement. 
	 */

	private void mutation(Individu indiv){

		/*  
	  TODO : Ecrire le code de l'algorithme suivant.   

	  L'algorithme d'une mutation

	  Entree : un individus individu 
	  Sortie : void 

	  - Pour chaque gene de l'individu
              avec probabilite tauxMutation changer sa valeur

		 */


	}

	/**
	 * Méthode qui sélectionne les survivants d'une génération pour la suivante 
	 * 
	 * On sélectionne les meilleurs parmi les enfants.
	 * 
	 */

	private Population Remplacement(Population parents, 
			Population enfants){

		/*  
	  TODO : Ecrire le code de l'algorithme suivant.   

	  L'algorithme du remplacement

	  Entree : deux population : parents, enfants 
	  Sortie : un population de survivants de meme taille que celle des parents 

	  - trier la population des enfants par ordre decroissant de fitness
	  - selectionner parmis les meilleurs autant qu'il y a de parent
	  - retourner cette nouvelle population 

		 */


	}

	/**
	 * Méthode  qui lit les paramètres.
	 *  
	 * Pour les paramètres manquants, on utilise les valeur par défaut  
	 * ci-dessus et on lance une exception.
	 *
	 */
	public void parametrerAlgorithme(Properties paramettres) throws InvalidPreferencesFormatException{
		String s;

		if ((s = paramettres.getProperty("taillePopulationParents")) != null) 
			taillePopulationParents = Integer.parseInt(s) ;
		else 
			throw new InvalidPreferencesFormatException
			("Paramètre taillePopulationParent manquant, utilisation de la valeur par défaut");

		if ((s = paramettres.getProperty("taillePopulationEnfants")) != null) 
			taillePopulationEnfants = Integer.parseInt(s) ;
		else 
			throw new InvalidPreferencesFormatException
			("Paramètre taillePopulationEnfants manquant, utilisation de la valeur par défaut");

		if ((s = paramettres.getProperty("tailleTournoi")) != null) 
			this.tailleTournoi = Integer.parseInt(s);
		else 
			throw new InvalidPreferencesFormatException
			("Paramètre tailleTournoi manquant, utilisation de la valeur par défaut");

		if ((s = paramettres.getProperty("tauxMutation")) != null) 
			this.tauxMutation = Double.parseDouble(s);
		else 
			throw new InvalidPreferencesFormatException
			("Paramètre tauxMutation manquant, utilisation de la valeur par défaut");

		if ((s = paramettres.getProperty("maxEvaluations")) != null) 
			this.maxEvaluations = Integer.parseInt(s);
		else 
			throw new InvalidPreferencesFormatException
			("Paramètre maxEvaluations manquant, utilisation de la valeur par défaut");

		if ((s = paramettres.getProperty("maxGenerations")) != null) 
			this.maxGenerations = Integer.parseInt(s);
		else 
			throw new InvalidPreferencesFormatException
			("Paramètre maxGenerations manquant, utilisation de la valeur par défaut");

		if ((s = paramettres.getProperty("verbose")) != null) 
			this.verbose = Boolean.parseBoolean(s);
		else 
			throw new InvalidPreferencesFormatException
			("Paramètre verbose manquant, utilisation de la valeur par défaut");


	}

}
