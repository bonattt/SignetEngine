package environment;

import misc.TextTools;

public class GameClock {
	
	private static GameClock instance;
	
	protected static int month_length = 30;
	protected static int day_length = 24000;
	public static final int MONTH_LENGTH = 1; // access code
	public static final int DAY_LENGTH = 2; // access code

	protected static int base_dusk_time = 20000;
	protected static int base_dark_time = 22000;
	protected static int midnight_hour = 23500;
	protected static int base_dawn_time = 70000;
	protected static int base_sunup_time = 90000;
	protected static int noon_hour = 11500;
	public static final int BASE_DUSK_TIME = 3; 	// access code
	public static final int BASE_DARK_TIME = 4; 	// access code
	public static final int MIDNIGHT_HOUR = 5; 		// access code
	public static final int BASE_DAWN_TIME = 6; 	// access code
	public static final int BASE_SUNUP_TIME = 7; 	// access code
	public static final int NOON_HOUR = 8; 			// access code
	
	protected static int day_lighting = 10;
	protected static int night_lighting = 3;
	public static final int DAY_LIGHTING = 9; 		// access code
	public static final int NIGHT_LIGHTING = 10; 	// access code

	protected static int seasonal_lighting_low = 0;
	protected static int seasonal_lighting_med = 1;
	protected static int seasonal_lighting_high = 2;
	public static final int SEASONAL_LIGHTING_LOW = 11; 	// access code
	public static final int SEASONAL_LIGHTING_MED = 12; 	// access code
	public static final int SEASONAL_LIGHTING_HIGH = 13; 	// access code
	
	protected static int new_moon = 1;				 // 1 (1)
	protected static int waxing_crecent = 2;		 // 2-6 (5)
	protected static int waxing_half_moon = 7;		 // 7-8 (2)
	protected static int waxing_gibous_moon = 9;	 // 9-13 (5)
	protected static int waxing_near_full_moon = 14; // 14 (1)
	protected static int full_moon = 15;			 // 15 (1)
	protected static int waning_near_full_moon = 16; // 16 (1)
	protected static int waning_gibous_moon = 17;	 // 17-21 (5)
	protected static int waning_half_moon = 22; 	 // 22-23(2)
	protected static int waning_crecent = 24; 		 // 24-28 (5)
	protected static int moonPeriod = 28; // numb days
	
	protected static double new_moon_lighting = 0;
	protected static double crecent_moon_lighting = .667;
	protected static double half_gibous_moon_lighting = 1.333;
	protected static double full_near_full_moon_lighting = 2;
	
	public static final int MOON_PERIOD = 0; 					// access code
	public static final int MOON_NEW = 14; 						// access code
	public static final int MOON_WAXING_CRECENT = 15; 			// access code
	public static final int MOON_WAXING_HALF_MOON = 16; 		// access code
	public static final int MOON_WAXING_GIBOUS_MOON = 17; 		// access code
	public static final int MOON_WAXING_NEAR_FULL_MOON = 18; 	// access code
	public static final int MOON_FULL = 19; 					// access code
	public static final int MOON_WANING_CRECENT = 20; 			// access code
	public static final int MOON_WANING_HALF_MOON = 21; 		// access code
	public static final int MOON_WANING_GIBOUS_MOON = 22;  		// access code
	public static final int MOON_WANING_NEAR_FULL_MOON = 23; 	// access code

	public static final int NEW_MOON_LIGHTING = 24;				// access code
	public static final int CRECENT_MOON_LIGHTING = 25;			// access code
	public static final int HALF_GIBOUS_MOON_LIGHTING = 26;		// access code
	public static final int FULL_NEAR_FULL_MOON_LIGHTING = 27;	// access code
	
	private int
	timeOfDay,
	date,
	month,
	lightLevel,
	moonCycle,
	seasonalLighting;
	
	public GameClock(){
		this(8000, 13, 5);
	}
	
	public GameClock(int startTime, int startDate, int startMonth){
		timeOfDay = startTime;
		date = startDate;
		month = startMonth;
		moonCycle = 0;
		
		instance = this;
		
		setSeasonalLighting();
	}
	
	public GameClock getInstance(){
		if (instance == null){
			return new GameClock();
		}
		return instance;
	}
	
	private void setLightLevel(){
		
		int dayLengthAdjustment = seasonalLighting * 1000;
		
		int dawn = base_dawn_time - dayLengthAdjustment;		// the time when the sun begins to rise.
		int day = base_sunup_time - dayLengthAdjustment;	// time the sun finishes rising
		int dusk = base_dusk_time + dayLengthAdjustment;		// time the sun begins to set
		int night = base_dark_time + dayLengthAdjustment;		// time the sun has finished setting.
		
		// If it's dark out
		if (timeOfDay < dawn || timeOfDay >= night){
			lightLevel = night_lighting + seasonalLighting + getMoonLight();
		}
		// if it's dawn.
		else if (timeOfDay >= dawn && timeOfDay < day){
			lightLevel = getLightingAtDawn(dawn, day);
		}
		
		// if it's light-out.
		else if (timeOfDay >= day && timeOfDay < dusk){
			lightLevel = day_lighting + seasonalLighting;
		}
		// if it's dusk
		else if (timeOfDay >= dusk && timeOfDay < night){
			lightLevel = getLightingAtDusk(dusk, night);
		}
		else {
			TextTools.display("something very strange happened to the time of day...[BUG ENCOUNTERED]");
		}		
	}
	
	private void setSeasonalLighting(){
		if (month == 1 || month == 2 || month == 12){
			seasonalLighting = seasonal_lighting_low;
		}
		else if (month == 6 || month == 7 || month == 8){
			seasonalLighting = seasonal_lighting_high;
		} else if (month > 12){
			throw new IllegalArgumentException("\n  date was outside the normal date range. \n      month > 12");
		} else if (month < 1){
			throw new IllegalArgumentException("\n  date was outside the normal date range. \n      month < 1");
		} else {
			// date is 3, 4, 5, 9, 10, or 11
			seasonalLighting = seasonal_lighting_med;
		}
	}
	private int getLightingAtDusk(int duskTime, int nightTime){
		int duskLength = nightTime - duskTime;				// how long dusk is.
		int time = timeOfDay - duskTime;					// how far inton the duks you currently are.
		double multiplier = time / ((double) duskLength);
		int difference = day_lighting - night_lighting;
		return night_lighting + seasonalLighting + (int) Math.round(difference * multiplier);
	}
	private int getLightingAtDawn(int dawnTime, int dayTime){
		int dawnLength = dayTime - dawnTime;				// how long dusk is.
		int time = timeOfDay - dawnTime;					// how far inton the duks you currently are.
		double multiplier = time / ((double) dawnLength);
		int difference = day_lighting - night_lighting;
		return day_lighting + seasonalLighting + (int) Math.round(difference*multiplier);
	}
	private int getMoonLight(){
		int moonlight;
		if(moonCycle == new_moon){
			moonlight = 0;
		} else if ((moonCycle >= waxing_crecent && moonCycle < waxing_half_moon) || (moonCycle >= waning_crecent)){
			moonlight = 1;
		} else if ((moonCycle >= waxing_half_moon && moonCycle < waxing_near_full_moon) || (moonCycle >= waning_gibous_moon && moonCycle < waning_crecent)){
			moonlight = 2;
		} else if (moonCycle >= waxing_near_full_moon && moonCycle < waning_near_full_moon){
			moonlight = 3;
		}
		else {
			System.out.println("The moon looks suspicious tonight....perhapse your code has fouled up.");
			moonlight = 0;
		}
		return moonlight;
	}
	public void passTime(int timePassed){
		timeOfDay += timePassed;
		int daysPassed = timeOfDay / day_length; // integer division
		timeOfDay -= day_length * daysPassed;
		passDays(daysPassed);
		
		
		// if in debug mode, print debug messages.
		if (Environment.getInstance() != null){
			if (Environment.getInstance().print_debugs){
				BUG_NET();
			}
		}
	}
	private void passDays(int daysPassed){
		date += daysPassed;
		int monthsPassed = date / month_length; // integer division
		date -= month_length * monthsPassed;
	}
	
	private void BUG_NET(){
		System.out.print("DEBUGING CLOCK...");
		boolean bugCaught = false;
		if (timeOfDay > day_length){
			System.err.println("\n BUG CAUGHT!!!");
			System.err.println("the time is too late:");
			System.err.println("time of day: " + timeOfDay);
			System.err.println("day length: " + day_length);
			bugCaught = true;
		}
		if (date > month_length){
			System.err.println("\n BUG CAUGHT!!!");
			System.err.println("the date is too late:");
			System.err.println("date: " + date);
			System.err.println("month length: " + month_length);
			bugCaught = true;
		}
		
		if (! bugCaught) {
			System.out.println("no bugs found.");
		}
	}
}
