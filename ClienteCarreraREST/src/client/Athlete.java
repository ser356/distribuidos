package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * Represents an athlete participating in a race.
 * 
 * @author
 * Sergio García
 * Andrés Blázquez
 */
public class Athlete extends Thread {
	String IP;
	String number;
	String time;
	
	/**
     * Constructs an Athlete object with the specified IP address and race number.
     * @param IP The IP address of the server hosting the race.
     * @param number The athlete's race number.
     */
	public Athlete(String IP, String number) {
		this.IP = IP;
		this.number = number;
	}
	
	/**
     * Constructs an Athlete object with the specified IP address and race number.
     * @param IP The IP address of the server hosting the race.
     * @param number The athlete's race number.
     */
	public void prepared() throws IOException {
		URL urlPrepared = new URL("http://" + IP + "/CarreraREST/Carrera100/preparado");
		HttpURLConnection connectionPrepared = (HttpURLConnection) urlPrepared.openConnection();
		connectionPrepared.setRequestMethod("GET");
		connectionPrepared.connect();
	}
	
	/**
     * Notifies the server that the athlete is ready to start the race.
     * @throws IOException If an I/O error occurs while performing the HTTP request.
     */
	public void ready() throws IOException {
		URL urlReady = new URL("http://" + IP + "/CarreraREST/Carrera100/listo");
		HttpURLConnection connectionReady = (HttpURLConnection) urlReady.openConnection();
		connectionReady.setRequestMethod("GET");
		if (connectionReady.getResponseCode() != 200) {
			throw new RuntimeException("Failed start race: HTTP error code : " + connectionReady.getResponseCode());
		} else {
			System.out.println("Vuelve");
		}
	}
	
	/**
     * Sends a request to the server to record the athlete's finish time.
     * @return The recorded finish time of the athlete.
     * @throws IOException If an I/O error occurs while performing the HTTP request.
     */
	private String finish() throws IOException {
		URL urlFinish = new URL("http://" + IP + "/CarreraREST/Carrera100/llegada?number=" + number);
        HttpURLConnection connectionFinish = (HttpURLConnection) urlFinish.openConnection();
        connectionFinish.setRequestMethod("GET");

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(connectionFinish.getInputStream()))) {
            return rd.lines().collect(Collectors.joining());
        }
	}
	
	/**
     * Causes the current thread to sleep for a random duration within the specified range.
     * @param minSeconds The minimum duration to sleep, in seconds.
     * @param maxSeconds The maximum duration to sleep, in seconds.
     */
	private void sleepRandomSeconds(double minSeconds, double maxSeconds) {
		try {
			long sleepTimeMillis = (long) ((minSeconds + Math.random() * (maxSeconds - minSeconds)) * 1000);
			Thread.sleep(sleepTimeMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Simulates the athlete participating in the race by executing the necessary steps.
     */
	public void run() {
		try {
			prepared();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sleepRandomSeconds(9.56, 11.76);
		
		try {
			time = finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
