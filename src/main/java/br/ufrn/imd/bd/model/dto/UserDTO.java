package br.ufrn.imd.bd.model.dto;

public record UserDTO(Long id, String cpf, String name, String email, String phone, String password, String type,
		String vendedorDescription, String organizadorCargo) {
}
