package no.kristiania.pgr200.http.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import no.kristiania.pgr200.core.database.Talk;
import no.kristiania.pgr200.core.http.HttpRequest;
import no.kristiania.pgr200.core.http.HttpResponse;
import no.kristiania.pgr200.core.http.HttpServer;


public class QueryHandlerTest {

	@BeforeClass
	public static void StartServer() throws IOException, SQLException {
		HttpServer server = new HttpServer(10083);
		server.start();
		addDemoData();
	}

	private static void addDemoData() throws SQLException {
		String[] args = { "reset" };
		CommandLineParser parser = new CommandLineParser(args);
		HttpRequest request = new HttpRequest("localhost", 10083, parser.getParsedPath(), parser.getMethod());
		HttpResponse response = request.execute();
	}

	@Test
	public void shouldConvertItemToJsonAndBack() {
		String[] talkArgs = { "insert", "talk", "-title", "TalkTitle", "-description", "TalkDesc", "-topic",
				"TalkTopic" };
		CommandLineParser talkParser = new CommandLineParser(talkArgs);
		QueryHandler handler = new QueryHandler();

		Talk talk = (Talk) handler.getItem(talkParser.getQuery());

		String json = handler.getJsonFromItem(talk);

		Object newTalk = handler.getItemFromJson(json, Talk.class);

		assertThat(json).contains(talk.getId().toString());
	}

	@Test
	public void shouldSendRequestAndConvertItemToJsonAndBack() {
		String[] talkArgs = { "insert", "talk", "-title", "TalkTitle", "-description", "TalkDesc", "-topic",
				"TalkTopic" };
		CommandLineParser talkParser = new CommandLineParser(talkArgs);
		QueryHandler handler = new QueryHandler();
		Object talk = handler.getItem(talkParser.getQuery());

		HttpRequest request = new HttpRequest("localhost", 10083, "/api?method=insert&table=talk",
				talkParser.getMethod());

		request.setBody(handler.getJsonFromItem(talk));
		HttpResponse response = request.execute();
		String returnBody = response.getBody();

		String json = handler.getJsonFromItem(talk);

		Object newTalk = handler.getItemFromJson(json, Talk.class);

		assertThat(talk).isEqualToComparingFieldByField(newTalk);

	}
}
