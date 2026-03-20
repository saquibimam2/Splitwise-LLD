package main.java.com.lld.Splitwise.SplitFactory;

import main.java.com.lld.Splitwise.Entities.SplitStrategy;
import main.java.com.lld.Splitwise.EntitiesImpl.EqualSplit;
import main.java.com.lld.Splitwise.EntitiesImpl.ExactSplit;
import main.java.com.lld.Splitwise.EntitiesImpl.PercentageSplit;
import main.java.com.lld.Splitwise.Enum.SplitEnum;

public class SplitFactory {

	private static SplitFactory factory = null;

	// Private constructor
	private SplitFactory() {
	}

	// Public static method to get the instance
	public static SplitFactory getInstance() {
		if (factory == null) {
			synchronized (SplitFactory.class) {
				if (factory == null)
					factory = new SplitFactory();
			}

		}
		return factory;
	}

	public SplitStrategy getSplitStrategy(SplitEnum splitType) {
		switch (splitType) {
			case EXACT:
				return new ExactSplit();
			case EQUAL:
				return new EqualSplit();
			case PERCENTAGE:
	            return new PercentageSplit();
	        default :
	        	throw new IllegalArgumentException("Invalid Split Type");
		}
	}
}
