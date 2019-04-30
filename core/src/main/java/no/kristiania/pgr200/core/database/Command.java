package no.kristiania.pgr200.core.database;

import javax.sql.DataSource;

import no.kristiania.pgr200.core.database.dao.ConferenceDao;
import no.kristiania.pgr200.core.database.dao.DateDao;
import no.kristiania.pgr200.core.database.dao.TalkDao;
import no.kristiania.pgr200.core.database.dao.TimeslotDao;
import no.kristiania.pgr200.core.database.dao.TrackDao;

public abstract class Command {

	private ProgramDataSource programDataSource = new ProgramDataSource();
	private DataSource dataSource = programDataSource.getDataSource();
	// private DataSource dataSource = TestDataSource.createDataSource();

	protected TalkDao talkDao = new TalkDao(dataSource);
	protected ConferenceDao confDao = new ConferenceDao(dataSource);
	protected TimeslotDao timeDao = new TimeslotDao(dataSource);
	protected DateDao dateDao = new DateDao(dataSource);
	protected TrackDao trackDao = new TrackDao(dataSource);

	protected ProgramDataSource getProgDataSource() {
		return programDataSource;
	}
}
