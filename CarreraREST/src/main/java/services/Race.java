package services;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Represents a RESTful service for managing a race.
 * 
 * @author
 * Sergio García
 * Andrés Blázquez
 */
@Path("Carrera100") // Path to the race class
public class Race {
	long startRace;			// Timestamp when the race starts
	Data data = new Data();	// Instance of Data class for race data

	// Barriers for coordinating race phases
	static CyclicBarrier preparado;
	static CyclicBarrier listo;
	static CyclicBarrier finish;
	
	/**
     * Waits until all athletes are prepared to start the race.
     */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("preparado")
	public void prepared() {
		try {
			preparado.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Waits until all athletes are ready and then starts the race.
     */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("listo")
	public void set() {
		try {
			listo.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		System.out.println("Ready");
		data.setStartRace(System.currentTimeMillis());
		data.setRace(false);
	}
	
	/**
     * Finishes the race for a given athlete and calculates the race time.
     * 
     * @param number the athlete's number
     * @return a string representation of the athlete's race time
     * @throws InterruptedException if the thread is interrupted
     * @throws BrokenBarrierException if the cyclic barrier is broken
     */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("llegada")
	public String finish( @QueryParam(value="number") int number ) throws InterruptedException, BrokenBarrierException {
		long startTime = data.getStartRace();
		long actualTime = System.currentTimeMillis();
		double totalTime = (actualTime - startTime) / 1000.0;
		
		String numberTime = String.format("Number: %d \t Time: %.2f s", number, totalTime);
		
		data.getFinishAthlete().add(numberTime);
		finish.await();

		return numberTime;
	}
	
	/**
     * Retrieves the scores of all athletes in the race.
     * 
     * @return a string representation of all athlete scores
     */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("resultados")
	public String scores() {
		StringBuilder s = new StringBuilder();
		data.getFinishAthlete().forEach(athlete -> s.append(athlete).append("\n"));
	    return s.toString();
	}
	
	/**
     * Restarts the race if it has finished, resetting all race-related data.
     * 
     * @return a string indicating the start time of the race
     */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("reinicio")
	public String restart() {
		if(!data.isRace()) {
			data.setRace(true);
			data.clearAthletes();
			preparado = new CyclicBarrier(data.getAthletes());
			listo = new CyclicBarrier(data.getAthletes());
			finish = new CyclicBarrier(data.getAthletes());
		}
		
		return "" + startRace;
	}
}