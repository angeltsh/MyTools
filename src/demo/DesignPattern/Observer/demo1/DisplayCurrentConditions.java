package demo.DesignPattern.Observer.demo1;

/**
 * Õ¹Ê¾°æ
 */
public class DisplayCurrentConditions implements Observer, DisplayElement {

	private float temperature;
	private float humidity;
	private Subject weatherData;

	public DisplayCurrentConditions(Subject weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}

	@Override
	public void display() {
		System.out.println("Current conditions: " + this.temperature
				+ "F degrees and " + this.humidity + "% humidity");
	}

	@Override
	public void update(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		display();
	}
}
