package algogenetique;

import java.util.Properties;
import java.util.prefs.InvalidPreferencesFormatException;

public class Fitness {

	private int evaluationsEffectuee = 0;

	private int nb_jobs = 4;

	private int[][] entree;

	public Fitness(int[][] e, int jobs) {
		entree = e;
		nb_jobs = jobs;
	}


	/**
	 * Retourne la taille du génome 
	 *
	 */
	public int RetourneTailleGenome() {
		return nb_jobs;
	}

	/**
	 * Méthode pour évaluer un individu.
	 * Retourne le nombre de bits correctes avec l'optimum.
	 */
	public int calculeFitness(Individu indiv) {

		int i, j;
		/* etat_jobs : correspond à l'avancement dans le temps de l'exécution de chacun des NB_JOBS jobs.
		 * A la fin, contient donc la date de terminaison de chacun des jobs.
		 */ 
		int[] etat_jobs = new int[nb_jobs];

		// On met la date de début minimum de chaque job dans etats_jobs
		for (i = 0; i < nb_jobs; i++){    
			etat_jobs[i] = entree[i][0];
		}

		// On avance le premier job dans la machine 1
		etat_jobs[indiv.retourneGene(0)] += entree[indiv.retourneGene(0)][1];

		// On avance chacun des autres jobs dans la machine 1 :
		// max(temps de démarrage + temps d'exé machine 1, temps de fin job précédent + temps d'exé machine 1)
		for (j = 1; j < nb_jobs; j++){
			int job = indiv.retourneGene(j);
			if (etat_jobs[job] > etat_jobs[indiv.retourneGene(j-1)]) {
				etat_jobs[job] += entree[job][1];
			} else {
				etat_jobs[job] = etat_jobs[indiv.retourneGene(j-1)] + entree[job][1];
			}
		}

		// On avance les jobs dans les deux autres machines
		for (i = 1; i < 3; i++){
			for (j = 0; j < nb_jobs; j++){
				int job = indiv.retourneGene(j);
				etat_jobs[job] += entree[job][i+1];
			}
		}

		// On calcule la somme des dates de fin
		int cmax = 0;
		for (i = 0; i < nb_jobs; i++){
			cmax += etat_jobs[i];
		}


		evaluationsEffectuee++;
		return cmax;
	}


	public int retourneNombreEvaluations(){
		return evaluationsEffectuee;
	}


}
