package demo.DesignPattern.Observer.demo1;

/**
 * 主题
 */
public interface Subject {

	/**
	 * 注册观察者
	 * 
	 * @param o
	 *            观察者
	 */
	void registerObserver(Observer o);

	/**
	 * 删除观察者
	 * 
	 * @param o
	 *            观察者
	 */
	void removeObserver(Observer o);

	/**
	 * 通知观察者
	 */
	void notifyObservers();
}
