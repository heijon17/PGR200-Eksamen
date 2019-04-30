package no.kristiania.pgr200.http.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.BeforeClass;
import org.junit.Test;

import no.kristiania.pgr200.core.database.Conference;
import no.kristiania.pgr200.core.database.Date;
import no.kristiania.pgr200.core.database.Talk;
import no.kristiania.pgr200.core.database.Timeslot;
import no.kristiania.pgr200.core.http.HttpRequest;
import no.kristiania.pgr200.core.http.HttpResponse;
import no.kristiania.pgr200.core.http.HttpServer;

public class ClientRequestTest {

	private static Talk talk, talk2, talk3;
	private static Date date;
	private static Conference conf;
	private static Timeslot timeslot;
	private static QueryHandler queryHandler = new QueryHandler();

	@BeforeClass
	public static void StartServer() throws IOException, SQLException {
		HttpServer server = new HttpServer(10081);
		server.start();
		addDemoData();
	}

	private static void addDemoData() throws SQLException {
		String[] args = { "reset" };
		CommandLineParser parser = new CommandLineParser(args);
		HttpRequest request = new HttpRequest("localhost", 10081, parser.getParsedPath(), parser.getMethod());
		HttpResponse response = request.execute();

		String[] confArgs = { "insert", "conference", "-name", "TechConf" };
		CommandLineParser confParser = new CommandLineParser(confArgs);
		HttpRequest confRequest = new HttpRequest("localhost", 10081, confParser.getParsedPath(),
				confParser.getMethod());
		conf = (Conference) queryHandler.getItem(confParser.getQuery());
		String confBody = queryHandler.getJsonFromItem(conf);
		confRequest.setBody(confBody);
		HttpResponse confResponse = confRequest.execute();

		talk = new Talk("TalkTitle", "TalkDesc", "TalkTopic");
		talk2 = new Talk("my talk", "desc", "topic");
		talk3 = new Talk("my talk", "desc", "topic");

		String[] talkArgs = { "insert", "talk", "-id", talk.getId().toString(), "-title", "TalkTitle", "-description",
				"TalkDesc", "-topic", "TalkTopic" };
		CommandLineParser talkParser = new CommandLineParser(talkArgs);
		HttpRequest talkRequest = new HttpRequest("localhost", 10081, talkParser.getParsedPath(),
				talkParser.getMethod());
		Object item = queryHandler.getItem(talkParser.getQuery());
		String body = queryHandler.getJsonFromItem(item);
		talkRequest.setBody(body);
		HttpResponse talkResponse = talkRequest.execute();

		date = new Date(LocalDate.of(2018, 12, 28));

		timeslot = new Timeslot(LocalTime.of(13, 00), LocalTime.of(18, 00));

	}

	@Test
	public void shouldMakeGetRequest() {
		String[] args = { "get", "talk", "-id", talk.getId().toString() };
		CommandLineParser parser = new CommandLineParser(args);
		HttpRequest request = new HttpRequest("localhost", 10081, parser.getParsedPath(), parser.getMethod());
		Object item = queryHandler.getItem(parser.getQuery());
		String body = queryHandler.getJsonFromItem(item);
		request.setBody(body);

		HttpResponse response = request.execute();

		Object returnedObject = queryHandler.getItemFromJson(response.getBody(), Talk.class);

		assertThat(returnedObject).isEqualToComparingFieldByField(talk);
	}

	@Test
	public void shouldInsertAndReadTalk() {
		String[] args = { "insert", "talk", "-id", talk3.getId().toString(), "-title", "my talk", "-description",
				"desc", "-topic", "topic" };
		CommandLineParser parser = new CommandLineParser(args);
		HttpRequest request = new HttpRequest("localhost", 10081, parser.getParsedPath(), parser.getMethod());
		Object item = queryHandler.getItem(parser.getQuery());
		String body = queryHandler.getJsonFromItem(item);
		request.setBody(body);

		HttpResponse response = request.execute();

		assertThat(response.getBody()).isEqualTo(queryHandler.getJsonFromItem(item));

		String[] getArgs = { "get", "talk", "-id", talk3.getId().toString() };

		CommandLineParser parser2 = new CommandLineParser(getArgs);
		HttpRequest request2 = new HttpRequest("localhost", 10081, parser2.getParsedPath(), parser2.getMethod());
		Object item2 = queryHandler.getItem(parser2.getQuery());
		String body2 = queryHandler.getJsonFromItem(item2);
		request2.setBody(body2);

		HttpResponse response2 = request2.execute();

		Talk returnedTalk = (Talk) queryHandler.getItemFromJson(response2.getBody(), Talk.class);

		assertThat(returnedTalk).isEqualToComparingFieldByField(talk3);
	}

	@Test
	public void shouldInsertAndReadDate() {

		String[] args = { "insert", "date", "-date", "28/12/2018", "-conference_id", conf.getId().toString() };
		CommandLineParser parser = new CommandLineParser(args);
		HttpRequest request = new HttpRequest("localhost", 10081, parser.getParsedPath(), parser.getMethod());
		Object item = queryHandler.getItem(parser.getQuery());
		String body = queryHandler.getJsonFromItem(item);
		request.setBody(body);

		HttpResponse response = request.execute();

		assertThat(response.getBody()).isEqualTo(queryHandler.getJsonFromItem(item));

		String[] getArgs = { "get", "date", "-id", ((Date) item).getId().toString() };

		CommandLineParser parser2 = new CommandLineParser(getArgs);
		HttpRequest request2 = new HttpRequest("localhost", 10081, parser2.getParsedPath(), parser2.getMethod());
		Object item2 = queryHandler.getItem(parser2.getQuery());
		String body2 = queryHandler.getJsonFromItem(item2);
		request2.setBody(body2);
		HttpResponse response2 = request2.execute();

		Date returnedDate = (Date) queryHandler.getItemFromJson(response.getBody(), Date.class);

		assertThat(item).isEqualToComparingFieldByField(returnedDate);
	}

	@Test
	public void shouldInsertAndReadTime() {
		String[] args = { "insert", "timeslot", "-timeFrom", "13:00", "-timeTo", "18:00" };
		CommandLineParser parser = new CommandLineParser(args);
		HttpRequest request = new HttpRequest("localhost", 10081, parser.getParsedPath(), parser.getMethod());
		Object item = queryHandler.getItem(parser.getQuery());
		String body = queryHandler.getJsonFromItem(item);
		request.setBody(body);
		HttpResponse response = request.execute();

		assertThat(response.getBody()).isEqualTo(queryHandler.getJsonFromItem(item));

		String[] getArgs = { "get", "timeslot", "-id", ((Timeslot) item).getId().toString() };

		CommandLineParser parser2 = new CommandLineParser(getArgs);
		HttpRequest request2 = new HttpRequest("localhost", 10081, parser2.getParsedPath(), parser2.getMethod());
		Object item2 = queryHandler.getItem(parser2.getQuery());
		String body2 = queryHandler.getJsonFromItem(item2);
		request2.setBody(body2);
		HttpResponse response2 = request2.execute();
		Timeslot returnedTimeslot = (Timeslot) queryHandler.getItemFromJson(response2.getBody(), Timeslot.class);

		assertThat(item).isEqualToComparingFieldByField(returnedTimeslot);
	}

}
