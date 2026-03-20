package main.java.com.lld.Splitwise.Entities;

import java.util.List;
import java.util.Map;

public interface SplitStrategy {
	Map<Integer, Double> calculateSplit(int paidUserId, List<Integer> involved, int amount, List<Double> splitValues);
}
