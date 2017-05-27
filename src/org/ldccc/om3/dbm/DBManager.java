package org.ldccc.om3.dbm;

import org.ldccc.om3.dto.PO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBManager {
	private static final DBManager singleton = new DBManager();

	public static DBManager getSingleton() {
		return singleton;
	}

	public <O extends PO> O findById(DTM<O> dtm, int id) {
		return findBy(dtm, PO.Companion.getID(), String.valueOf(id));
	}

	public <O extends PO> O findBy(DTM<O> dtm, String column, String value) {
		Connection conn = DBSource.getSingleton().getConnection();
		try (Statement statement = conn.createStatement()) {
			return dtm.findBy(statement, column, value);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBSource.getSingleton().closeConnection(conn);
		}
	}

	public <O extends PO> List<O> findsBy(DTM<O> dtm, String column, String value) {
		Connection conn = DBSource.getSingleton().getConnection();
		try (Statement statement = conn.createStatement()) {
			return dtm.findsBy(statement, column, value);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBSource.getSingleton().closeConnection(conn);
		}
	}

	public <O extends PO> List<O> findAll(DTM<O> dtm) {
		Connection conn = DBSource.getSingleton().getConnection();
		try (Statement statement = conn.createStatement()) {
			return dtm.findAll(statement);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBSource.getSingleton().closeConnection(conn);
		}
	}

	public <O extends PO> List<O> findWithCond(DTM<O> dtm, String sql) {
		Connection conn = DBSource.getSingleton().getConnection();
		try (Statement statement = conn.createStatement()) {
			return dtm.findWithCond(statement, sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBSource.getSingleton().closeConnection(conn);
		}
	}

	public final <O extends PO> boolean add(DTM<O> dtm, O o) {
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

	public <O extends PO> boolean update(DTM<O> dtm, O o) {
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

	@SafeVarargs
	public final <O extends PO> boolean delete(DTM<O> dtm, O... os) {
		Connection conn = DBSource.getSingleton().getConnection();
		try (Statement statement = conn.createStatement()) {
			return dtm.delete(statement, os);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBSource.getSingleton().closeConnection(conn);
		}
	}

}

