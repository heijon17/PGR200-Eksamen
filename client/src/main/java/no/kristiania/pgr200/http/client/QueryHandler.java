package no.kristiania.pgr200.http.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.google.gson.Gson;

import no.kristiania.pgr200.core.database.Conference;
import no.kristiania.pgr200.core.database.Date;
import no.kristiania.pgr200.core.database.Talk;
import no.kristiania.pgr200.core.database.Timeslot;
import no.kristiania.pgr200.core.database.Track;
import no.kristiania.pgr200.core.http.HttpQuery;

/**
 * this class will convert query-parameters to Java-objects
 */

public class QueryHandler {

	private Gson gson = new Gson();

	public Object getItem(HttpQuery query) {
		switch (query.getParameter("table")) {
		case "talk":
			return new Talk(
					query.getParameter("id") != null ? UUID.fromString(query.getParameter("id")) : UUID.randomUUID(),
					query.getParameter("title") != null ? query.getParameter("title") : null,
					query.getParameter("description") != null ? query.getParameter("description") : null,
					query.getParameter("topic") != null ? query.getParameter("topic") : null);
		case "conference":
			return new Conference(query.getParameter("name") != null ? query.getParameter("name") : null);
		case "date":
			String[] dateString = query.getParameter("date") != null ? query.getParameter("date").split("-") : null;
			return new Date(
					query.getParameter("id") != null ? (UUID) UUID.fromString(query.getParameter("id"))
							: UUID.randomUUID(),
					dateString != null
							? LocalDate.of(stringToInt(dateString[0]), stringToInt(dateString[1]),
									stringToInt(dateString[2]))
							: null,
					query.getParameter("conference_id") != null ? UUID.fromString(query.getParameter("conference_id"))
							: null);

		case "timeslot":
			String[] timeFromString = query.getParameter("timefrom") != null ? query.getParameter("timefrom").split(":")
					: null;
			String[] timeToString = query.getParameter("timeto") != null ? query.getParameter("timeto").split(":")
					: null;

			return new Timeslot(
					query.getParameter("id") != null ? UUID.fromString(query.getParameter("id")) : UUID.randomUUID(),
					query.getParameter("timefrom") != null
							? LocalTime.of(stringToInt(timeFromString[0]), stringToInt(timeFromString[1]))
							: null,
					query.getParameter("timefrom") != null
							? LocalTime.of(stringToInt(timeToString[0]), stringToInt(timeToString[1]))
							: null);
		case "track":
			return new Track(query.getParameter("name") != null ? query.getParameter("name") : null,
					query.getParameter("conference_Id") != null ? UUID.fromString(query.getParameter("conference_Id"))
							: null);
		default:
			return null;
		}
	}

	public String getJsonFromItem(Object item) {
		return gson.toJson(item);
	}

	public <T> Object getItemFromJson(String json, Class<T> type) {
		return gson.fromJson(json, type);
	}

	public int stringToInt(Object dateString) {
		return Integer.parseInt((String) dateString);
	}

}
