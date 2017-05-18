#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <float.h>
#include <time.h>
#include <limits.h>

#define NB_JOBS 4

// Affiche une liste d'entiers
void print_arr(int *arr, int size) {
	int i;

	for(i = 0; i < size; i++) {
		printf("%d\n", arr[i]);
	}
}

// Fonction d'heuristique aléatoire
int *heuristique_random(void) {
	
  int i, r, t;
  int *jobs = malloc(sizeof(int)*NB_JOBS); // Liste ordonnée des jobs

  srand(time(NULL));

  // Génération de la liste ordonnée des jobs
  for(i = 0; i < NB_JOBS; i++) jobs[i] = i;

  // Randomisation de la liste des jobs
  for(i = NB_JOBS-1; i >= 0; i--) {
    r = rand()%NB_JOBS;
    t = jobs[i];
    jobs[i] = jobs[r];
    jobs[r] = t;
  }

  return jobs;
}

// Retourne un tableau contenant les jobs triés par leur (date de début / somme durées exécution)
int* heuristique_debut_par_somme(int entree[4][NB_JOBS]){
  int i, j;
  double* rapports = malloc(NB_JOBS * sizeof(double));
  int* solution = malloc(NB_JOBS * sizeof(int));

  // On calcule les rapports
  for (i = 0; i < NB_JOBS; i++) {
    rapports[i] = (double)entree[i][0] / ((double)entree[i][1] + (double)entree[i][2] + (double)entree[i][3]);
  }

  // On parcourt les rapports en sortant le minimum à chaque fois, et on met l'indice de ce minimum dans la solution
  for (i = 0; i < NB_JOBS; i++) {    
    double min = DBL_MAX;
    int min_indice = -1;

    for (j = 0; j < NB_JOBS; j++){
      if (rapports[j] < min){
	min = rapports[j];
	min_indice = j;
      }
    }
    
    rapports[min_indice] = DBL_MAX;
    solution[i] = min_indice;
  }

  free(rapports);
  
  return solution;
}

// Retourne un tableau contenant les jobs triés par leur date de début
int* heuristique_debut(int entree[4][NB_JOBS]){
  int i, j;
  int* debuts = malloc(NB_JOBS * sizeof(double));
  int* solution = malloc(NB_JOBS * sizeof(int));

  // On "copie" les dates de début
  for (i = 0; i < NB_JOBS; i++) {    
    debuts[i] = entree[i][0];
  }

  // On parcourt les dates en sortant le minimum à chaque fois, et on met l'indice de ce minimum dans la solution
  for (i = 0; i < NB_JOBS; i++) {    
    int min = INT_MAX;
    int min_indice = -1;

    for (j = 0; j < NB_JOBS; j++){
      if (debuts[j] < min){
	min = debuts[j];
	min_indice = j;
      }
    }
    
    debuts[min_indice] = INT_MAX;
    solution[i] = min_indice;
  }

  free(debuts);
  
  return solution;
}

// Fonction d'heuristique "greedy"
int *heuristique_greedy(int entrees[4][NB_JOBS]) {
	int i, j, k, s;
	int *sums = malloc(sizeof(int)*NB_JOBS);
	int *indexes = malloc(sizeof(int)*NB_JOBS);
	int min = INT_MAX;

	// Somme des temps d'executions des jobs
	for(i = 0; i < NB_JOBS; i++) {
		s = 0;
		for(j = 1; j < 4; j++) s += entrees[i][j];
		sums[i] = s;
	}

	// Création de la liste ordonnées des index des jobs (par temps d'execution donc)
	for(k = 0; k < NB_JOBS; k++) {
		for(i = 0; i < NB_JOBS; i++) {
			if(sums[i] < min) {
				min = sums[i];
				indexes[k] = i;
			}
		}
		sums[indexes[k]] = INT_MAX;
		min = INT_MAX;
	}

	free(sums);

	return indexes;
}

int evaluer_solution(int solution[], int entree[NB_JOBS][4]){
  int i, j;
  /* etat_jobs : correspond à l'avancement dans le temps de l'exécution de chacun des NB_JOBS jobs.
   * A la fin, contient donc la date de terminaison de chacun des jobs.
   */ 
  int* etat_jobs = malloc(NB_JOBS * sizeof(int));

  // On met la date de début minimum de chaque job dans etats_jobs
  for (i = 0; i < NB_JOBS; i++){    
    etat_jobs[i] = entree[i][0];
  }

  // On avance le premier job dans la machine 1
  etat_jobs[solution[0]] += entree[solution[0]][1];
  
  // On avance chacun des autres jobs dans la machine 1 :
  // max(temps de démarrage + temps d'exé machine 1, temps de fin job précédent + temps d'exé machine 1)
  for (j = 1; j < NB_JOBS; j++){
    int job = solution[j];
    if (etat_jobs[job] > etat_jobs[solution[j-1]]) {
      etat_jobs[job] += entree[job][1];
    } else {
      etat_jobs[job] = etat_jobs[solution[j-1]] + entree[job][1];
    }
  }

  // On avance les jobs dans les deux autres machines
  for (i = 1; i < 3; i++){
    for (j = 0; j < NB_JOBS; j++){
      int job = solution[j];
      etat_jobs[job] += entree[job][i+1];
    }
  }

  // On calcule la somme des dates de fin
  int cmax = 0;
  for (i = 0; i < NB_JOBS; i++){
    cmax += etat_jobs[i];
  }

  free(etat_jobs);
  
  return cmax;
}


int main(void) {
  int entree[4][4] = {
	  {1,1,3,6},
	  {2,5,1,2},
	  {3,6,7,1},
	  {2,5,1,3}};
  int solution[4] = {1,3,0,2};
  int eval = evaluer_solution(solution, entree);
  printf("eval = %d\n", eval);
  return 0;
}
