package no.kristiania.pgr200.core.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import no.kristiania.pgr200.core.database.Conference;

public class ConferenceDao extends AbstractDao<Conference> implements Dao<Conference> {

	public ConferenceDao(DataSource dataSource) {
		super(dataSource);
	}

	public void insert(Conference conf) throws SQLException {
		String sql = "insert into CONFERENCE values (CAST(? as uuid), ?)";
		save(sql, conf.getId(), conf.getName());
	}

	public Conference read(Object id) throws SQLException {
		String sql = "select * from CONFERENCE where id = CAST(? as uuid)";

		List<Conference> confs = list(sql, this::mapResultSet, id);
		if (confs.size() > 0)
			return confs.get(0);
		return null;
	}

	public void update(Conference conf) throws SQLException {
		String sql = "update CONFERENCE set NAME = ? where ID = CAST(? as uuid)";
		save(sql, conf.getName(), conf.getId());
	}

	public void delete(Object id) throws SQLException {
		String sql = "delete from CONFERENCE where ID = CAST(? as uuid)";
		save(sql, id);
	}

	@Override
	public List<Conference> listAll() throws SQLException {
		String sql = "select * from CONFERENCE";
		return list(sql, this::mapResultSet);
	}

	public Conference mapResultSet(ResultSet rs) throws SQLException {
		return new Conference((UUID) rs.getObject(1), rs.getString(2));
	}
}
