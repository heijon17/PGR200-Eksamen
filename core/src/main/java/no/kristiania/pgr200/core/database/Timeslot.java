package no.kristiania.pgr200.core.database;

import java.time.LocalTime;
import java.util.UUID;

public class Timeslot {

	private UUID id;
	private LocalTime timeFrom;
	private LocalTime timeTo;
	private UUID daysId, tracksId;
	
	
	public Timeslot(UUID id, LocalTime timeFrom, LocalTime timeTo) {
		this.id = id;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
	}
	
	public Timeslot (LocalTime timeFrom, LocalTime timeTo) {
		this.id = UUID.randomUUID();
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalTime getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(LocalTime timeFrom) {
		this.timeFrom = timeFrom;
	}

	public LocalTime getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(LocalTime timeTo) {
		this.timeTo = timeTo;
	}

	public UUID getDaysId() {
		return daysId;
	}

	public void setDaysId(UUID daysId) {
		this.daysId = daysId;
	}

	public UUID getTracksId() {
		return tracksId;
	}

	public void setTracksId(UUID tracksId) {
		this.tracksId = tracksId;
	}

	@Override
	public String toString() {
		return "Timeslot [id=" + id + ", timeFrom=" + timeFrom + ", timeTo=" + timeTo + "]";
	}
	
	
}
