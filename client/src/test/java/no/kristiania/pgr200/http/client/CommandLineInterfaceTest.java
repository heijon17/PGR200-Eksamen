package no.kristiania.pgr200.http.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import no.kristiania.pgr200.core.database.Conference;
import no.kristiania.pgr200.core.database.Date;
import no.kristiania.pgr200.core.database.Talk;
import no.kristiania.pgr200.core.database.Timeslot;
import no.kristiania.pgr200.core.http.HttpRequest;
import no.kristiania.pgr200.core.http.HttpResponse;
import no.kristiania.pgr200.core.http.HttpServer;


public class CommandLineInterfaceTest {
	
	private static Talk talk, talk2, talk3;
	private static Date date;
	private static Conference conf;
	private static Timeslot timeslot;
	
	
	@BeforeClass
	public static void StartServer() throws IOException, SQLException {
		HttpServer server = new HttpServer(10080);
		server.start();
		addDemoData();
	}

	private static void addDemoData() throws SQLException {
		String[] args = {"reset"};
		CommandLineParser parser = new CommandLineParser(args);
		HttpRequest request = new HttpRequest("localhost", 10080, parser.getParsedPath(), parser.getMethod());
		HttpResponse response = request.execute();
		
		conf = new Conference("TechoConf");
		String[] confArgs = {"insert", "conference", "-id", conf.getId().toString(), "-name", conf.getName() };
		CommandLineInterface cli1 = new CommandLineInterface();
		ArrayList<String> argArrayList1 = new ArrayList<>(Arrays.asList(confArgs));
		
		talk = new Talk("TalkTitle", "TalkDesc", "TalkTopic");
		talk2 = new Talk("The title", null, null);
		talk3 = new Talk("my talk", "desc", "topic");
		
		String[] talkArgs = { "insert", "talk", "-id", talk.getId().toString(), "-title", "TalkTitle", "-description", "TalkDesc", "-topic", "TalkTopic" };
		CommandLineInterface cli2 = new CommandLineInterface();
		ArrayList<String> argArrayList2 = new ArrayList<>(Arrays.asList(talkArgs));
		
	}
	
	
	@Test
	public void shouldCheckDone() {
		CommandLineInterface cli = new CommandLineInterface();
		String[] args = {"done"};
		ArrayList<String> argArrayList = new ArrayList<>(Arrays.asList(args));
		
		Boolean expected = true;
		Boolean response = cli.checkIfDoneOrHelp(false, argArrayList);
		
		assertThat(response)
			.isEqualTo(expected);
	}
	
	
	@Test
	public void shouldCheckHelp() {
		CommandLineInterface cli = new CommandLineInterface();
		String[] args = {"help"};
		ArrayList<String> argArrayList = new ArrayList<>(Arrays.asList(args));
		
		Boolean expected = false;
		Boolean response = cli.checkIfDoneOrHelp(false, argArrayList);
		
		assertThat(response)
			.isEqualTo(expected);
	}
	
	
	
	@Test
	public void shouldParseAndExecuteGet() {
		String[] args1 = {"insert", "talk", "-id", talk2.getId().toString(), "-title", "The title"};
		CommandLineInterface cli1 = new CommandLineInterface();
		ArrayList<String> argArrayList1 = new ArrayList<>(Arrays.asList(args1));
		String response1 = cli1.parseThenExecute(argArrayList1);
		
		String[] args2 = {"listall", "talk"};
		CommandLineInterface cli2 = new CommandLineInterface();
		ArrayList<String> argArrayList2 = new ArrayList<>(Arrays.asList(args2));
		String response2 = cli2.parseThenExecute(argArrayList2);
		
		String expected = "[" + talk2.toString() + "]";
		
		assertThat(response2)
			.isEqualTo(expected);
	}
	
	
	@Test
	public void shouldReadWelcomeMessage() {
		CommandLineInterface cli = new CommandLineInterface();
		
		String expected = "So you are going to have a conference?\n" +
						 "\n" +
						 "Great! Here's how to add content to our system.\n" +
						 "If you'd like to add say a conference you'd write 'insert conference -name 'JavaZone 2019'\n" +
						 "If you're curious what else you can do, just write 'help' and you'll see all valid commands\n" +
						 "when you're done, just write 'done'\n";
		
		assertThat(cli.welcomeMessage())
			.isEqualTo(expected);
	}
	
	@Test
	public void shouldReadGoodbyeMessage() {
		CommandLineInterface cli = new CommandLineInterface();
		
		String expected = "Until next time, take care!\n";
		
		assertThat(cli.goodbyeMessage())
			.isEqualTo(expected);
	}
	
	@Test
	public void shouldReadHelpMessage() {
		CommandLineInterface cli = new CommandLineInterface();
		
		String expected = "Here's the syntax you need to use:\n"
				+ "command table -firstColumn first value -secondColumn second value\n" + "\n"
				+ "These are the commands you can use:\n" + "insert, get, delete, list, listall & update\n" + "\n"
				+ "These are the available tables with their optional columns\n" + "conference: -name\n"
				+ "talk: -title, -description, -topic, -timeslot_id \n"
				+ "timeslot: -timeFrom, -timeTo, -days_id, -tracks_id \n" + "date: -date, -conference_id \n"
				+ "track: -name, -conference_id \n" + "\n" + "An example of this put together would look like this:\n"
				+ "insert talk -title The title -description The description comes here -topic The topic we're talking about\n"
				+ "\n"
				+ "If you are using get, delete or update, you need to provide an ID, find the ID by using list like so: \n"
				+ "get talk -id ID_number\n" + "delete talk -id ID_number\n"
				+ "update talk -id ID_number -title New title -description New descipion -topic New topic\n" + "\n"
				+ "If you want to reset you database type 'reset'. WARNING everything will be deleted!"
				+ "\n"
				+ "\n"
				+ "You can do `listall talk` which returns all talks\n";
		
		assertThat(cli.helpMessage())
			.isEqualTo(expected);
	}

}





