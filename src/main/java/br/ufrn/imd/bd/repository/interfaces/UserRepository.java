package br.ufrn.imd.bd.repository.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import br.ufrn.imd.bd.model.User;

public interface UserRepository extends RepositoryGeneric {
	User findById(Long id);

	User findByEmail(String email);

	User findByUsername(String email);

	void update(User userDto);

	void save(User user);

	void delete(Long id);

	DataSource getDatasource();

	public default Long getNextId() {
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
		}
		return 1L;
	}
}