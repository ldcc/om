package org.ldccc.om3.dbm;

import org.ldccc.om3.dto.PO;

import java.util.List;

public class OMer {
	private static final OMer singleton = new OMer();

	public static OMer getSingleton() {
		return singleton;
	}

	public <O extends PO> O findById(OM<O> OM, int id) {
		return findBy(OM, PO.Companion.getID(), String.valueOf(id));
	}

	public <O extends PO> O findBy(OM<O> OM, String column, String value) {
		return OM.findSingle(column, value);
	}

	public <O extends PO> List<O> findsBy(OM<O> OM, String column, String value) {
		return OM.findsBy(column, value);
	}

	public <O extends PO> List<O> findAll(OM<O> OM) {
		return OM.findAll();
	}

	public <O extends PO> List<O> findWithCond(OM<O> OM, String sql) {
		return OM.find(sql);
	}

	public final <O extends PO> boolean add(OM<O> OM, O o) {
		return OM.add(o);
	}

	public <O extends PO> boolean update(OM<O> OM, O o) {
		return OM.update(o);
	}

	public final <O extends PO> boolean delete(OM<O> OM, O o) {
		return OM.delete(o);
	}
}