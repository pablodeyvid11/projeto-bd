package br.ufrn.imd.bd.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.User;
import br.ufrn.imd.bd.model.UserDetailsImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private DataSource dataSource;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User u = findByEmail(username);
		return new UserDetailsImpl(u);
	}

	public User findByEmail(String email) {
		String sql = String.format("SELECT * FROM %s WHERE email = ?", "USUARIO");
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return mapRowToUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private User mapRowToUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setCpf(rs.getString("cpf"));
		user.setEmail(rs.getString("email"));
		user.setPhone(rs.getString("telefone"));
		user.setName(rs.getString("nome"));
		user.setPassword(rs.getString("senha"));
		return user;
	}
}
