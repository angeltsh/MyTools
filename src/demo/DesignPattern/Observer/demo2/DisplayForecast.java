package demo.DesignPattern.Observer.demo2;

import java.util.Observable;
import java.util.Observer;

/**
 * 展示版
 */
public class DisplayForecast implements Observer, DisplayElement {

	Observable observable;

	private float temperature;
	private float humidity;
	private String message;

	public DisplayForecast(Observable observable) {
		this.observable = observable;
	}

	@Override
	public void display() {
		System.out.println(this.message);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof WeatherData) {
			WeatherData weatherData = (WeatherData) o;
			this.temperature = weatherData.getTemperature();
			this.humidity = weatherData.getHumidity();

			// 数据处理
			if (humidity >= 70) {
				this.message = "Rainy weather!";
			} else if (temperature > 80) {
				this.message = "Watch out for cooler!";
			} else {
				this.message = "More fo the same!";
			}
			display();
		}
	}
}
