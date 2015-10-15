package demo.DesignPattern.Observer.demo2;

/**
 * ²âÊÔ¹«¸æ°å
 */
public class WeatherStation {
	public static void main(String[] args) {

		WeatherData weatherData = new WeatherData();

		DisplayCurrentConditions currentConditions = new DisplayCurrentConditions(
				weatherData);

		weatherData.setMeasurements(80, 65, 30.4f);
		currentConditions.update(weatherData, null);

		weatherData.setMeasurements(82, 70, 29.2f);
		weatherData.setMeasurements(78, 90, 29.2f);
		currentConditions.update(weatherData, null);

	}

}
