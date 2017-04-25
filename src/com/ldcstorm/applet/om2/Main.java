package com.ldcstorm.applet.om2;

import com.ldcstorm.applet.om2.dbm.DBManager;
import com.ldcstorm.applet.om2.dbm.DTNotice;
import com.ldcstorm.applet.om2.dbm.DTProduct;
import com.ldcstorm.applet.om2.dbm.DTUser;
import com.ldcstorm.applet.om2.dto.Notice;
import com.ldcstorm.applet.om2.dto.Product;
import com.ldcstorm.applet.om2.dto.User;

import java.sql.Date;
import java.util.List;

import static com.ldcstorm.applet.om2.dto.DTO.ID;

public class Main {
    /**
     * Sample
     */
    public static void main(String[] args) {
        System.out.println("Hello Database");
//        getSingle();
//        getAllProducts();
        addNotice();
//        updateUser();
//        deleteNotice();

//        System.out.println(testTry());
    }

    private static String testTry() {
        try {
            System.out.println("try");
            return testSout();
        } catch (Exception e) {
            System.out.println("err");
        } finally {
            System.out.println("finally");
            return "finally";
        }
    }

    private static String testSout() {
        System.out.println("sout");
        return "sout";
    }

    /**
     * getting single object
     */
    private static void getSingle() {
        User user = DBManager.getSingleton().get(DTUser.getDtm(), ID, "2");
        System.out.println(user.getInt(User.ID));
        System.out.println(user.<String>getObj(User.USER_NAME));
    }

    /**
     * getting All object (A Object List)
     */
    private static void getAllProducts() {
        List<Product> products = DBManager.getSingleton().getAll(DTProduct.getDtm());
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
        if (DBManager.getSingleton().add(DTNotice.getDtm(), notice)) {
            System.out.println(notice);
        } else {
            System.out.println("Something wrong, can't insert data to database");
        }
    }

    /**
     * updating object
     */
    private static void updateUser() {
        User user = DBManager.getSingleton().get(DTUser.getDtm(), ID, "2");
        user.setObj(User.USER_AUTH, true);
        System.out.println(
                DBManager.getSingleton().update(DTUser.getDtm(), user) ? user : "Update failed"
        );
    }

    /**
     * deleting object
     */
    private static void deleteNotice() {
        Notice notice = DBManager.getSingleton().get(DTNotice.getDtm(), ID, "19");
        System.out.println(
                DBManager.getSingleton().delete(DTNotice.getDtm(), notice) ? "Delete success" : "Delete failed"
        );
    }
}
