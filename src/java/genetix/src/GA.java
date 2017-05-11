
import algogenetique.*;

import java.util.Properties;
import java.util.prefs.InvalidPreferencesFormatException;

public class GA {


	public static void main(String[] args) {

		// Lecture des paramettre 
		Properties paramettres = readProperties("paramettres.properties");

		int[][] entree = new int[][] {{1,1,3,6}, {2,5,1,2}, {3,6,7,1}, {2,5,1,3}};
		int nb_jobs = 4;
		
		// Creation et parametrage de l'algorithme
		Algorithme algo = new Algorithme(entree, nb_jobs);
		try {
			algo.parametrerAlgorithme(paramettres);
		}
		catch (InvalidPreferencesFormatException e) {
			System.err.println(e.getMessage());
		}



		System.out.println("# Execution avec les parametre suivants:");
		System.out.println("# "+paramettres);


		// execute l'algorithme
		Individu solution = algo.executeJusquAFin();

		System.out.println("# Solution finale : "+ solution +" ("+ algo.getFitness().calculeFitness(solution)+")");
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
