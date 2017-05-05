import dto.Notice;
import dto.Product;
import dto.User;
import org.ldccc.om2.dbm.DBManager;

import java.sql.Date;
import java.util.List;

import static org.ldccc.om2.dto.DTO.ID;

public class Main {
    /**
     * Sample
     */
    public static void main(String[] args) {
        getSingle();
//        getAllProducts();
//        addNotice();
//        updateUser();
//        deleteNotice();
    }

    private static String testSout() {
        System.out.println("sout");
        return "return sout";
    }

    /**
     * getting single object
     */
    private static void getSingle() {
        User user = DBManager.getSingleton().get(User.getDtm(), ID, "1");
//        System.out.println(user.getInt(User.ID));
        System.out.println(user.<Integer>getObj(User.ID));
        System.out.println(user.<String>getObj(User.USER_NAME));
    }

    /**
     * getting All object (A Object List)
     */
    private static void getAllProducts() {
        List<Product> products = DBManager.getSingleton().getAll(Product.getDtm());
        System.out.println(products);
    }

    /**
     * adding object
     */
    private static void addNotice() {
        int uid = 2;
        String title = "bobobobobobobobobobob";
        String content = "bobobobobobobobobobobbobobobobobobobobobobbobobobobobobobobobobbobobobobobobobobobob";
        Date date = new Date(new java.util.Date().getTime());
        String type = "news ";
        Notice notice = new Notice(uid, title, content, date, type);
        if (DBManager.getSingleton().add(Notice.getDtm(), notice)) {
            System.out.println(notice);
        } else {
            System.out.println("Something wrong, can't insert data to database");
        }
    }

    /**
     * updating object
     */
    private static void updateUser() {
        User user = DBManager.getSingleton().get(User.getDtm(), ID, "2");
        user.setObj(User.USER_AUTH, true);
        System.out.println(
                DBManager.getSingleton().update(User.getDtm(), user) ? user : "Update failed"
        );
    }

    /**
     * deleting object
     */
    private static void deleteNotice() {
        Notice notice = DBManager.getSingleton().get(Notice.getDtm(), ID, "19");
        System.out.println(
                DBManager.getSingleton().delete(Notice.getDtm(), notice) ? "Delete success" : "Delete failed"
        );
    }
}
