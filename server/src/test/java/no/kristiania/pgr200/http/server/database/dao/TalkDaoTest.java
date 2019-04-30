package no.kristiania.pgr200.http.server.database.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.time.LocalTime;

import javax.sql.DataSource;

import org.junit.Test;

import no.kristiania.pgr200.core.database.Talk;
import no.kristiania.pgr200.core.database.Timeslot;
import no.kristiania.pgr200.core.database.dao.TalkDao;
import no.kristiania.pgr200.core.database.dao.TimeslotDao;
import no.kristiania.pgr200.http.server.database.TestDataSource;


public class TalkDaoTest {

	@Test
	public void shouldAddTalk() throws Exception {
		TalkDao talkDao = new TalkDao(TestDataSource.createDataSource());
		Talk talk = new Talk("Tittel", "Desc", "Topic");
		talkDao.insert(talk);

		assertThat(talkDao.read(talk.getId())).isEqualToComparingFieldByField(talk);
	}

	@Test
	public void shouldUpdateTalk() throws SQLException {
		TalkDao talkDao = new TalkDao(TestDataSource.createDataSource());
		Talk talk = new Talk("Tittel", "Desc", "Topic");
		talkDao.insert(talk);

		Talk newTalk = new Talk(talk.getId(), "Tittel2", "Desc2", "Topic2");
		talkDao.update(newTalk);

		assertThat(talkDao.read(talk.getId())).isEqualToComparingFieldByField(newTalk);
	}

	@Test
	public void shouldDeleteTalk() throws SQLException {
		TalkDao talkDao = new TalkDao(TestDataSource.createDataSource());
		Talk talk = new Talk("Tittel", "Desc", "Topic");
		talkDao.insert(talk);

		assertThat(talkDao.read(talk.getId())).isNotNull();
		talkDao.delete(talk.getId());

		assertThat(talkDao.read(talk.getId())).isNull();
	}

	@Test
	public void shouldJoinTalkAndTimeslot() throws SQLException {
		DataSource ds = TestDataSource.createDataSource();

		TimeslotDao timeDao = new TimeslotDao(ds);
		Timeslot timeslot = new Timeslot(LocalTime.of(10, 0), LocalTime.of(14, 0));
		Timeslot otherTimeslot = new Timeslot(LocalTime.of(12, 0), LocalTime.of(16, 0));
		timeDao.insert(timeslot);
		timeDao.insert(otherTimeslot);

		TalkDao talkDao = new TalkDao(ds);
		Talk talk = new Talk("Title", "Desc", "Topic");
		Talk otherTalk = new Talk("Title2", "Desc2", "Topic2");
		talk.setTimeslotId(timeslot.getId());
		talkDao.insert(otherTalk);
		talkDao.insert(talk);

		assertThat(talkDao.join(timeslot.getId())).isEqualToComparingFieldByField(talk);
	}

}
