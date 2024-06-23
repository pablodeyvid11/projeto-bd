package br.ufrn.imd.bd.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.bd.model.User;
import br.ufrn.imd.bd.repository.interfaces.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "USUARIO";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

	@Override
	public User findById(Long id) throws SQLException {
		try (Connection connection = dataSource.getConnection()) {

			String SQL = String.format("SELECT * FROM %s WHERE id = ?", getTableName());

			PreparedStatement ps = connection.prepareStatement(SQL);
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setCpf(rs.getString("cpf"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setPassword(rs.getString("password"));

				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	@Override
	public void save(User user) throws SQLException {
		String sql = "INSERT INTO USUARIO (id, cpf, email, telefone, nome, senha) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, user.getId());
			ps.setString(2, user.getCpf());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPhone());
			ps.setString(5, user.getName());
			ps.setString(6, user.getPassword());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public User findByEmail(String email) throws SQLException {
		String sql = String.format("SELECT * FROM %s WHERE email = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return mapRowToUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	@Override
	public void update(User user) throws SQLException {
		String sql = String.format(
				"UPDATE %s SET cpf = ?, email = ?, telefone = ?, nome = ?, senha = MD5(?) WHERE id = ?",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, user.getCpf());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getName());
			ps.setString(5, user.getPassword()); // Aplicando hash MD5 aqui, conforme solicitado anteriormente
			ps.setLong(6, user.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void delete(Long id) throws SQLException {
		String sql = String.format("DELETE FROM %s WHERE id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
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

	@Override
	public User findByUsername(String email) throws SQLException {
		return findByEmail(email);
	}

}
