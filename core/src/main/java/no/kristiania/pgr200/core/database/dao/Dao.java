package no.kristiania.pgr200.core.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

	void insert(T object) throws SQLException;

	T read(Object id) throws SQLException;

	void update(T object) throws SQLException;

	void delete(Object id) throws SQLException;

	List<T> listAll() throws SQLException;

	T mapResultSet(ResultSet rs) throws SQLException;

}
