package main.java.com.lld.Splitwise;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.lld.Splitwise.Entities.Group;
import main.java.com.lld.Splitwise.Entities.User;
import main.java.com.lld.Splitwise.EntitiesImpl.GroupImpl;
import main.java.com.lld.Splitwise.EntitiesImpl.UserImpl;
import main.java.com.lld.Splitwise.Enum.SplitEnum;

// facasde class
public class Splitwise {

	private static Splitwise app = null;

	// Private constructor
	private Splitwise() {
		users = new HashMap<>();
		groups = new HashMap<>();
	}

	// Public static method to get the instance
	public static Splitwise getInstance() {
		if (app == null) {
			synchronized (Splitwise.class) {
				if (app == null)
					app = new Splitwise();
			}

		}
		return app;
	}

	Map<Integer, User> users;
	Map<Integer, Group> groups;

	public static void main(String[] args) {
		Splitwise app = Splitwise.getInstance();
		User firstUser = new UserImpl(1, "Saquib");
		User secondUser = new UserImpl(2, "Second");
		User thirdUser = new UserImpl(3, "Third");
		User fourthUser = new UserImpl(4, "Fourth");
		User fifthUser = new UserImpl(5, "Fifth");
		User sixthUser = new UserImpl(6, "Sixth");
		app.addUser(firstUser);
		app.addUser(secondUser);
		app.addUser(thirdUser);
		app.addUser(fourthUser);
		app.addUser(fifthUser);
		app.addUser(sixthUser);

		Group newGroup = new GroupImpl(1);
		app.addGroup(newGroup);
		newGroup.addUser(firstUser);
		newGroup.addUser(secondUser);
		newGroup.addUser(thirdUser);
		newGroup.addUser(fourthUser);
		newGroup.addUser(fifthUser);
		newGroup.addUser(sixthUser);

		List<Integer> involvedUsers = Arrays.asList(1, 2, 3, 4, 5);
		newGroup.addExpense(1, 1000, involvedUsers, SplitEnum.EQUAL, null);
		involvedUsers = Arrays.asList(2, 3, 6);
		List<Double> values = List.of(10.0, 40.0, 50.0);
		newGroup.addExpense(2, 600, involvedUsers, SplitEnum.PERCENTAGE, values);
		involvedUsers = Arrays.asList(3, 6);
		values = Arrays.asList(300.0, 0.0);
		newGroup.addExpense(6, 300, involvedUsers, SplitEnum.EXACT, values);
		System.out.println(newGroup.getUserBalanceInGroup(1));
		System.out.println(newGroup.getUserBalanceInGroup(2));
		System.out.println(newGroup.getUserBalanceInGroup(3));
		System.out.println(newGroup.getUserBalanceInGroup(4));
		System.out.println(newGroup.getUserBalanceInGroup(5));
		System.out.println(newGroup.getUserBalanceInGroup(6));
		((GroupImpl) newGroup).printTotalNumberOfTransactions();
		((GroupImpl) newGroup).simplifyTranstions();
		newGroup.settlePayment(3, 1);
		((GroupImpl) newGroup).printTotalNumberOfTransactions();

	}

	void addUser(User user) {
		int id = ((UserImpl) user).getId();
		users.putIfAbsent(id, user);
	}

	void addGroup(Group group) {
		int id = ((GroupImpl) group).getId();
		groups.putIfAbsent(id, group);
	}
}
