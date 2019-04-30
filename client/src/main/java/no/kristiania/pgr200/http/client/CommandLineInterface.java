package no.kristiania.pgr200.http.client;

import java.util.ArrayList;

import no.kristiania.pgr200.core.http.HttpRequest;
import no.kristiania.pgr200.core.http.HttpResponse;

public class CommandLineInterface {

	private Reader reader;

	public CommandLineInterface() {
		reader = new Reader();
	}

	public void start() {
		boolean done = false;

		System.out.println(welcomeMessage());

		while (!done) {
			ArrayList<String> input = reader.readInput();

			done = checkIfDoneOrHelp(done, input);
		}
		System.out.println(goodbyeMessage());
	}

	boolean checkIfDoneOrHelp(boolean done, ArrayList<String> input) {
		if (input.size() == 1 && input.contains("done")) {
			done = true;
		} else if (input.size() == 1 && input.contains("help")) {
			System.out.println(helpMessage());
		} else if (input.size() == 1 && input.contains("reset")) {
			System.out.println(parseThenExecute(input));
		} else {
			System.out.println(parseThenExecute(input));
		}
		return done;
	}

	String parseThenExecute(ArrayList<String> input) {
		HttpResponse response;
		CommandLineParser parser = new CommandLineParser(input.toArray(new String[0]));
		String parsedQuery = parser.getParsedPath();

		HttpRequest request = new HttpRequest("localhost", 10080, parsedQuery, parser.getMethod());
		QueryHandler queryHandler = new QueryHandler();

		if(parsedQuery.equals("/api?method=reset")){
			request.setBody("reset");
			return "Database reset";
		} 
		
		
		Object item = queryHandler.getItem(parser.getQuery());
		String body = queryHandler.getJsonFromItem(item);
		request.setBody(body);
		response = request.execute();

		

		if (parser.getMethod().equals("GET")) {
			return response.getBody();
		} else {
			return "Database updated!";
		}
	}

	String welcomeMessage() {
		String msg = "So you are going to have a conference?\n" + "\n"
				+ "Great! Here's how to add content to our system.\n"
				+ "If you'd like to add say a conference you'd write 'insert conference -name 'JavaZone 2019'\n"
				+ "If you're curious what else you can do, just write 'help' and you'll see all valid commands\n"
				+ "when you're done, just write 'done'\n";
		return msg;
	}

	String goodbyeMessage() {
		return "Until next time, take care!\n";
	}

	String helpMessage() {
		String msg = "Here's the syntax you need to use:\n"
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
				+ "\n" + "\n" + "You can do `listall talk` which returns all talks\n";
		return msg;
	}
}
