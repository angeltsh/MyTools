package demo.DesignPattern.Observer.demo1;

/**
 * ²âÊÔ¹«¸æ°å
 */
public class WeatherStation {
	public static void main(String[] args) {

		SubjectWeatherData weatherData = new SubjectWeatherData();

		DisplayCurrentConditions currentDisplay = new DisplayCurrentConditions(
				weatherData);

		weatherData.setMeasurements(80, 65, 30.4f);
		weatherData.setMeasurements(60, 60, 20.4f);
	}

}
