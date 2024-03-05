package services;

import java.util.ArrayList;

public class Data {
	private static long startRace;
	private static boolean race = false;
	
	private static int athletes = 4;
	private static ArrayList<String> finishAthlete = new ArrayList<String>();
	
	public Data() {
		
	}
	
	public void clearAthletes() {
		finishAthlete.clear();
	}
	
	/*
	 * Getters and setters
	 */
	public static long getStartRace() {
		return startRace;
	}
	
	public static void setStartRace(long startRace) {
		Data.startRace = startRace;
	}
	
	public static boolean isRace() {
		return race;
	}
	
	public static void setRace(boolean race) {
		Data.race = race;
	}
	
	public static int getAthletes() {
		return athletes;
	}
	
	public static void setAthletes(int athletes) {
		Data.athletes = athletes;
	}
	
	public static ArrayList<String> getFinishAthlete() {
		return finishAthlete;
	}
	
	public static void setFinishAthlete(ArrayList<String> finishAthlete) {
		Data.finishAthlete = finishAthlete;
	}
}
