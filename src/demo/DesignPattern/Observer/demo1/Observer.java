package demo.DesignPattern.Observer.demo1;

/**
 * �۲��߽ӿ�
 */
public interface Observer {

	/**
	 * 
	 * @param temp
	 *            �¶�
	 * @param humidity
	 *            ʪ��
	 * @param pressure
	 *            ��ѹ
	 */
	void update(float temperature, float humidity, float pressure);
}
