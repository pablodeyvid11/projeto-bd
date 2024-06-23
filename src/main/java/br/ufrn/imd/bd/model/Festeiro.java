package br.ufrn.imd.bd.model;

public class Festeiro extends User {
	private Long id;

	public Festeiro() {
	}

	public Festeiro(Long id, String cpf, String name, String email, String phone, String password) {
		super(id, cpf, name, email, phone, password);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}