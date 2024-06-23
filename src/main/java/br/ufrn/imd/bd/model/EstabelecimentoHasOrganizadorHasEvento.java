package br.ufrn.imd.bd.model;

public class EstabelecimentoHasOrganizadorHasEvento {
	private Long estabelecimentoId;
	private Long organizadorId;
	private Long eventoId;

	public EstabelecimentoHasOrganizadorHasEvento() {

	}

	public EstabelecimentoHasOrganizadorHasEvento(Long estabelecimentoId, Long organizadorId, Long eventoId) {
		this.estabelecimentoId = estabelecimentoId;
		this.organizadorId = organizadorId;
		this.eventoId = eventoId;
	}

	public Long getEstabelecimentoId() {
		return estabelecimentoId;
	}

	public void setEstabelecimentoId(Long estabelecimentoId) {
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
