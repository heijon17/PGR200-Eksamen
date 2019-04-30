package no.kristiania.pgr200.http.server.database.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Test;

import no.kristiania.pgr200.core.database.Date;
import no.kristiania.pgr200.core.database.dao.DateDao;
import no.kristiania.pgr200.http.server.database.TestDataSource;


public class DateDaoTest {

	@Test
	public void shouldAddDate() throws SQLException {
		DateDao dateDao = new DateDao(TestDataSource.createDataSource());
		Date date = new Date(LocalDate.of(2018, 12, 15));
		dateDao.insert(date);

		assertThat(dateDao.read(date.getId())).isEqualToComparingFieldByField(date);
	}

	@Test
	public void shouldUpdateDate() throws SQLException {
		DateDao dateDao = new DateDao(TestDataSource.createDataSource());
		Date date = new Date(LocalDate.of(2018, 12, 15));
		dateDao.insert(date);

		Date newDate = new Date(date.getId(), LocalDate.of(2019, 1, 16));
		dateDao.update(newDate);

		assertThat(dateDao.read(date.getId())).isEqualToComparingFieldByField(newDate);
	}

	@Test
	public void shouldDeleteDate() throws SQLException {
		DateDao dateDao = new DateDao(TestDataSource.createDataSource());
		Date date = new Date(LocalDate.of(2018, 12, 15));
		dateDao.insert(date);

		assertThat(dateDao.read(date.getId())).isNotEqualTo(null);
		dateDao.delete(date.getId());

		assertThat(dateDao.read(date.getId())).isEqualTo(null);
	}

}
