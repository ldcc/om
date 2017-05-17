import constant.C;
import dto.User;
import org.ldccc.om3.dbm.Aide;
import org.ldccc.om3.dbm.DBManager;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class Main {
	/**
	 * Sample
	 */
	public static void main(String[] args) {
//		C.INSTANCE.getDtu().setColumns(C.INSTANCE.getUParams());

//		getSingle();
//		getAll();
//		add();
//		update();
//		delete();

//		String[] ss = Arrays.stream(User.class.getDeclaredFields()).map(Main::field2column).toArray(String[]::new);
//		System.out.println(Arrays.toString(ss));
	}

	public static String field2column(Field field) {
		return Aide.INSTANCE.field2column(field);
	}

	/**
	 * findById single
	 */
	private static void getSingle() {
		User user = DBManager.getSingleton().findById(C.INSTANCE.getDtu(), 1);
		System.out.println(user);
	}

	/**
	 * findById All
	 */
	private static void getAll() {
		List<User> us = DBManager.getSingleton().findAll(C.INSTANCE.getDtu());
		System.out.println(us);
	}

	/**
	 * add
	 */
	private static void add() {
		User user1 = new User("sad", "123", false, new Date(new java.util.Date().getTime()), 0);
		User user2 = new User("68", "123", false, new Date(new java.util.Date().getTime()), 0);
		User user3 = new User("yiot", "123", false, new Date(new java.util.Date().getTime()), 0);
		System.out.println(
				DBManager.getSingleton().add(C.INSTANCE.getDtu(), user1, user2, user3) ? Arrays.asList(user1, user2, user3) : "Insert failed"
		);
	}

	/**
	 * update
	 */
	private static void update() {
		User user = DBManager.getSingleton().findById(C.INSTANCE.getDtu(), 22);
		user.setAuth(true);
		System.out.println(
				DBManager.getSingleton().update(C.INSTANCE.getDtu(), user) ? user : "Update failed"
		);
	}

	/**
	 * delete
	 */
	private static void delete() {
		User user1 = DBManager.getSingleton().findById(C.INSTANCE.getDtu(), 23);
		User user2 = DBManager.getSingleton().findById(C.INSTANCE.getDtu(), 24);
		User user3 = DBManager.getSingleton().findById(C.INSTANCE.getDtu(), 25);
		System.out.println(
				DBManager.getSingleton().delete(C.INSTANCE.getDtu(), user1, user2, user3) ? Arrays.asList(user1, user2, user3) : "Delete failed"
		);
	}
}
