package no.kristiania.pgr200.core.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import no.kristiania.pgr200.core.database.Track;

public class TrackDao extends AbstractDao<Track> implements Dao<Track> {

	public TrackDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void insert(Track track) throws SQLException {
		String sql = "insert into TRACKS values (?, ?, ?)";
		save(sql, track.getId(), track.getName(), track.getConferenceId());
	}

	@Override
	public Track read(Object id) throws SQLException {
		String sql = "select * from TRACKS where ID = CAST(? as uuid)";
		List<Track> tracks = list(sql, this::mapResultSet, id);
		if (tracks.size() > 0)
			return tracks.get(0);
		return null;
	}

	@Override
	public void update(Track track) throws SQLException {
		String sql = "update TRACKS set NAME = ? where ID = CAST(? as uuid)";
		save(sql, track.getName(), track.getId());
	}

	@Override
	public void delete(Object id) throws SQLException {
		String sql = "delete from TRACKS where ID = CAST(? as uuid)";
		save(sql, id);
	}

	@Override
	public List<Track> listAll() throws SQLException {
		String sql = "select * from TRACKS";
		return list(sql, this::mapResultSet);
	}

	public Track mapResultSet(ResultSet rs) throws SQLException {
		return new Track((UUID) rs.getObject(1), rs.getString(2));
	}
}
