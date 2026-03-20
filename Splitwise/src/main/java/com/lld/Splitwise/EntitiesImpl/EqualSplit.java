package main.java.com.lld.Splitwise.EntitiesImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.lld.Splitwise.Entities.SplitStrategy;

public class EqualSplit implements SplitStrategy {
	
	public EqualSplit() {
		super();
	}

	@Override
	public Map<Integer, Double> calculateSplit(int paidUserId, List<Integer> involved, int amount,
			List<Double> splitValues) {
		Map<Integer, Double> usersBalance = new HashMap<>();
		Double perUserShare = (double) (amount/involved.size());
		for(Integer user : involved) {
			if(user == paidUserId) usersBalance.putIfAbsent(user, amount - perUserShare);
			else usersBalance.putIfAbsent(user, (-1) * perUserShare);
		}
		return usersBalance;
	}

}
