package demo.DesignPattern.Observer;

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
	void update(float temp, float humidity, float pressure);
}
