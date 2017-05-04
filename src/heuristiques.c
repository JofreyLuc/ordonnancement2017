#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define NB_MACHINES 3
#define NB_JOBS 4


int evaluer_solution(int solution[], int entree[4][NB_JOBS]){
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
  for (i = 1; i < NB_MACHINES; i++){
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
  
  return cmax;
}

int main(int argc, char* argv[]){
  int entree[4][4] = {{1,1,3,6}, {2,5,1,2}, {3,6,7,1}, {2,5,1,3}};
  int solution[4] = {1,3,0,2};
  int eval = evaluer_solution(solution, entree);
  printf("eval = %d\n", eval);
  return 0;
}
