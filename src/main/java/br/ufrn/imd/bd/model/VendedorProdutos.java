package br.ufrn.imd.bd.model;

public class VendedorProdutos {
	private Long vendedorId;
	private String productName;
	private String description;

	public VendedorProdutos() {
	}

	public VendedorProdutos(Long vendedorId, String productName, String description) {
		this.vendedorId = vendedorId;
		this.productName = productName;
		this.description = description;
	}

	public Long getVendedorId() {
		return vendedorId;
	}

	public void setVendedorId(Long vendedorId) {
		this.vendedorId = vendedorId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
