package main.java.com.lld.Splitwise.Optimizer;

public class AmountToUserId {
	public AmountToUserId(Double amount, int userId) {
		this.amount = amount;
		this.userId = userId;
	}
	
	
	Double amount;
	int userId;

	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
