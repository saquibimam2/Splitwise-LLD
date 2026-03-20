package main.java.com.lld.Splitwise.Entities;

import java.util.List;
import java.util.Map;

public interface Expense {

	Map<Integer, Double> calculateSplit(List<Double> splitValues);
}
