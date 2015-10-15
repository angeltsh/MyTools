package demo.DesignPattern.Observer.demo1;

/**
 * չʾ��
 */
public class DisplayStatistics implements Observer, DisplayElement {

	public float temperature;
	public float humidity;
	public Subject weatherData;

	public Float avg;
	public Float max;
	public Float min;

	public Float sum;// �¶�����
	public int num;// ���ݻ�ȡ����

	public DisplayStatistics(Subject weatherData) {
		this.weatherData = weatherData;
		sum = 0f;
		weatherData.registerObserver(this);
	}

	@Override
	public void display() {
		System.out.println("Avg/Max/Min temperature: " + this.avg + "/"
				+ this.max + "/" + this.min);
	}

	@Override
	public void update(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
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
