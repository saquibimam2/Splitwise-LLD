package main.java.com.lld.Splitwise.EntitiesImpl;

import java.util.HashMap;
import java.util.Map;

import main.java.com.lld.Splitwise.Entities.User;

public class UserImpl implements User {
	
	int id;
	String name;
	Map<Integer,Double> balance;
	

	public UserImpl(int id, String name) {
		this.id = id;
		this.name = name;
		this.balance = new HashMap<>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<Integer, Double> getBalance() {
		return balance;
	}
	public void setBalance(Map<Integer, Double> balance) {
		this.balance = balance;
	}

	@Override
	public void updateBalance(int userId, int amount) {
		Double presentBalance = balance.get(userId) != null ? balance.get(userId) : 0;
		balance.put(userId, presentBalance+amount);
		
	} 
	@Override
	public void update(String msg) {
		System.out.println(msg);
	}

}
