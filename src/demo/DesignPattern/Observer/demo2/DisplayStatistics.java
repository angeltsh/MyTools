package demo.DesignPattern.Observer.demo2;

import java.util.Observable;
import java.util.Observer;

/**
 * չʾ��
 */
public class DisplayStatistics implements Observer, DisplayElement {
	Observable observable;

	private float temperature;
	private float humidity;

	private Float avg;
	private Float max;
	private Float min;

	private Float sum;// �¶�����
	private int num;// ���ݻ�ȡ����

	public DisplayStatistics(Observable observable) {
		this.observable = observable;
		sum = 0f;
	}

	@Override
	public void display() {
		System.out.println("Avg/Max/Min temperature: " + this.avg + "/"
				+ this.max + "/" + this.min);
	}

	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof WeatherData) {

			WeatherData weatherData = (WeatherData) o;
			this.temperature = weatherData.getTemperature();
			this.humidity = weatherData.getHumidity();

			num++;

			// ��������
			sum += temperature;
			avg = sum / num;

			if (min == null) {
				min = Float.MAX_VALUE;
			}
			if (max == null) {
				max = -Float.MIN_VALUE;
			}

			if (min > temperature) {
				min = temperature;
			}

			if (max < temperature) {
				max = temperature;
			}

			display();
		}
	}
}
