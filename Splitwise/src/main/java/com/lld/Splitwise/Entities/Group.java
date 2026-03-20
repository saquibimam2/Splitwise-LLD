package main.java.com.lld.Splitwise.Entities;

import java.util.List;
import java.util.Map;

import main.java.com.lld.Splitwise.Enum.SplitEnum;

public interface Group {
//	void addUser(int userId);
	void removeUser(int userId);
//	void updateBalanceMap();
//	void addExpense();
//	void settlePayment();
	void addExpense(int paidUserId, int amount, List<Integer> involvedUserId, SplitEnum splitType,
			List<Double> splitValues);
	void updateBalanceMap(int paidUserId, Map<Integer, Double> transactions);
	void addUser(User user);
	void settlePayment(int payingUserId, int toUserId);
	Double getUserBalanceInGroup(int userId);
}
