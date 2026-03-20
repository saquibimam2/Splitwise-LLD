package main.java.com.lld.Splitwise.EntitiesImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.lld.Splitwise.Entities.SplitStrategy;

public class ExactSplit implements SplitStrategy {
	
	public ExactSplit() {
		super();
	}

	@Override
	public Map<Integer, Double> calculateSplit(int paidUserId, List<Integer> involved, int amount,
			List<Double> splitValues) {
		Map<Integer, Double> usersBalance = new HashMap<>();
		for(int i=0;i<involved.size();i++) {
			int user = involved.get(i);
			Double userShare = splitValues.get(i);
			if(user == paidUserId) usersBalance.putIfAbsent(user, amount - userShare);
			else usersBalance.putIfAbsent(user, (-1) * userShare);
		}
		return usersBalance;
	}

}
