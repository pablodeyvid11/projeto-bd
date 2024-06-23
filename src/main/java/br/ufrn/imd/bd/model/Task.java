package br.ufrn.imd.bd.model;

import java.time.LocalDateTime;

public class Task {
	private Long id;
	private Double points;
	private String name;
	private String description;
	private LocalDateTime initialDeadline;
	private LocalDateTime finalDeadline;
	private Long eventId;

	public Task() {
	}

	public Task(Long id, Double points, String name, String description, LocalDateTime initialDeadline,
			LocalDateTime finalDeadline, Long eventId) {
		this.id = id;
		this.points = points;
		this.name = name;
		this.description = description;
		this.initialDeadline = initialDeadline;
		this.finalDeadline = finalDeadline;
		this.eventId = eventId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getInitialDeadline() {
		return initialDeadline;
	}

	public void setInitialDeadline(LocalDateTime initialDeadline) {
		this.initialDeadline = initialDeadline;
	}

	public LocalDateTime getFinalDeadline() {
		return finalDeadline;
	}

	public void setFinalDeadline(LocalDateTime finalDeadline) {
		this.finalDeadline = finalDeadline;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

}
