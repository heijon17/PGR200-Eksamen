package no.kristiania.pgr200.core.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import no.kristiania.pgr200.core.database.Timeslot;

public class TimeslotDao extends AbstractDao<Timeslot> implements Dao<Timeslot> {

	public TimeslotDao(DataSource dataSource) {
		super(dataSource);
	}

	public void insert(Timeslot ts) throws SQLException {
		String sql = "insert into TIMESLOTS values (?, ?, ?, ?, ?)";

		save(sql, ts.getId(), ts.getTimeFrom(), ts.getTimeTo(), ts.getDaysId(), ts.getTracksId());
	}

	public Timeslot read(Object id) throws SQLException {
		String sql = "select * from TIMESLOTS where id = CAST(? as uuid)";

		List<Timeslot> timeslots = list(sql, this::mapResultSet, id);
		if (timeslots.size() > 0)
			return timeslots.get(0);
		return null;
	}

	public void update(Timeslot ts) throws SQLException {
		String sql = "update TIMESLOTS set TIME_FROM = ?, TIME_TO = ? where ID = CAST(? as uuid)";
		save(sql, ts.getTimeFrom(), ts.getTimeTo(), ts.getId());
	}

	public void delete(Object id) throws SQLException {
		String sql = "delete from TIMESLOTS where ID = CAST(? as uuid)";
		save(sql, id);
	}

	public List<Timeslot> listAll() throws SQLException {
		String sql = "select * from TIMESLOTS";
		return list(sql, this::mapResultSet);
	}

	public Timeslot join(Object id) throws SQLException {
		String sql = "select TIMESLOTS.ID, TIME_FROM, TIME_TO from TIMESLOTS " + "join DAYS on DAYS_ID = DAYS.ID "
				+ "where TIMESLOTS.ID = CAST(? as uuid)";
		List<Timeslot> talks = list(sql, this::mapResultSet, id);
		if (talks.size() > 0)
			return talks.get(0);
		return null;

	}

	public Timeslot mapResultSet(ResultSet rs) throws SQLException {
		return new Timeslot((UUID) rs.getObject(1), rs.getTime(2).toLocalTime(), rs.getTime(3).toLocalTime());
	}
}
