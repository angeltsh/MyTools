package demo.DesignPattern.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * ������Ϣ������
 */
public class SubjectWeatherData implements Subject {
	private List<Observer> observers;
	private float temperature;
	private float humidity;
	private float pressure;

	public SubjectWeatherData() {
		this.observers = new ArrayList<Observer>();
	}

	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		int i = this.observers.indexOf(o);
		if (i >= 0) {
			this.observers.remove(i);
		}

	}

	@Override
	public void notifyObservers() {
		for (Observer o : this.observers) {
			o.update(temperature, humidity, pressure);
		}
	}

	/**
	 * ������վ��ȡ��������
	 */
	public void measurementsChanged() {
		notifyObservers();
	}

	/**
	 * ���������������ʹ��
	 * 
	 * @param temperature
	 *            �¶�
	 * @param humidity
	 *            ʪ��
	 * @param pressure
	 *            ��ѹ
	 */
	public void setMeasurements(float temperature, float humidity,
			float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		measurementsChanged();
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public float getPressure() {
		return pressure;
	}

	public void setPressure(float pressure) {
		this.pressure = pressure;
	}

}
