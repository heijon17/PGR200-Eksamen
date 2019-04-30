package no.kristiania.pgr200.core.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import no.kristiania.pgr200.core.database.Talk;

public class TalkDao extends AbstractDao<Talk> implements Dao<Talk> {

	public TalkDao(DataSource dataSource) {
		super(dataSource);
	}

	public void insert(Talk talk) throws SQLException {
		String sql = "insert into TALKS values (?, ?, ?, ?, ?)";
		save(sql, talk.getId(), talk.getTitle(), talk.getDescription(), talk.getTopic(), talk.getTimeslotId());
	}

	public Talk read(Object id) throws SQLException {
		String sql = "select * from TALKS where ID = CAST(? as uuid)";

		List<Talk> talks = list(sql, this::mapResultSet, id);
		if (talks.size() > 0)
			return talks.get(0);
		return null;
	}

	public void update(Talk talk) throws SQLException {
		String sql = "update TALKS set TITLE = ?, DESCRIPTION = ?, TOPIC = ? where ID = CAST(? as uuid)";
		save(sql, talk.getTitle(), talk.getDescription(), talk.getTopic(), talk.getId());
	}

	public void delete(Object id) throws SQLException {
		String sql = "delete from TALKS where ID = CAST(? as uuid)";
		save(sql, id);
	}

	public List<Talk> listAll() throws SQLException {
		String sql = "select * from TALKS";
		return list(sql, this::mapResultSet);
	}

	public Talk join(Object id) throws SQLException {
		String sql = "select TALKS.ID, TITLE, DESCRIPTION, TOPIC, TIMESLOT_ID from TALKS join TIMESLOTS on TIMESLOT_ID = TIMESLOTS.ID "
				+ "where TALKS.TIMESLOT_ID = CAST(? as uuid)";
		List<Talk> talks = list(sql, this::mapResultSet, id);
		if (talks.size() > 0)
			return talks.get(0);
		return null;
	}

	public Talk mapResultSet(ResultSet rs) throws SQLException {
		return new Talk((UUID) rs.getObject(1), rs.getString(2), rs.getString(3), rs.getString(4),
				(UUID) rs.getObject(5));
	}
}
