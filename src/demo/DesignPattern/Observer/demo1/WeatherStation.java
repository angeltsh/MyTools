package demo.DesignPattern.Observer.demo1;

/**
 * ²âÊÔ¹«¸æ°å
 */
public class WeatherStation {
	public static void main(String[] args) {

		SubjectWeatherData weatherData = new SubjectWeatherData();

		DisplayCurrentConditions currentConditions = new DisplayCurrentConditions(
				weatherData);
		DisplayStatisticsConditions statisticsConditions = new DisplayStatisticsConditions(
				weatherData);

		weatherData.setMeasurements(80, 65, 30.4f);
		weatherData.setMeasurements(82, 70, 29.2f);
	}

}
