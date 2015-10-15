package demo.DesignPattern.Observer.demo1;

/**
 * 展示版
 */
public class DisplayForecast implements Observer, DisplayElement {

	public float temperature;
	public float humidity;
	public Subject weatherData;

	public DisplayForecast(Subject weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}

	@Override
	public void display() {
		System.out.println("Watch out for cooler!");
	}

	@Override
	public void update(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;

		// 数据处理
		display();
	}
}
