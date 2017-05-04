package algogenetique;

public class Individu implements Comparable{


	private int[] genes;             // la liste des gènes
	private int fitness = 0;         // la fitness de cet individu 
	private Fitness f;
	private boolean aevaluer = true; // pour ne pas evaluer plusieurs fois


	// Constructeur qui crée un individu 
	public Individu (Fitness fit){
		f = fit;
		int taille = f.RetourneTailleGenome();		
		this.genes = new int[taille];
	}

	// Initialise l'individu aléatoirement
	public void aleatoire() {
		for (int i = 0; i < genes.length; i++) genes[i] = i;
		
		for (int i = genes.length - 1; i >= 0; i--){
			int r = (int)Math.random()*(genes.length-1);
			int t = genes[i];
			genes[i] = genes[r];
			genes[r] = t;
		}		
	}

	// Retourne le gene d'indice index
	public int retourneGene(int index) {
		return genes[index];
	}

	// Met valeur dans le gene d'indice index
	public void fixerGene(int index, int valeur) {
		genes[index] = valeur;
		aevaluer = true;
	}

	// retourne la taille du genome
	public int retourneTaille() {
		return genes.length;
	}

	// retourne la fitness de l'individu
	public int retourneFitness() {
		this.evaluer();
		return fitness;
	}

	// evalue l'individu
	public void evaluer(){
		if (this.aevaluer) {
			this.fitness = f.calculeFitness(this);
			this.aevaluer = false;
		}
	}

	// force la réévaluation 
	public void mettreAEvaluer(){
		this.aevaluer = true;
	}

	// pour l'afichage 
	public String toString() {
		String geneString = "";
		for (int i = 0; i <this.genes.length; i++) {
			geneString += retourneGene(i);
		}
		return geneString;
	}

	// comparaison pour le trie 
	public int compareTo(Object indiv){
		return this.fitness - ((Individu)indiv).retourneFitness() ;
	}

	// calcule la distance de d'edition entre individu 
	public int retourneDistanceAvec(Individu indiv){
		int dist = 0;
		for (int i = 0; i <this.genes.length; i++)
			if(this.genes[i] != indiv.retourneGene(i))
				dist ++;

		return dist;
	}

}
