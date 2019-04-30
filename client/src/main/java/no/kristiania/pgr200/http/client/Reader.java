package no.kristiania.pgr200.http.client;

import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
	private Scanner reader;

	public Reader() {
		reader = new Scanner(System.in);
	}

	public ArrayList<String> readInput() {
		System.out.print("> ");
		String inputLine = reader.nextLine().trim().toLowerCase();
		return translateLineToArrayList(inputLine);
	}

//    This method parses a line of text and 
//    returns an ArrayList<String> with the appropriate values
//    Input: "insert talk -title The title -description Notice how it understands when to cut -topic Perfectly cut ArrayList"
//    Output: {"insert", "talk", "-title", "The title", "-description", "Notice how it understands when to cut", "-topic", "perfectly cut ArrayList"}
	ArrayList<String> translateLineToArrayList(String inputLine) {
		String currentString = "";
		ArrayList<String> commands = new ArrayList<String>();
		int counter = 0;
		boolean inProtectedString = false;
		boolean skippedFirstSpaceinProtectedString = false;

		while (counter < inputLine.length()) {
			if (inputLine.charAt(counter) == '-' && inProtectedString == false) {
				inProtectedString = true;
				currentString += inputLine.charAt(counter);
				counter++;

			} else if (inputLine.charAt(counter) == ' ' && inProtectedString == true
					&& skippedFirstSpaceinProtectedString == true) {
				if (counter < inputLine.length()) {
					if (inputLine.charAt(counter + 1) == '-') {
						counter++;
					} else {
						currentString += inputLine.charAt(counter);
						counter++;
					}
				}

			} else if (inProtectedString == true && inputLine.charAt(counter) == ' '
					&& skippedFirstSpaceinProtectedString == false) {
				commands.add(currentString);
				currentString = "";
				skippedFirstSpaceinProtectedString = true;
				counter++;

			} else if (inputLine.charAt(counter) != '-' && inProtectedString == true) {
				currentString += inputLine.charAt(counter);
				counter++;

			} else if (inputLine.charAt(counter) == '-' && inProtectedString == true) {
				skippedFirstSpaceinProtectedString = false;
				commands.add(currentString);
				currentString = "";
				currentString += inputLine.charAt(counter);
				counter++;

			} else if (inputLine.charAt(counter) == ' ' && inProtectedString == false) {
				commands.add(currentString);
				currentString = "";
				counter++;

			} else {
				currentString += inputLine.charAt(counter);
				counter++;
			}
			if (counter == inputLine.length()) {
				commands.add(currentString);

			}
		}
		return commands;
	}
}
