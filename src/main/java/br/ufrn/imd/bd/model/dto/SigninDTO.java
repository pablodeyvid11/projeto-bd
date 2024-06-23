package br.ufrn.imd.bd.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SigninDTO(
		@NotNull
		@NotBlank(message = "O email não pode estar em branco")
		@Size(min = 5, max = 50, message = "O email deve ter entre 5 e 50 caracteres")
		String email,
		@NotNull
		String password) {
}