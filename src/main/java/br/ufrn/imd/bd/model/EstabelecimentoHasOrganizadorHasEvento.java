package br.ufrn.imd.bd.model;

public class EstabelecimentoHasOrganizadorHasEvento {
	private String estabelecimentoId;
	private Long organizadorId;
	private Long eventoId;

	public EstabelecimentoHasOrganizadorHasEvento() {

	}

	public EstabelecimentoHasOrganizadorHasEvento(String estabelecimentoId, Long organizadorId, Long eventoId) {
		this.estabelecimentoId = estabelecimentoId;
		this.organizadorId = organizadorId;
		this.eventoId = eventoId;
	}

	public String getEstabelecimentoId() {
		return estabelecimentoId;
	}

	public void setEstabelecimentoId(String estabelecimentoId) {
		this.estabelecimentoId = estabelecimentoId;
	}

	public Long getOrganizadorId() {
		return organizadorId;
	}

	public void setOrganizadorId(Long organizadorId) {
		this.organizadorId = organizadorId;
	}

	public Long getEventoId() {
		return eventoId;
	}

	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}

}
