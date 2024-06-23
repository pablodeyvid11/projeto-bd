package br.ufrn.imd.bd.repository.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import br.ufrn.imd.bd.model.User;

public interface UserRepository extends RepositoryGeneric {
	User findById(Long id) throws SQLException;

	User findByEmail(String email) throws SQLException;

	User findByUsername(String email) throws SQLException;

	void update(User userDto) throws SQLException;

	void save(User user) throws SQLException;

	void delete(Long id) throws SQLException;

	DataSource getDatasource();

	public default Long getNextId() throws SQLException {
		String sql = String.format("SELECT MAX(id) AS max_id FROM %s", getTableName());

		try (Connection connection = getDatasource().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				Long maxId = rs.getLong("max_id");
				return (maxId != null ? maxId : 0) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return 1L;
	}
}