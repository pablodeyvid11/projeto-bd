package br.ufrn.imd.bd.model;

import java.time.LocalDateTime;

public class Evento {
	private Long id;
	private String title;
	private Double ingressoPrice;
	private String description;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private Integer qtyIngressos;

	public Evento() {
	}

	public Evento(Long id, String title, Double ingressoPrice, String description, LocalDateTime startDateTime,
			LocalDateTime endDateTime, Integer qtyIngressos) {
		this.id = id;
		this.title = title;
		this.ingressoPrice = ingressoPrice;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.qtyIngressos = qtyIngressos;
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

	public Double getIngressoPrice() {
		return ingressoPrice;
	}

	public void setIngressoPrice(Double ingressoPrice) {
		this.ingressoPrice = ingressoPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Integer getQtyIngressos() {
		return qtyIngressos;
	}

	public void setQtyIngressos(Integer qtyIngressos) {
		this.qtyIngressos = qtyIngressos;
	}

}
