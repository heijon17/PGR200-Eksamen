package no.kristiania.pgr200.core.database;

import java.util.UUID;

public class Talk{

	private UUID id, timeslotId;
	private String title, description, topic;

	public Talk(String title, String description, String topic) {
		this.id = UUID.randomUUID();
		this.title = title;
		this.description = description;
		this.topic = topic;
	}

	public Talk(UUID id, String title, String description, String topic) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.topic = topic;
	}
	
	public Talk(UUID id, String title, String description, String topic, UUID timeslotId) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.topic = topic;
		this.timeslotId = timeslotId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	

	public UUID getTimeslotId() {
		return timeslotId;
	}

	public void setTimeslotId(UUID timeslotId) {
		this.timeslotId = timeslotId;
	}

	@Override
	public String toString() {
		return "Talk [id=" + id + ", title=" + title + ", description=" + description + ", topic=" + topic + "]";
	}
}
