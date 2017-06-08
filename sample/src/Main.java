import dto.DTU;
import dto.User;
import kotlin.Unit;

import java.util.List;

public class Main {
	/**
	 * Sample
	 */
	public static void main(String[] args) {
		getSingle();
		getAll();
		select();
		insert();
		update();
		delete();
	}

	private static DTU<User> dtu = C.INSTANCE.getUom();

	/**
	 * find single
	 */
	private static void getSingle() {
		User user = dtu.selectSingle(u -> {
			u.setId(4);
			return Unit.INSTANCE;
		});
		System.out.println(user);
	}

	/**
	 * find All
	 */
	private static void getAll() {
		List<User> users = dtu.selectAll().orderBy().execute();
		System.out.println(users);
	}

	/**
	 * select
	 */
	private static void select() {
		dtu.select(select -> select.regexp("u_name", "a"))
				;
	}

	/**
	 * insert
	 */
	private static void insert() {

	}

	/**
	 * update
	 */
	private static void update() {

	}

	/**
	 * delete
	 */
	private static void delete() {

	}
}
