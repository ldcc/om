package org.ldccc.om3.dbm;

import org.ldccc.om3.dto.DTO;
import org.ldccc.om3.dto.Operator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public abstract class DTM<O extends DTO> {
	private Class<O> clazz;
	private String[] params;
	private Field[] fields;

	protected String base;

	protected DTM(Class<O> clazz, String[] params, String base) {
		this.clazz = clazz;
		this.params = params;
		fields = clazz.getDeclaredFields();
		if (base != null) {
			this.base = base;
		} else {
			throw new InvalidParameterException("Database can't be NULL");
		}
	}

	private O getO(Map<String, Object> map) {
		try {
			return clazz.getConstructor(Map.class).newInstance(map);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Map<String, Object> getMap(ResultSet set, ResultSetMetaData data) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		for (int i = 1; i <= data.getColumnCount(); i++) {
			String clsName = data.getColumnClassName(i);
			if (clsName.equals(String.class.getName())) {
				map.put(data.getColumnLabel(i), set.getString(i));
			} else if (clsName.equals(Integer.class.getName())) {
				map.put(data.getColumnLabel(i), set.getInt(i));
			} else if (clsName.equals(Date.class.getName())) {
				map.put(data.getColumnLabel(i), set.getDate(i));
			} else if (clsName.equals(Boolean.class.getName())) {
				map.put(data.getColumnLabel(i), set.getBoolean(i));
			} else {
				map.put(data.getColumnLabel(i), set.getObject(i));
			}
		}
		return map;
	}

	protected O get(Statement statement, String column, String value) {
		String sql = "SELECT * FROM " + base + " WHERE " + column + "='" + value + "';";
		return get(statement, sql);
	}

	protected List<O> getAll(Statement statement) {
		String sql = "SELECT * FROM " + base + ";";
		return getAll(statement, sql);
	}

	protected boolean add(Statement statement, O o) {
		String sql = "INSERT INTO " + base + Operator.INSTANCE.toInsertSQL(o, fields, params);
		return add(statement, sql);
	}

//	protected boolean update(Statement statement, O o) {
//		String sql = "UPDATE " + base + o.toUpdateSQL();
//		System.out.println(sql);
//		return update(statement, sql);
//	}

//	protected boolean delete(Statement statement, O o) {
//		String sql = "DELETE FROM " + base + o.toDeleteSQL();
//		return delete(statement, sql);
//	}

	protected O get(Statement statement, String sql) {
		try (ResultSet set = statement.executeQuery(sql)) {
			ResultSetMetaData data = set.getMetaData();
			set.first();
			Map<String, Object> map = getMap(set, data);
			return getO(map);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected List<O> getAll(Statement statement, String sql) {
		try (ResultSet set = statement.executeQuery(sql)) {
			ResultSetMetaData data = set.getMetaData();
			List<O> objs = new ArrayList<>();
			while (set.next()) {
				Map<String, Object> map = getMap(set, data);
				O o = getO(map);
				objs.add(o);
			}
			return objs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected boolean add(Statement statement, String sql) {
		try {
			return statement.executeUpdate(sql) != 0;
		} catch (SQLException e) {
			System.err.println("\nuser name is already used\n");
			e.printStackTrace();
			return false;
		}
	}

	protected boolean update(Statement statement, String sql) {
		try {
			return statement.executeUpdate(sql) != 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	protected boolean delete(Statement statement, String sql) {
		try {
			return statement.executeUpdate(sql) != 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
