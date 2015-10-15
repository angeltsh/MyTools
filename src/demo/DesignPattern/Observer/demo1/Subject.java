package demo.DesignPattern.Observer.demo1;

/**
 * ����
 */
public interface Subject {

	/**
	 * ע��۲���
	 * 
	 * @param o
	 *            �۲���
	 */
	void registerObserver(Observer o);

	/**
	 * ɾ���۲���
	 * 
	 * @param o
	 *            �۲���
	 */
	void removeObserver(Observer o);

	/**
	 * ֪ͨ�۲���
	 */
	void notifyObservers();
}
