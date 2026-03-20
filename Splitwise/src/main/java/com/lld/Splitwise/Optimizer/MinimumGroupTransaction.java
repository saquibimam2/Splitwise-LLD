package main.java.com.lld.Splitwise.Optimizer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class MinimumGroupTransaction {
	private static MinimumGroupTransaction instance = null;
	private MinimumGroupTransaction() {
		
	}
		
	public static MinimumGroupTransaction getInstance() {
		if(instance == null) {
			synchronized (MinimumGroupTransaction.class) {
				if(instance == null) {
					instance = new MinimumGroupTransaction();					
				}
			}
		}
		return instance;
	}
	
	public Map<Integer, Map<Integer,Double>> simplify(Map<Integer, Map<Integer,Double>> existingTranstionMap) {
		Map<Integer, Map<Integer,Double>> minTransactionMap = new HashMap<>();
		Map<Integer,Double> userIdToOwed = new HashMap<>();
		for(Entry<Integer, Map<Integer,Double>> userToTransaction : existingTranstionMap.entrySet()) {
			int userId = userToTransaction.getKey();
			Map<Integer,Double> toUserBalance = userToTransaction.getValue();
			for(Entry<Integer, Double> fromUser : toUserBalance.entrySet()) {
				if(fromUser.getValue()>0) {
					double fromVal = userIdToOwed.get(fromUser.getKey()) != null ? userIdToOwed.get(fromUser.getKey()) : 0;
					double toVal = userIdToOwed.get(userId) != null ? userIdToOwed.get(userId) : 0;
					userIdToOwed.put(userId, toVal+fromUser.getValue());
					userIdToOwed.put(fromUser.getKey(), fromVal-fromUser.getValue());
				}
			}
		}
		
		PriorityQueue<AmountToUserId> positive = new PriorityQueue<>((a, b) -> Double.compare(b.getAmount(), a.getAmount()));
		PriorityQueue<AmountToUserId> negative = new PriorityQueue<>((a, b) -> Double.compare(b.getAmount(), a.getAmount()));
		
		for(Entry<Integer,Double> entry : userIdToOwed.entrySet()) {
			if(entry.getValue()>0) {
				positive.add(new AmountToUserId(entry.getValue(),entry.getKey()));
			} else if(entry.getValue()<0){
				negative.add(new AmountToUserId((-1)*entry.getValue(),entry.getKey()));
			}
		}
		
		while(positive.size()>0 || negative.size()>0) {
			AmountToUserId pos = positive.poll();
			AmountToUserId nev = negative.poll();
			double topPos = pos.getAmount();
			double topNev = nev.getAmount();
			double val = Math.min(topPos, topNev);
			if (topPos>topNev) {
				positive.add(new AmountToUserId(topPos-topNev, pos.getUserId()));
			} else if(topNev>topPos){
				negative.add(new AmountToUserId(topNev-topPos, nev.getUserId()));
			}
			
			if(minTransactionMap.get(pos.getUserId()) == null) {
				minTransactionMap.put(pos.getUserId(), new HashMap<>());
			}
			minTransactionMap.get(pos.getUserId()).put(nev.getUserId(), val);
			if(minTransactionMap.get(nev.getUserId()) == null) {
				minTransactionMap.put(nev.getUserId(), new HashMap<>());
			}
			minTransactionMap.get(nev.getUserId()).put(pos.getUserId(), (-1)*val);
		}
		
		return minTransactionMap;
	}

}
