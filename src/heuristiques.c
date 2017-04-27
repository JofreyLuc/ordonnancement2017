#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define NB_MACHINES 3
#define NB_JOBS 4


int evaluer_solution(int solution[], int entree[4][NB_JOBS]){
  int i, j, u;
  int* etat_jobs = malloc(NB_JOBS * sizeof(int));
  for (i = 0; i < NB_JOBS; i++){
    etat_jobs[i] = entree[i][0];
  }

  for (i = 0; i < NB_JOBS; i++){
    printf("[%d] : %d, ", solution[i], etat_jobs[i]);
  }
  printf("\n\n");

  //Premier job
  etat_jobs[solution[0]] += entree[solution[0]][1];

  for (i = 0; i < NB_JOBS; i++){
    printf("[%d] : %d, ", solution[i], etat_jobs[i]);
  }
  printf("\n\n");
  
  //Machine 1
  for (j = 1; j < NB_JOBS; j++){
    int job = solution[j];
    if (etat_jobs[job] > etat_jobs[solution[j-1]]) {
      etat_jobs[job] += entree[job][1];
    } else {
      etat_jobs[job] = etat_jobs[job-1] + entree[job][1];
    }
  }

  for (i = 0; i < NB_JOBS; i++){
    printf("[%d] : %d, ", solution[i], etat_jobs[i]);
  }
  printf("\n\n");
  
  //Machines 2 et 3
  for (i = 1; i < NB_MACHINES; i++){
    for (j = 0; j < NB_JOBS; j++){
      int job = solution[j];
      etat_jobs[job] += entree[job][i+1];
    }
    for (u = 0; u < NB_JOBS; u++){
      printf("[%d] : %d, ", solution[u], etat_jobs[u]);
    }
    printf("\n\n");
  }

  int cmax = 0;
  for (i = 0; i < NB_JOBS; i++){
    cmax += etat_jobs[i];
  }

  for (i = 0; i < NB_JOBS; i++){
    printf("[%d] : %d, ", solution[i], etat_jobs[i]);
  }
  printf("\n\n");
  
  return cmax;
}

int main(int argc, char* argv[]){
  int entree[4][4] = {{1,1,3,6}, {2,5,1,2}, {3,6,7,1}, {2,5,1,3}};
  int solution[4] = {1,3,0,2};
  int eval = evaluer_solution(solution, entree);
  printf("eval = %d\n", eval);
  return 0;
}
