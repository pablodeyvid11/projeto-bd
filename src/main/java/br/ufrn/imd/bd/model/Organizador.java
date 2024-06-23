package br.ufrn.imd.bd.model;

public class Organizador extends User {
	private Long id;
	private String cargo;

	public Organizador() {
	}

	public Organizador(Long id, String cpf, String name, String email, String phone, String password, String cargo) {
		super(id, cpf, name, email, phone, password);
		this.id = id;
		this.cargo = cargo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

}
