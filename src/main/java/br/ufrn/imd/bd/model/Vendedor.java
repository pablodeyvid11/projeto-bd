package br.ufrn.imd.bd.model;

public class Vendedor extends User {
	private Long id;
	private String description;

	public Vendedor() {
	}

	public Vendedor(Long id, String cpf, String name, String email, String phone, String password,
			String description) {
		super(id, cpf, name, email, phone, password);
		this.id = id; 
		this.description = description;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}