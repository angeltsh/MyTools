package demo.DesignPattern.Observer.demo2;

import java.util.Observable;
import java.util.Observer;

/**
 * Õ¹Ê¾°æ
 */
public class DisplayCurrentConditions implements Observer, DisplayElement {
	Observable observable;

	private float temperature;
	private float humidity;

	public DisplayCurrentConditions(Observable observable) {
		this.observable = observable;
	}

	@Override
	public void display() {
		System.out.println("Current conditions: " + this.temperature
				+ "F degrees and " + this.humidity + "% humidity");
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof WeatherData) {
			WeatherData weatherData = (WeatherData) o;
			this.temperature = weatherData.getTemperature();
			this.humidity = weatherData.getHumidity();
			display();
		}
	}
}
