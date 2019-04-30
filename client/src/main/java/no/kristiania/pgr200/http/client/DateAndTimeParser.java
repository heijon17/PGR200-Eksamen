package no.kristiania.pgr200.http.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateAndTimeParser {

	public String formatDateString(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate.toString();
	}

	public String formatTimeString(String time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
		LocalTime localTime = LocalTime.parse(time, formatter);
		return localTime.toString();
	}

}
