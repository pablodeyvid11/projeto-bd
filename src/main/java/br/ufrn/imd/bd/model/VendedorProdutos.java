package br.ufrn.imd.bd.model;

public class VendedorProdutos {
	private Long vendedorId;
	private String productName;

	public VendedorProdutos() {
	}

	public VendedorProdutos(Long vendedorId, String productName) {
		this.vendedorId = vendedorId;
		this.productName = productName;
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

}
