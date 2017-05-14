import constant.C;
import dto.User;
import org.ldccc.om3.dbm.DBManager;
import org.ldccc.om3.dbm.Aide;

import java.sql.Date;
import java.util.List;

public class Main {
	/**
	 * Sample
	 */
	public static void main(String[] args) {
//		getSingle();
//		getAll();
//		add();
//		update();
//		delete();

		String s = Aide.INSTANCE.camel2muji("userName");
		System.out.println(s);
		C.INSTANCE.getDtu().setParams(C.INSTANCE.getUParams());
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
		User user = new User("2224", "123", false, new Date(new java.util.Date().getTime()), 0);
		System.out.println(
				DBManager.getSingleton().add(C.INSTANCE.getDtu(), user) ? user : "Insert failed"
		);
	}

	/**
	 * update
	 */
	private static void update() {
		User user = DBManager.getSingleton().get(C.INSTANCE.getDtu(), User.Companion.getID(), "12");
		user.setAuth(true);
		System.out.println(
				DBManager.getSingleton().update(C.INSTANCE.getDtu(), user) ? user : "Update failed"
		);
	}

	/**
	 * delete
	 */
	private static void delete() {
		User user = DBManager.getSingleton().get(C.INSTANCE.getDtu(), User.Companion.getID(), "15");
		System.out.println(
				DBManager.getSingleton().delete(C.INSTANCE.getDtu(), user) ? user : "Delete failed"
		);
	}
}
