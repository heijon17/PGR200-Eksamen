package no.kristiania.pgr200.core.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public abstract class AbstractDao<T> {

	protected DataSource dataSource;

	public AbstractDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void save(String sql, Object... values) throws SQLException {

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				for (int i = 0; i < values.length; i++) {
					preparedStatement.setObject(i + 1, values[i]);
				}

				preparedStatement.executeUpdate();
			}
		}
	}

	public List<T> list(String sql, Mapper<T> mapper, Object... values) throws SQLException {

		List<T> results = new ArrayList<>();

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				for (int i = 0; i < values.length; i++) {
					preparedStatement.setObject(i + 1, values[i]);
				}

				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					results.add(mapper.map(rs));
				}
			}
		}
		return results;
	}

}
