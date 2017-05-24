import constant.C;
import dto.User;
import org.ldcc.martin.dto.Good;
import org.ldcc.martin.dto.GoodType;
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
//		multable();
		multableupd();
	}

	private static void multableupd() {
		Good good1 = new Good(2, "", "", 4899.0, 4789.0, 100, "", "", new GoodType("","",""), new Date(new java.util.Date().getTime()));
		System.out.println(good1);
		DBManager.getSingleton().update(C.INSTANCE.getDtg(), good1);
		System.out.println(good1);
	}

	private static void multable() {
		Good good1 = DBManager.getSingleton().findById(C.INSTANCE.getDtg(), 2);
		System.out.println(good1);
	}

	public static String field2column(Field field) {
		return Aide.INSTANCE.field2column(field);
	}

	/**
	 * findById single
	 */
	private static void getSingle() {
		User user1 = DBManager.getSingleton().findById(C.INSTANCE.getDtu(), 3);
		User user2 = DBManager.getSingleton().findBy(C.INSTANCE.getDtu(), "u_name", "scala");
		System.out.println(user1);
		System.out.println(user2);
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
		User user1 = new User(0, "haskell", "suckhaskell", "asd@asd", "12145411321", null, false, "", "", "", false, new Date(new java.util.Date().getTime()));
		System.out.println(
				DBManager.getSingleton().add(C.INSTANCE.getDtu(), user1) ? user1 : "Insert failed"
		);
	}

	/**
	 * update
	 */
	private static void update() {
		User user = DBManager.getSingleton().findById(C.INSTANCE.getDtu(), 2);
		user.setUActiveStatus(true);
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
