package no.kristiania.pgr200.core.database;

import java.util.UUID;

public class Track {

	private UUID id;
	private String name;
	private UUID conferenceId;
	
	public Track(String name, UUID confId) {
		this.id = UUID.randomUUID();
		this.conferenceId = confId;
		this.name = name;
	}
	
	public Track(UUID id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Track(String name) {
		this.id = UUID.randomUUID();
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(UUID conferenceId) {
		this.conferenceId = conferenceId;
	}

	@Override
	public String toString() {
		return "Track [id=" + id + ", name=" + name + "]";
	}
	
	
}
