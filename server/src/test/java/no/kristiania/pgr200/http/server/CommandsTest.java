package no.kristiania.pgr200.http.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

import no.kristiania.pgr200.core.database.Commands;
import no.kristiania.pgr200.core.database.Conference;
import no.kristiania.pgr200.core.database.Date;
import no.kristiania.pgr200.core.database.Talk;
import no.kristiania.pgr200.core.database.Timeslot;
import no.kristiania.pgr200.core.database.Track;
import no.kristiania.pgr200.core.http.HttpQuery;


public class CommandsTest {
	
	private static Gson gson = new Gson();
	

	@Test
	public void ShouldInsertUpdateAndReadConference() throws SQLException {
		
		reset();
		
		String table = "conference";
		String name = "The best ever conference";
		String updateValue = "it wasnt that good sorry";
		Conference conference = new Conference(name);
		
		String jsonString = gson.toJson(conference);
		
		insert(table, jsonString);
		List<Object> received1 = listAll(table, "");
		
//		Check insert
		assertThat(received1.get(0)).isEqualToComparingFieldByField(conference);
		
//		update
		conference.setName(updateValue);
		jsonString = gson.toJson(conference);
		update(table, jsonString);
		
//		check update
		String received2 = get(table, jsonString);
		Conference receivedConf = gson.fromJson(received2, Conference.class);
		assertThat(receivedConf).isEqualToComparingFieldByField(conference);

		
	}
	
	@Test
	public void ShouldInsertAndReadTalk() throws SQLException {
		
		reset();
		
		String table = "talk";
		String title = "The best ever conference";
		String updateValue = "it wasnt that good sorry";
		Talk talk = new Talk(title, "description here", "topic also");
		
		String jsonString = gson.toJson(talk);
		
		insert(table, jsonString);
		List<Object> received1 = listAll(table, "");
		
//		Check insert
		assertThat(received1.get(0)).isEqualToComparingFieldByField(talk);
		
//		update
		talk.setTitle(updateValue);
		jsonString = gson.toJson(talk);
		update(table, jsonString);
		
//		check update
		String received2 = get(table, jsonString);
		Talk receivedTalk = gson.fromJson(received2, Talk.class);
		assertThat(receivedTalk).isEqualToComparingFieldByField(talk);

		

	}
	
	@Test
	public void ShouldInsertAndReadTrack() throws SQLException {
		
		reset();
		
		String table = "track";
		String name = "The best ever track";
		String updateValue = "it wasnt that good sorry";
		Track track = new Track(name);
		
		String jsonString = gson.toJson(track);
		
		insert(table, jsonString);
		List<Object> received1 = listAll(table, "");
		
//		Check insert
		assertThat(received1.get(0)).isEqualToComparingFieldByField(track);
		
//		update
		track.setName(updateValue);
		jsonString = gson.toJson(track);
		update(table, jsonString);
		
//		check update
		String received2 = get(table, jsonString);
		Track receivedTalk = gson.fromJson(received2, Track.class);
		assertThat(receivedTalk).isEqualToComparingFieldByField(track);

		

	}
	
	@Test
	public void ShouldInsertAndReadDate() throws SQLException {
		
		reset();
		
		String table = "date";
		LocalDate localDate = LocalDate.of(2018, 12, 15);
		LocalDate updatedLocalDate = LocalDate.of(2019, 12, 15);
		Date date = new Date(localDate);
		
		String jsonString = gson.toJson(date);
		
		insert(table, jsonString);
		List<Object> received1 = listAll(table, "");
		
//		Check insert
		assertThat(received1.get(0)).isEqualToComparingFieldByField(date);
		
//		update
		date.setDate(updatedLocalDate);
		jsonString = gson.toJson(date);
		update(table, jsonString);
		
//		check update
		String received2 = get(table, jsonString);
		Date receivedTalk = gson.fromJson(received2, Date.class);
		assertThat(receivedTalk).isEqualToComparingFieldByField(date);

		

	}
	
	@Test
	public void ShouldInsertAndReadTimeslot() throws SQLException {
		
		reset();
		
		String table = "timeslot";
		LocalTime localTime = LocalTime.of(10, 0);
		LocalTime updatedLocalTime = LocalTime.of(11, 0);
		Timeslot ts = new Timeslot(localTime, LocalTime.of(14, 0));
		
		String jsonString = gson.toJson(ts);
		
		insert(table, jsonString);
		List<Object> received1 = listAll(table, "");
		
//		Check insert
		assertThat(received1.get(0)).isEqualToComparingFieldByField(ts);
		
//		update
		ts.setTimeFrom(updatedLocalTime);
		jsonString = gson.toJson(ts);
		update(table, jsonString);
		
//		check update
		String received2 = get(table, jsonString);
		Timeslot receivedTalk = gson.fromJson(received2, Timeslot.class);
		assertThat(receivedTalk).isEqualToComparingFieldByField(ts);
		

	

	}

	private void insert(String table, String json) throws SQLException {
		String query = "method=insert&table=" + table;
		Commands commands = createNewCommandWithStringAndJson(query, json);
		commands.insert();
	}
	
	private <T> List<T> listAll(String table, String json) throws SQLException {
		String string = "method=listall&table=" + table;
		Commands commands = createNewCommandWithStringAndJson(string, json);
		List<T> response = commands.listAll();
		return response;
	}
	
	private String get(String table, String json) throws SQLException {
		String string = "method=get&table=" + table;
		Commands commands = createNewCommandWithStringAndJson(string, json);
		String response = commands.get();
		return response;
	}
	
	private void update(String table, String json) throws SQLException {
		String string = "method=update&table=" + table;
		Commands commands = createNewCommandWithStringAndJson(string, json);
		commands.update();
	}
	
	private void delete(String table, String json) throws SQLException {
		String string = "method=delete&table=" + table;
		Commands commands = createNewCommandWithStringAndJson(string, json);
		commands.delete();
	}
	
	private Commands createNewCommandWithStringAndJson(String queryString, String json) {
		HttpQuery query = new HttpQuery(queryString);
		Commands commands = new Commands(query, json);
		return commands;
	}
	
	private void reset() {
		Commands commands = new Commands();
		commands.resetDb();
	}
	

	
}
