package main.java.com.lld.Splitwise.Entities;

public interface User extends Observer {
	int getId();
	String getName();
	void updateBalance(int userId, int amount);
	
}
