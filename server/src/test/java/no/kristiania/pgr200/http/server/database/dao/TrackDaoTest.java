package no.kristiania.pgr200.http.server.database.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;

import org.junit.Test;

import no.kristiania.pgr200.core.database.Track;
import no.kristiania.pgr200.core.database.dao.TrackDao;
import no.kristiania.pgr200.http.server.database.TestDataSource;


public class TrackDaoTest {

	@Test
	public void shouldAddTrack() throws SQLException {
		TrackDao trackDao = new TrackDao(TestDataSource.createDataSource());
		Track track = new Track("Java");
		trackDao.insert(track);

		assertThat(trackDao.read(track.getId())).isEqualToComparingFieldByField(track);
	}

	@Test
	public void shouldUpdateTrack() throws SQLException {
		TrackDao trackDao = new TrackDao(TestDataSource.createDataSource());
		Track track = new Track("Java");
		trackDao.insert(track);

		Track newTrack = new Track(track.getId(), "Hardware");
		trackDao.update(newTrack);

		assertThat(trackDao.read(track.getId())).isEqualToComparingFieldByField(newTrack);
	}

	@Test
	public void shouldDeleteTrack() throws SQLException {
		TrackDao trackDao = new TrackDao(TestDataSource.createDataSource());
		Track track = new Track("Java");
		trackDao.insert(track);

		assertThat(trackDao.read(track.getId())).isNotEqualTo(null);
		trackDao.delete(track.getId());

		assertThat(trackDao.read(track.getId())).isEqualTo(null);
	}

}
