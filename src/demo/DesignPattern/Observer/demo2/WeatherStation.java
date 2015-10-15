package demo.DesignPattern.Observer.demo2;

/**
 * ���Թ����
 */
public class WeatherStation {
	public static void main(String[] args) {

		WeatherData weatherData = new WeatherData();

		DisplayCurrentConditions currentConditions = new DisplayCurrentConditions(
				weatherData);
		DisplayForecast forecast = new DisplayForecast(weatherData);
		DisplayStatistics statistics = new DisplayStatistics(weatherData);

		System.out.println("=======��һ�θ���=======");
		weatherData.setMeasurements(80, 65, 30.4f);
		currentConditions.update(weatherData, null);
		forecast.update(weatherData, null);
		statistics.update(weatherData, null);

		System.out.println("=======�ڶ��θ���=======");
		weatherData.setMeasurements(82, 70, 29.2f);
		currentConditions.update(weatherData, null);
		forecast.update(weatherData, null);
		statistics.update(weatherData, null);

		System.out.println("=======�����θ���=======");
		weatherData.setMeasurements(78, 90, 29.2f);
		currentConditions.update(weatherData, null);
		forecast.update(weatherData, null);
		statistics.update(weatherData, null);
	}

}
