package main.java.com.lld.Splitwise.EntitiesImpl;

import java.util.List;
import java.util.Map;

import main.java.com.lld.Splitwise.Entities.Expense;
import main.java.com.lld.Splitwise.Entities.SplitStrategy;

public class ExpenseImpl implements Expense {

	public int paidId;
	public int amount;
	public List<Integer> involved;
	public Map<Integer, Double> userToAmount;
	SplitStrategy splitStrategy;

	public ExpenseImpl(int paidId, int amount, List<Integer> involved, SplitStrategy splitStrategy) {
		this.paidId = paidId;
		this.amount = amount;
		this.involved = involved;
		this.splitStrategy = splitStrategy;
	}

	public int getPaidId() {
		return paidId;
	}

	public void setPaidId(int paidId) {
		this.paidId = paidId;
	}

	public List<Integer> getInvolved() {
		return involved;
	}

	public void setInvolved(List<Integer> involved) {
		this.involved = involved;
	}

	public Map<Integer, Double> getUserToAmount() {
		return userToAmount;
	}

	public void setUserToAmount(Map<Integer, Double> userToAmount) {
		this.userToAmount = userToAmount;
	}

	public SplitStrategy getSplitStrategy() {
		return splitStrategy;
	}

	public void setSplitStrategy(SplitStrategy splitStrategy) {
		this.splitStrategy = splitStrategy;
	}

	@Override
	public Map<Integer, Double> calculateSplit(List<Double> splitValues) {
		return userToAmount = splitStrategy.calculateSplit(paidId, involved, amount, splitValues);
	}

}
