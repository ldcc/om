import constant.C;
import dto.DTU;
import dto.User;
import org.ldccc.om3.om.DML;

public class Main {
  /**
   * Sample
   */
  public static void main(String[] args) {
    getSingle();


  }

  /**
   * findById single
   */
  private static void getSingle() {
    DTU<User> dtu = C.INSTANCE.getDtu();
    User user = DML.INSTANCE.selectSingle(dtu, u -> {
      u.setId(4);
      return u;
    });
    System.out.println(user);
  }

  /**
   * findById All
   */
  private static void getAll() {

  }

  /**
   * add
   */
  private static void add() {
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
