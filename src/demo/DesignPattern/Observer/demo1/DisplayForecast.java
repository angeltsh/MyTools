package demo.DesignPattern.Observer.demo1;

/**
 * 展示版
 */
public class DisplayForecast implements Observer, DisplayElement {

	private float temperature;
	private float humidity;
	private Subject weatherData;
	private String message;

	public DisplayForecast(Subject weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}

	@Override
	public void display() {
		System.out.println(this.message);
	}

	@Override
	public void update(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;

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
