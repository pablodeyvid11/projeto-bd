package br.ufrn.imd.bd.model;

public class Ingresso {
	private String token;
	private Long eventoId;
	private Long festeiroId;

	public Ingresso() {
	}

	public Ingresso(String token, Long eventoId, Long festeiroId) {
		this.token = token;
		this.eventoId = eventoId;
		this.festeiroId = festeiroId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

}
