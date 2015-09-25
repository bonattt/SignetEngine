package environment;

/**
 * The WeatherOVerride class is used to override the effects of weather while the player is outside it's reach
 * (ie. indoors, underground, etc...)
 * @author bonattt
 *
 */
public class WeatherOverride extends Weather {
	
	public Weather weatherOutside;

	// TODO implement weather override
	public WeatherOverride(Weather outside){
		weatherOutside = outside;
	}
}
