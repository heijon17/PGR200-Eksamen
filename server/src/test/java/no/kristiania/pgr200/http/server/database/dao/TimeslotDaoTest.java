package no.kristiania.pgr200.http.server.database.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.time.LocalTime;

import org.junit.Test;

import no.kristiania.pgr200.core.database.Timeslot;
import no.kristiania.pgr200.core.database.dao.TimeslotDao;
import no.kristiania.pgr200.http.server.database.TestDataSource;


public class TimeslotDaoTest {

	@Test
	public void shouldAddTimeslot() throws SQLException {
		TimeslotDao timeDao = new TimeslotDao(TestDataSource.createDataSource());
		Timeslot ts = new Timeslot(LocalTime.of(10, 0), LocalTime.of(14, 0));
		timeDao.insert(ts);

		assertThat(timeDao.read(ts.getId())).isEqualToComparingFieldByField(ts);
	}

	@Test
	public void shouldUpdateTimeslot() throws SQLException {
		TimeslotDao timeDao = new TimeslotDao(TestDataSource.createDataSource());
		Timeslot ts = new Timeslot(LocalTime.of(10, 0), LocalTime.of(14, 0));
		timeDao.insert(ts);

		Timeslot newTs = new Timeslot(ts.getId(), LocalTime.of(12, 0), LocalTime.of(16, 0));
		timeDao.update(newTs);

		assertThat(timeDao.read(ts.getId())).isEqualToComparingFieldByField(newTs);
	}

	@Test
	public void shouldDeleteTimeslot() throws SQLException {
		TimeslotDao timeDao = new TimeslotDao(TestDataSource.createDataSource());
		Timeslot ts = new Timeslot(LocalTime.of(10, 0), LocalTime.of(14, 0));
		timeDao.insert(ts);

		assertThat(timeDao.read(ts.getId())).isNotEqualTo(null);
		timeDao.delete(ts.getId());

		assertThat(timeDao.read(ts.getId())).isEqualTo(null);
	}
}
