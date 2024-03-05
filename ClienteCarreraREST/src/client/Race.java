package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Represents a race scenario with a specified number of athletes.
 * 
 * @author
 * Sergio García
 * Andrés Blázquez
 */
public class Race {
	
	private static final int NUM_ATHLETES = 4;	// Number of athletes participating in the race
	private static final String IP = "";		// IP address of the race server
	
	/**
     * Main method to initiate the race scenario.
     * @param args Command line arguments (not used)
     */
	public static void main(String[] args) {	
		 try {
	            restartRace();

	            ArrayList<Athlete> athletes = new ArrayList<>(); 

	            for (int i = 0; i < NUM_ATHLETES; i++) {
	                Athlete athlete = new Athlete(IP, "" + i);
	                athletes.add(athlete);
	            }

	            for (int i = 0; i < NUM_ATHLETES; i++) {
	                athletes.get(i).start();
	            }

	            for (Thread thread : athletes) {
	                thread.join();
	            }

	            displayScore(); 
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        }
	}
	
	/**
     * Restarts the race by sending an HTTP GET request to the race server.
     * @throws Exception If an error occurs during the HTTP request.
     */
	private static void restartRace() throws Exception {
		URL urlRestart = new URL ("http://" + IP + "/Carrera100REST/Carrera100/reinicio");
		HttpURLConnection connectionRestart = (HttpURLConnection) urlRestart.openConnection();
        connectionRestart.setRequestMethod("GET");
        if (connectionRestart.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed to reset race: HTTP error code : " + connectionRestart.getResponseCode());
        }
        connectionRestart.disconnect();
	}
	
	/**
     * Displays the race results by sending an HTTP GET request to the race server
     * and printing the results to the console.
     * @throws Exception If an error occurs during the HTTP request.
     */
	private static void displayScore() throws Exception {
		URL urlScores = new URL("http://" + IP + "/CarreraREST/Carrera100/resultados");
        HttpURLConnection connectionScores = (HttpURLConnection) urlScores.openConnection();
        connectionScores.setRequestMethod("GET");
        if (connectionScores.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed to get race results: HTTP error code : " + connectionScores.getResponseCode());
        }

        StringBuilder results = new StringBuilder();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(connectionScores.getInputStream()))) {
            rd.lines().forEach(line -> results.append("\t").append(line).append("\n"));
        }

        System.out.println("FINAL CARRERA:\n");
        System.out.println(results.toString());

        connectionScores.disconnect();
	}
}
