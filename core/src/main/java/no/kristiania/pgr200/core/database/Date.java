package no.kristiania.pgr200.core.database;

import java.time.LocalDate;
import java.util.UUID;

public class Date {

	private UUID id;
	private LocalDate date;
	private UUID conferenceId;
	
	public Date (UUID id, LocalDate date, UUID confId) {
		this.id = id;
		this.date = date;
		this.conferenceId = confId;
	}
	
	public Date (LocalDate date, UUID confId) {
		this.id = UUID.randomUUID();
		this.date = date;
		this.conferenceId = confId;
	}
	
	public Date (UUID id, LocalDate date) {
		this.id = id;
		this.date = date;
	}
	
	public Date (LocalDate date) {
		this.id = UUID.randomUUID();
		this.date = date;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public UUID getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(UUID conferenceId) {
		this.conferenceId = conferenceId;
	}

	@Override
	public String toString() {
		return "Date [id=" + id + ", date=" + date + "]";
	}
	
	
}
