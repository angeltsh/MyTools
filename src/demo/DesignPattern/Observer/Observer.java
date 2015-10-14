package demo.DesignPattern.Observer;

/**
 * 观察者接口
 */
public interface Observer {

	/**
	 * 
	 * @param temp
	 *            温度
	 * @param humidity
	 *            湿度
	 * @param pressure
	 *            气压
	 */
	void update(float temp, float humidity, float pressure);
}
