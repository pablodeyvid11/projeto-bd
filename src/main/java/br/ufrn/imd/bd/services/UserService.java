package br.ufrn.imd.bd.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.bd.model.User;
import br.ufrn.imd.bd.model.UserDetailsImpl;
import br.ufrn.imd.bd.model.dto.RecoveryJWTDTO;
import br.ufrn.imd.bd.model.dto.SigninDTO;
import br.ufrn.imd.bd.model.dto.UserDTO;
import br.ufrn.imd.bd.repository.interfaces.FesteiroRepository;
import br.ufrn.imd.bd.repository.interfaces.OrganizadorRepository;
import br.ufrn.imd.bd.repository.interfaces.UserRepository;
import br.ufrn.imd.bd.repository.interfaces.VendedorRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrganizadorRepository organizadorRepository;

	@Autowired
	private VendedorRepository vendedorRepository;

	@Autowired
	private FesteiroRepository festeiroRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenService jwtTokenService;

	@Transactional(rollbackFor = SQLException.class)
	public User findByEmail(String email) throws SQLException {
		return userRepository.findByEmail(email);
	}

	@Transactional(rollbackFor = SQLException.class)
	public User signon(UserDTO sigonRecord) throws SQLException {
		User user = new User();
		Long nextId = userRepository.getNextId();

		user.setId(nextId);
		user.setCpf(sigonRecord.cpf());
		user.setEmail(sigonRecord.email());
		user.setPhone(sigonRecord.phone());
		user.setName(sigonRecord.name());
		user.setPassword(passwordEncoder.encode(sigonRecord.password()));
		userRepository.save(user);

		String type = sigonRecord.type();
		if ("festeiro".equalsIgnoreCase(type)) {
			festeiroRepository.save(nextId);
		} else if ("organizador".equalsIgnoreCase(type)) {
			organizadorRepository.save(nextId, sigonRecord.organizadorCargo());
		} else if ("vendedor".equalsIgnoreCase(type)) {
			vendedorRepository.save(nextId, sigonRecord.vendedorDescription());
		}

		return user;
	}

	@Transactional(rollbackFor = SQLException.class)
	public RecoveryJWTDTO login(SigninDTO siginRecord) throws SQLException {

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				siginRecord.email(), siginRecord.password());

		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return new RecoveryJWTDTO(jwtTokenService.generateToken(userDetails));
	}

	@Transactional(rollbackFor = SQLException.class)
	public void update(UserDTO sigonRecord) throws SQLException {
		User user = userRepository.findById(sigonRecord.id());
		if (user == null) {
			throw new IllegalStateException("User not found");
		}

		user.setCpf(sigonRecord.cpf());
		user.setEmail(sigonRecord.email());
		user.setPhone(sigonRecord.phone());
		user.setName(sigonRecord.name());
		user.setPassword(sigonRecord.password()); // Aqui você pode aplicar MD5 ou outro método de hash se desejar
		userRepository.update(user);

		// Atualiza informações adicionais baseadas no tipo
		String type = sigonRecord.type();
		if ("festeiro".equalsIgnoreCase(type)) {
			// Festeiro não precisa atualizar nenhum campo extra neste momento
		} else if ("organizador".equalsIgnoreCase(type)) {
			organizadorRepository.updateOrganizador(user.getId(), sigonRecord.organizadorCargo());
		} else if ("vendedor".equalsIgnoreCase(type)) {
			vendedorRepository.updateVendedor(user.getId(), sigonRecord.vendedorDescription());
		}
	}

	@Transactional(rollbackFor = SQLException.class)
	public User findUserFromToken(String authorizationHeader) throws SQLException {
		String token = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring(7);
		}

		String username = jwtTokenService.getSubjectFromToken(token);

		return userRepository.findByEmail(username);
	}

	@Transactional(rollbackFor = SQLException.class)
	public void deleteById(Long id) throws SQLException {
		if (festeiroRepository.existsById(id)) {
			festeiroRepository.deleteFesteiro(id);
		}
		if (organizadorRepository.existsById(id)) {
			organizadorRepository.deleteOrganizador(id);
		}
		if (vendedorRepository.existsById(id)) {
			vendedorRepository.deleteVendedor(id);
		}

		userRepository.delete(id);
	}
}
