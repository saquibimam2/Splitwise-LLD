package main.java.com.lld.Splitwise.EntitiesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.com.lld.Splitwise.Entities.Expense;
import main.java.com.lld.Splitwise.Entities.Group;
import main.java.com.lld.Splitwise.Entities.SplitStrategy;
import main.java.com.lld.Splitwise.Entities.User;
import main.java.com.lld.Splitwise.Enum.SplitEnum;
import main.java.com.lld.Splitwise.Optimizer.MinimumGroupTransaction;
import main.java.com.lld.Splitwise.SplitFactory.SplitFactory;

public class GroupImpl implements Group {
	
	public GroupImpl(int id) {
		this.setId(id);
		users = new HashMap<>();
		expenses = new ArrayList<>();
		userToBalancesMap = new HashMap<>();
	}

	private int id;
	Map<Integer, User> users;
	List<Expense> expenses;
	Map<Integer, Map<Integer,Double>> userToBalancesMap;

	@Override
	public void addUser(User user) {
		int userId = user.getId();
		users.putIfAbsent(userId, user);
		userToBalancesMap.putIfAbsent(userId, new HashMap<>());
	}

	@Override
	public void removeUser(int userId) {
		if(userToBalancesMap.get(userId).size()==0) {
			users.remove(userId);
			userToBalancesMap.remove(userId);
		} else {
			System.out.println("Cant remove user from group as user has unsettled balance");
		}
	}

	@Override
	public void updateBalanceMap(int paidUserId, Map<Integer, Double> transactions) {
		User paidUser = users.get(paidUserId);
		String paidUserName = paidUser.getName();
		for(Entry<Integer, Double> transaction : transactions.entrySet()) {
			int involvedUserId = transaction.getKey();
			if(involvedUserId==paidUserId) continue;
			double owedAmount = transaction.getValue();
			double newOwedAmount = owedAmount;
			Map<Integer,Double> involvedUserMap = userToBalancesMap.get(involvedUserId);
			Map<Integer,Double> paidUserMap = userToBalancesMap.get(paidUserId);
			if(involvedUserMap.get(paidUserId) != null) {
				newOwedAmount += involvedUserMap.get(paidUserId);
			}
			involvedUserMap.putIfAbsent(paidUserId, newOwedAmount);
			paidUserMap.putIfAbsent(involvedUserId, (-1)*newOwedAmount);
			User involvedUser = users.get(involvedUserId);
			String involvedUserName = involvedUser.getName();
			involvedUser.update(paidUserName + " paid for an Expense and "+ involvedUserName +" owe " + String.valueOf(owedAmount) + " amount to him.");
		}
		
	}

	@Override
	public void addExpense(int paidUserId, int amount, List<Integer> involvedUserId, SplitEnum splitType, List<Double> splitValues)  {
		SplitStrategy strategy = SplitFactory.getInstance().getSplitStrategy(splitType);
		Expense expense = new ExpenseImpl(paidUserId, amount, involvedUserId, strategy);
		Map<Integer,Double> transactions = expense.calculateSplit(splitValues);
		expenses.add(expense);
		updateBalanceMap(paidUserId, transactions);
	}

	@Override
	public void settlePayment(int payingUserId, int toUserId) {
		User paidUser = users.get(payingUserId);
		String paidUserName = paidUser.getName();
		User toUser = users.get(toUserId);
		Map<Integer,Double> paidUserMap = userToBalancesMap.get(payingUserId);
		Map<Integer,Double> toUserMap = userToBalancesMap.get(toUserId);
		double amountOwed = paidUserMap.get(toUserId);
		if(paidUserMap.get(toUserId) == null || amountOwed>=0) {
			paidUser.update("You dont owe any money");
			return;
		}
		paidUserMap.remove(toUserId);
		toUserMap.remove(payingUserId);
		toUser.update(paidUserName + " settled the payment with you by paying you "+ String.valueOf(amountOwed));
	}
	
	@Override
	public Double getUserBalanceInGroup(int userId) {
		Double amount = 0.0;
		Map<Integer,Double> userMap = userToBalancesMap.get(userId);
		for(Entry<Integer,Double> val : userMap.entrySet()) {
			amount += val.getValue();
		}
		return amount;
	}
	
	public void printTotalNumberOfTransactions() {
		int totalTranstions =0;
		System.out.println("*********Transactions************");
		for(Entry<Integer, Map<Integer,Double>> userToTransaction : userToBalancesMap.entrySet()) {
			int userId = userToTransaction.getKey();
			Map<Integer,Double> toUserBalance = userToTransaction.getValue();
			String name = users.get(userId).getName();
			for(Entry<Integer, Double> perUser : toUserBalance.entrySet()) {
				String toUser = users.get(perUser.getKey()).getName();
				if(perUser.getValue()>0) {
					System.out.println(toUser + " has to pay " + name + " an amount of " + perUser.getValue());	
					totalTranstions++;
				}
			}
		}
		System.out.println("total Transtions is equal to " + totalTranstions);	
		
	}
	
	public void simplifyTranstions() {
		MinimumGroupTransaction minimumGroupTransaction = MinimumGroupTransaction.getInstance();
		userToBalancesMap = minimumGroupTransaction.simplify(userToBalancesMap);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
