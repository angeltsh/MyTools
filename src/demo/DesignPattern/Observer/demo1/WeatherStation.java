package demo.DesignPattern.Observer.demo1;

/**
 * ²âÊÔ¹«¸æ°å
 */
public class WeatherStation {
	public static void main(String[] args) {

		SubjectWeatherData weatherData = new SubjectWeatherData();

		DisplayCurrentConditions currentConditions = new DisplayCurrentConditions(
				weatherData);
		DisplayStatistics statistics = new DisplayStatistics(weatherData);
		DisplayForecast forecast = new DisplayForecast(weatherData);

		weatherData.setMeasurements(80, 65, 30.4f);
		weatherData.setMeasurements(82, 70, 29.2f);
		weatherData.setMeasurements(78, 90, 29.2f);
	}

}
