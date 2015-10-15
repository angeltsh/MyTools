package demo.DesignPattern.Observer.demo2;

/**
 * 测试公告板
 */
public class WeatherStation {
	public static void main(String[] args) {

		WeatherData weatherData = new WeatherData();

		DisplayCurrentConditions currentConditions = new DisplayCurrentConditions(
				weatherData);
		DisplayForecast forecast = new DisplayForecast(weatherData);
		DisplayStatistics statistics = new DisplayStatistics(weatherData);

		System.out.println("=======第一次更新=======");
		weatherData.setMeasurements(80, 65, 30.4f);
		currentConditions.update(weatherData, null);
		forecast.update(weatherData, null);
		statistics.update(weatherData, null);

		System.out.println("=======第二次更新=======");
		weatherData.setMeasurements(82, 70, 29.2f);
		currentConditions.update(weatherData, null);
		forecast.update(weatherData, null);
		statistics.update(weatherData, null);

		System.out.println("=======第三次更新=======");
		weatherData.setMeasurements(78, 90, 29.2f);
		currentConditions.update(weatherData, null);
		forecast.update(weatherData, null);
		statistics.update(weatherData, null);
	}

}
