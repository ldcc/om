package org.ldccc.om3.dbm;

import org.ldccc.om3.dto.DTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBManager {
	private static final DBManager singleton = new DBManager();

	public static DBManager getSingleton() {
		return singleton;
	}

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

