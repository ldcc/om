import constant.C;
import dto.User;
import org.ldccc.om3.dbm.OMer;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class Main {
	/**
	 * Sample
	 */
	public static void main(String[] args) {

	}

	/**
	 * findById single
	 */
	private static void getSingle() {
		User user1 = OMer.getSingleton().findById(C.INSTANCE.getDtu(), 3);
		User user2 = OMer.getSingleton().findBy(C.INSTANCE.getDtu(), "u_name", "scala");
		System.out.println(user1);
		System.out.println(user2);
	}

	/**
	 * findById All
	 */
	private static void getAll() {
		List<User> us = OMer.getSingleton().findAll(C.INSTANCE.getDtu());
		System.out.println(us);
	}

	/**
	 * add
	 */
	private static void add() {
		User user1 = new User(0, "haskell", "suckhaskell", "asd@asd", "12145411321", null, false, "", "", "", false, new Date(new java.util.Date().getTime()));
		System.out.println(
				OMer.getSingleton().add(C.INSTANCE.getDtu(), user1) ? user1 : "Insert failed"
		);
	}

	/**
	 * update
	 */
	private static void update() {
		User user = OMer.getSingleton().findById(C.INSTANCE.getDtu(), 2);
		user.setUActiveStatus(true);
		System.out.println(
				OMer.getSingleton().update(C.INSTANCE.getDtu(), user) ? user : "Update failed"
		);
	}

	/**
	 * delete
	 */
	private static void delete() {
		User user1 = OMer.getSingleton().findById(C.INSTANCE.getDtu(), 23);
		User user2 = OMer.getSingleton().findById(C.INSTANCE.getDtu(), 24);
		User user3 = OMer.getSingleton().findById(C.INSTANCE.getDtu(), 25);
		System.out.println(
				OMer.getSingleton().delete(C.INSTANCE.getDtu(), user1) ? Arrays.asList(user1, user2, user3) : "Delete failed"
		);
	}
}
