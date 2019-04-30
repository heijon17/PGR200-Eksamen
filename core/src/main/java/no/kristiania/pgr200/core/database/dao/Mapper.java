package no.kristiania.pgr200.core.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T> {

	T map(ResultSet resultSet) throws SQLException;

}
