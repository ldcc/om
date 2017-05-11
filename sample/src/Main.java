import constant.C;
import dto.User;
import org.ldccc.om3.dbm.DBManager;

import java.sql.Date;
import java.util.List;


class A {
	String name;

	@Override
	public String toString() {
		return name;
	}
}


public class Main {
	/**
	 * Sample
	 */
	public static void main(String[] args) {
//		getSingle();
//		getAll();
//		add();
//		update();
		delete();

//		Map<Integer, A> map = new HashMap<>();
//
//		A a = new A();
//		a.name = "tom";
//
//		map.put(1, a);
//		System.out.println(map);
//
//		a.name = "ldc";
//
//		System.out.println(map);

	}

	/**
	 * get single
	 */
	private static void getSingle() {
		User user = DBManager.getSingleton().get(C.INSTANCE.getDtu(), User.Companion.getID(), "1");
		System.out.println(user);
	}

	/**
	 * get All
	 */
	private static void getAll() {
		List<User> us = DBManager.getSingleton().getAll(C.INSTANCE.getDtu());
		System.out.println(us);
	}

	/**
	 * add
	 */
	private static void add() {
		User user = new User("hahahh", "123", false, new Date(new java.util.Date().getTime()), 0);
		System.out.println(
				DBManager.getSingleton().add(C.INSTANCE.getDtu(), user) ? user : "Insert failed"
		);
	}

	/**
	 * update
	 */
	private static void update() {
		User user = DBManager.getSingleton().get(C.INSTANCE.getDtu(), User.Companion.getID(), "14");
		user.setAuth(true);
		System.out.println(
				DBManager.getSingleton().update(C.INSTANCE.getDtu(), user) ? user : "Update failed"
		);
	}

	/**
	 * delete
	 */
	private static void delete() {
		User user = DBManager.getSingleton().get(C.INSTANCE.getDtu(), User.Companion.getID(), "14");
		System.out.println(
				DBManager.getSingleton().delete(C.INSTANCE.getDtu(), user)
		);
	}
}
