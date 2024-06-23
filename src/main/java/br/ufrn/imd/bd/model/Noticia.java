package br.ufrn.imd.bd.model;

import java.time.LocalDateTime;

public class Noticia {
	private Long id;
	private String title;
	private LocalDateTime date;
	private String text;
	private String midiaPath;
	private Long eventoId;

	public Noticia() {
	}

	public Noticia(Long id, String title, LocalDateTime date, String text, String midiaPath, Long eventoId) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.text = text;
		this.midiaPath = midiaPath;
		this.eventoId = eventoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMidiaPath() {
		return midiaPath;
	}

	public void setMidiaPath(String midiaPath) {
		this.midiaPath = midiaPath;
	}

	public Long getEventoId() {
		return eventoId;
	}

	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}

}
