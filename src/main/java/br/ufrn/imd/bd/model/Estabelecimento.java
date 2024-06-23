package br.ufrn.imd.bd.model;

public class Estabelecimento {
	private Long id;
	private String cnpj;
	private String name;
	private String addressBairro;
	private String addressRua;
	private String addressCep;
	private String addressNumero;

	public Estabelecimento() {
	}

	public Estabelecimento(Long id, String cnpj, String name, String addressBairro, String addressRua,
			String addressCep, String addressNumero) {
		this.id = id;
		this.cnpj = cnpj;
		this.name = name;
		this.addressBairro = addressBairro;
		this.addressRua = addressRua;
		this.addressCep = addressCep;
		this.addressNumero = addressNumero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddressBairro() {
		return addressBairro;
	}

	public void setAddressBairro(String addressBairro) {
		this.addressBairro = addressBairro;
	}

	public String getAddressRua() {
		return addressRua;
	}

	public void setAddressRua(String addressRua) {
		this.addressRua = addressRua;
	}

	public String getAddressCep() {
		return addressCep;
	}

	public void setAddressCep(String addressCep) {
		this.addressCep = addressCep;
	}

	public String getAddressNumero() {
		return addressNumero;
	}

	public void setAddressNumero(String addressNumero) {
		this.addressNumero = addressNumero;
	}

}