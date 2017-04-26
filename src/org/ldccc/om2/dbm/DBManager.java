package org.ldccc.om2.dbm;

import org.ldccc.om2.dto.DTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBManager {
    /**
     * DBManager utilizing "Singleton Pattern" too
     */
    private static final DBManager singleton = new DBManager();

    public static DBManager getSingleton() {
        return singleton;
    }

    /**
     * Getting object with single column and value
     *
     * @param dtm    A tool for building object type of O
     * @param column A column name from your database
     * @param value  A data value from that column
     * @param <O>    As you can see, O extend from DTO
     * @return Object cast to O and then return that
     */
    public <O extends DTO> O get(DTM<O> dtm, String column, String value) {
        Connection conn = DBSource.getSingleton().getConnection();
        try (Statement statement = conn.createStatement()) {
            return dtm.get(statement, column, value);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBSource.getSingleton().closeConnection(conn);
        }
    }

    /**
     * Getting All object
     *
     * @param dtm A tool for building object type of O
     * @param <O> As you can see, O extend from DTO
     * @return All Object will add to List<O>, but no filter
     */
    public <O extends DTO> List<O> getAll(DTM<O> dtm) {
        Connection conn = DBSource.getSingleton().getConnection();
        try (Statement statement = conn.createStatement()) {
            return dtm.getAll(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBSource.getSingleton().closeConnection(conn);
        }
    }

    /**
     * Insert o under beneath the last row with dtm from database
     *
     * @param dtm A tool for building object type of O
     * @param o   just a object you wanted to do something
     * @param <O> As you can see, O extend from DTO
     * @return return the action whether success
     */
    public <O extends DTO> boolean add(DTM<O> dtm, O o) {
        Connection conn = DBSource.getSingleton().getConnection();
        try (Statement statement = conn.createStatement()) {
            return dtm.add(statement, o);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBSource.getSingleton().closeConnection(conn);
        }
    }

    /**
     * update o with dtm from database
     *
     * @param dtm A tool for building object type of O
     * @param o   just a object you wanted to do something
     * @param <O> As you can see, O extend from DTO
     * @return return the action whether success
     */
    public <O extends DTO> boolean update(DTM<O> dtm, O o) {
        Connection conn = DBSource.getSingleton().getConnection();
        try (Statement statement = conn.createStatement()) {
            return dtm.update(statement, o);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBSource.getSingleton().closeConnection(conn);
        }
    }

    /**
     * delete o(a whole row) with dtm from database
     *
     * @param dtm A tool for building object type of O
     * @param o   just a object you wanted to do something
     * @param <O> As you can see, O extend from DTO
     * @return return the action whether success
     */
    public <O extends DTO> boolean delete(DTM<O> dtm, O o) {
        Connection conn = DBSource.getSingleton().getConnection();
        try (Statement statement = conn.createStatement()) {
            return dtm.delete(statement, o);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBSource.getSingleton().closeConnection(conn);
        }
    }

}

