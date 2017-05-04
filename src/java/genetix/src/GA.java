
import algogenetique.*;

import java.util.Properties;
import java.util.prefs.InvalidPreferencesFormatException;

public class GA {


    public static void main(String[] args) {

	// Lecture des paramettre 
	Properties paramettres = readProperties("paramettres.properties");

	// Creation et parametrage de l'algorithme
	Algorithme algo = new Algorithme();
	try {
	    algo.parametrerAlgorithme(paramettres);
	}
	catch (InvalidPreferencesFormatException e) {
	    System.err.println(e.getMessage());
	}
	
	// Parametrage de la fitness
	try {
	    Fitness.initialiseFitness(paramettres);
	}
	catch (InvalidPreferencesFormatException e) {
	    System.err.println(e.getMessage());
	}


	
	System.out.println("# Execution avec les parametre suivants:");
	System.out.println("# "+paramettres);
	System.out.println("# Optimum : "+Fitness.afficheOptimum());
	System.out.println("# Dimension : "+Fitness.RetourneTailleGenome());


	// execute l'algorithme
	Individu solution = algo.executeJusquAFin();
     
	System.out.println("# Solution finale : "+ solution
			   +" ("+ Fitness.calculeFitness(solution)+")");
	System.out.println("# Optimum         : "+Fitness.afficheOptimum());
	

    }



    static Properties readProperties(String fileName) {
	Properties properties = new Properties();
	try {
            java.io.FileInputStream fis = new java.io.FileInputStream(fileName);
            properties.load(fis);
            fis.close();
        } 
        catch(java.io.IOException e) { 
            System.out.println("File '" + 
			       fileName + "' not found, no options read");
            e.printStackTrace();
	    System.exit(1);
        }
	
        return properties;
    }
    


}
