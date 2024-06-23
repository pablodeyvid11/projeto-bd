package br.ufrn.imd.bd.model;

public class FesteiroAvaliaEvento {
	private Long eventoId;
	private Long festeiroId;
	private Double rating;
	private String comment;

	public FesteiroAvaliaEvento() {
	}

	public FesteiroAvaliaEvento(Long eventoId, Long festeiroId, Double rating, String comment) {
		this.eventoId = eventoId;
		this.festeiroId = festeiroId;
		this.rating = rating;
		this.comment = comment;
	}

	public Long getEventoId() {
		return eventoId;
	}

	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}

	public Long getFesteiroId() {
		return festeiroId;
	}

	public void setFesteiroId(Long festeiroId) {
		this.festeiroId = festeiroId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
