package demo.DesignPattern.Decorator;

/**
 * ×ÛºÏ¿§·È
 */
public class Mocha extends DecoratorCondiment {
	Beverage beverage;

	public Mocha(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription();
	}

	@Override
	public double cost() {
		return .20 + beverage.cost();
	}
}
