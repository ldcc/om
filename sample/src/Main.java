import dto.User1;
import dto.a;
import org.ldccc.om2.dbm.DBManager;

import static org.ldccc.om2.dto.DTO.ID;

public class Main {
	/**
	 * Sample
	 */
	public static void main(String[] args) {
		getSingle();
//        getAllProducts();
//		addNotice();
//        updateUser();
//        deleteNotice();
	}

	/**
	 * getting single object
	 */
	private static void getSingle() {
		User1 user1 = DBManager.getSingleton().get(a.INSTANCE.getDTM(), ID, "1");
		System.out.println(user1);


//		System.out.println(user1.<Integer>getObj(User1.ID));
//		System.out.println(user1.<String>getObj(User1.Companion.getUSER_NAME()));
	}

	/**
	 * getting All object (A Object List)
	 */
	private static void getAllProducts() {
	}

	/**
	 * adding object
	 */
	private static void addNotice() {
	}

	/**
	 * updating object
	 */
	private static void updateUser() {
		User1 user1 = DBManager.getSingleton().get(a.INSTANCE.getDTM(), ID, "2");
		user1.setObj(a.INSTANCE.getUSER_AUTH(), true);
		System.out.println(
				DBManager.getSingleton().update(a.INSTANCE.getDTM(), user1) ? user1 : "Update failed"
		);
	}

	/**
	 * deleting object
	 */
	private static void deleteNotice() {
	}
}
