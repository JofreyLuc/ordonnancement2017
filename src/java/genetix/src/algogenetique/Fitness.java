package algogenetique;

import java.util.Properties;
import java.util.prefs.InvalidPreferencesFormatException;

public class Fitness {
   
    static int[] optimum;
    static int evaluationsEffectuee = 0;


    public static void initialiseFitness(Properties paramettres)
	throws InvalidPreferencesFormatException {
	String s;
	
	// Optimum par defaut
	fixeOptimum("1111000000000000000000000000000000000000000000000000000000001111");

	// prendre un optimum depuis le fichier de prametres
	if ((s = paramettres.getProperty("optimum")) != null) 
	    fixeOptimum(s);
	else 
	    throw new InvalidPreferencesFormatException
		("Paramètre optimum manquant, utilisation de la valeur par défaut");

    }
  
    /**
     * Retourne la taille du génome 
     *
     */
    public static int RetourneTailleGenome() {
        return optimum.length;
    }


    /**
     * Fixe une valeur de l'optimum global.
     * Une chaine de 1 et 0. 
     * 
     */
    static void fixeOptimum(String nouveauOptimum) {
        optimum = new int[nouveauOptimum.length()];
	for (int i = 0; i < nouveauOptimum.length(); i++) {
            String character = nouveauOptimum.substring(i, i + 1);
            if (character.contains("0") || character.contains("1")) {
                optimum[i] = Integer.parseInt(character);
            } else {
                optimum[i] = 0;
            }
        }
    }


    public static String afficheOptimum() {
	String geneString = "";
        for (int i = 0; i < optimum.length; i++) {
	    if(optimum[i]==1)
		geneString += "1";
	    if(optimum[i]==0)
		geneString += "0";
        }

	return geneString;
    }


    /**
     * Méthode pour évaluer un individu.
     * Retourne le nombre de bits correctes avec l'optimum.
     */
    public static int calculeFitness(Individu indiv) {
        int fitness = optimum.length;
	for (int i = 0; i < indiv.retourneTaille() && i < optimum.length; i++) {
            if (indiv.retourneGene(i) == optimum[i]) {
                fitness--;
            }
        }
	
	evaluationsEffectuee++;
        return fitness;
    }
    

    public static int retourneNombreEvaluations(){
	return evaluationsEffectuee;
    }

 
}
