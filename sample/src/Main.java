import dto.User;
import kotlin.Unit;
import org.ldccc.om.core.DML;

import java.util.List;

public class Main {
	/**
	 * Sample
	 */
	public static void main(String[] args) {
		getSingle();
		getAll();
		select1();
		select2();
		insert();
		update();
		delete();
	}

	private static final String USER_BASE = ".e_user";
	private static DML<User> uom = new DML<>(User.class, USER_BASE);

	/**
	 * find single
	 */
	private static void getSingle() {
		User user = uom.selectSingle(u -> {
			u.setId(4);
			return Unit.INSTANCE;
		});
		System.out.println(user);
	}

	/**
	 * find All
	 */
	private static void getAll() {
		List<User> users = uom.selectAll().execute();
		users.forEach(System.out::println);
	}

	/**
	 * select
	 */
	private static void select1() {
		List<User> users = uom.select(USER_BASE, select -> select.regexp("u_name", "a"));
		users.forEach(System.out::println);
	}

	private static void select2() {
		List<User> users = uom.select(user -> {
			user.setUName("%a%");
			return Unit.INSTANCE;
		}).orderBy("id").execute();
		users.forEach(System.out::println);
	}

	/**
	 * insert
	 */
	private static void insert() {
		uom.insert(user -> {
			user.setUName("sbc");
			user.setUPassword("Aa0000");
			return Unit.INSTANCE;
		});
	}

	/**
	 * update
	 */
	private static void update() {
		uom.update(user -> {
			user.setId(6);
			user.setUProvince("奥地利");
			user.setUPassword("Aa1111");
			return Unit.INSTANCE;
		});
	}

	/**
	 * delete
	 */
	private static void delete() {
		uom.delete(user -> {
			user.setId(8);
			return Unit.INSTANCE;
		});
	}
}
