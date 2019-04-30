package no.kristiania.pgr200.core.database;

import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import no.kristiania.pgr200.core.http.HttpQuery;

/**
 * this class executes commands on the servers database based on query and
 * http-body.
 */

public class Commands extends Command {

	private HttpQuery query;
	private Gson gson = new Gson();
	private String body;
	
	public Commands() {
		
	}

	public Commands(HttpQuery query, String body) {
		this.query = query;
		this.body = body;
	}

//	COMMANDS
	public void insert() throws SQLException {
		if (query.getParameter("table").equals("talk"))
			insertTalk();
		if (query.getParameter("table").equals("conference"))
			insertConference();
		if (query.getParameter("table").equals("timeslot"))
			insertTimeslot();
		if (query.getParameter("table").equals("date"))
			insertDate();
		if (query.getParameter("table").equals("track"))
			insertTrack();
	}

	public String get() throws SQLException {
		switch (query.getParameter("table")) {
		case "talk":
			Talk talk = gson.fromJson(body, Talk.class);
			return gson.toJson(talkDao.read(talk.getId()));
		case "conference":
			Conference conf = gson.fromJson(body, Conference.class);
			return gson.toJson(confDao.read(conf.getId()));
		case "date":
			Date date = gson.fromJson(body, Date.class);
			return gson.toJson(dateDao.read(date.getId()));
		case "timeslot":
			Timeslot ts = gson.fromJson(body, Timeslot.class);
			return gson.toJson(timeDao.read(ts.getId()));
		case "track":
			Track track = gson.fromJson(body, Track.class);
			return gson.toJson(trackDao.read(track.getId()));
		default:
			return "Wrong table";
		}
	}

	public void delete() throws SQLException {
		switch (query.getParameter("table")) {
		case "talk":
			Talk talk = gson.fromJson(body, Talk.class);
			talkDao.delete(talk);
		case "conference":
			Conference conf = gson.fromJson(body, Conference.class);
			confDao.delete(conf);
		case "date":
			Date date = gson.fromJson(body, Date.class);
			dateDao.delete(date);
		case "timeslot":
			Timeslot timeslot = gson.fromJson(body, Timeslot.class);
			timeDao.delete(timeslot);
		case "track":
			Track track = gson.fromJson(body, Track.class);
			trackDao.delete(track);
		default:
		}
	}

	public void update() throws SQLException {
		switch (query.getParameter("table")) {
		case "talk":
			updateTalk();
		case "conference":
			updateConference();
		case "date":
			updateDate();
		case "timeslot":
			updateTimeslot();
		case "track":
			updateTrack();
		default:
		}
	}

	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> listAll() throws SQLException {
		if (query.getParameter("table").equals("talk"))
			return (ArrayList<T>) talkDao.listAll();
		if (query.getParameter("table").equals("conference"))
			return (ArrayList<T>) confDao.listAll();
		if (query.getParameter("table").equals("timeslot"))
			return (ArrayList<T>) timeDao.listAll();
		if (query.getParameter("table").equals("date"))
			return (ArrayList<T>) dateDao.listAll();
		if (query.getParameter("table").equals("track"))
			return (ArrayList<T>) trackDao.listAll();
		return null;
	}

//	UPDATE
	private void updateTalk() throws SQLException {
		Talk talk = gson.fromJson(body, Talk.class);
		talkDao.update(talk);

	}

	private void updateConference() throws SQLException {
		Conference conf = gson.fromJson(body, Conference.class);
		confDao.update(conf);

	}

	private void updateTimeslot() throws SQLException {
		Timeslot timeslot = gson.fromJson(body, Timeslot.class);
		timeDao.update(timeslot);

	}

	private void updateDate() throws SQLException {
		Date date = gson.fromJson(body, Date.class);
		dateDao.update(date);
	}

	private void updateTrack() throws SQLException {
		Track track = gson.fromJson(body, Track.class);
		trackDao.update(track);
	}

//	INSERT
	private void insertTrack() throws SQLException {
		Track track = gson.fromJson(body, Track.class);
		trackDao.insert(track);
	}

	private void insertDate() throws SQLException {
		Date date = gson.fromJson(body, Date.class);
		dateDao.insert(date);
	}

	private void insertTimeslot() throws SQLException {
		Timeslot timeslot = gson.fromJson(body, Timeslot.class);
		timeDao.insert(timeslot);

	}

	private void insertConference() throws SQLException {
		Conference conf = gson.fromJson(body, Conference.class);
		confDao.insert(conf);

	}

	private void insertTalk() throws SQLException {
		Talk talk = gson.fromJson(body, Talk.class);
		talkDao.insert(talk);

	}

	public int stringToInt(String dateString) {
		return Integer.parseInt(dateString);
	}

	public void resetDb() {
		getProgDataSource().cleanDb();
	}
}
