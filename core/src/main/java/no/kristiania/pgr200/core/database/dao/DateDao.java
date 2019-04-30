package no.kristiania.pgr200.core.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import no.kristiania.pgr200.core.database.Date;

public class DateDao extends AbstractDao<Date> implements Dao<Date> {

	public DateDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void insert(Date date) throws SQLException {
		String sql = "insert into DAYS values (?, ?, ?)";
		save(sql, date.getId(), date.getDate(), date.getConferenceId());
	}

	@Override
	public Date read(Object id) throws SQLException {
		String sql = "select * from DAYS where ID = CAST(? as uuid)";
		List<Date> dates = list(sql, this::mapResultSet, id);
		if (dates.size() > 0)
			return dates.get(0);
		return null;
	}

	@Override
	public void update(Date date) throws SQLException {
		String sql = "update DAYS set DATE = ? where ID = CAST(? as uuid)";
		save(sql, date.getDate(), date.getId());
	}

	@Override
	public void delete(Object id) throws SQLException {
		String sql = "delete from DAYS where ID = CAST(? as uuid)";
		save(sql, id);
	}

	@Override
	public List<Date> listAll() throws SQLException {
		String sql = "select * from DAYS";
		return list(sql, this::mapResultSet);
	}

	public Date mapResultSet(ResultSet rs) throws SQLException {
		return new Date((UUID) rs.getObject(1), rs.getDate(2).toLocalDate());
	}

}
