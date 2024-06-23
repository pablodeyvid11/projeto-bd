package br.ufrn.imd.bd.model;

public class VendedorHasEvento {
	private Long eventoId;
	private Long vendedorId;
	private Boolean isApproved;

	public VendedorHasEvento() {
	}

	public VendedorHasEvento(Long eventoId, Long vendedorId, Boolean isApproved) {
		this.eventoId = eventoId;
		this.vendedorId = vendedorId;
		this.isApproved = isApproved;
	}

	public Long getEventoId() {
		return eventoId;
	}

	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}

	public Long getVendedorId() {
		return vendedorId;
	}

	public void setVendedorId(Long vendedorId) {
		this.vendedorId = vendedorId;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

}
