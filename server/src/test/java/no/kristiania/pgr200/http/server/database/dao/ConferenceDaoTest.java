package no.kristiania.pgr200.http.server.database.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;

import org.junit.Test;

import no.kristiania.pgr200.core.database.Conference;
import no.kristiania.pgr200.core.database.dao.ConferenceDao;
import no.kristiania.pgr200.http.server.database.TestDataSource;


public class ConferenceDaoTest {

	@Test
	public void shouldAddConference() throws SQLException {
		ConferenceDao confDao = new ConferenceDao(TestDataSource.createDataSource());
		Conference conf = new Conference("TechConf");
		confDao.insert(conf);

		assertThat(confDao.read(conf.getId())).isEqualToComparingFieldByField(conf);
	}

	@Test
	public void shouldUpdateConference() throws SQLException {
		ConferenceDao confDao = new ConferenceDao(TestDataSource.createDataSource());
		Conference conf = new Conference("TechConf");
		confDao.insert(conf);

		Conference newConf = new Conference(conf.getId(), "TechConf2");
		confDao.update(newConf);

		assertThat(confDao.read(conf.getId())).isEqualToComparingFieldByField(newConf);
	}

	@Test
	public void shouldDeleteConference() throws SQLException {
		ConferenceDao confDao = new ConferenceDao(TestDataSource.createDataSource());
		Conference conf = new Conference("TechConf");
		confDao.insert(conf);

		assertThat(confDao.read(conf.getId())).isNotEqualTo(null);
		confDao.delete(conf.getId());

		assertThat(confDao.read(conf.getId())).isEqualTo(null);
	}
}
