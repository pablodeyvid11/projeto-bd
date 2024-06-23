package br.ufrn.imd.bd.repository.interfaces;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.sql.DataSource;

public interface RepositoryGeneric {
	String getTableName();

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

	public default boolean existsById(Long id) throws SQLException {
		String sql = String.format("SELECT COUNT(*) FROM %s WHERE id = ?", getTableName());
		try (Connection connection = getDatasource().getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}

	public default Date convertToSqlDate(LocalDateTime dateTime) {
		LocalDate localDate = dateTime.toLocalDate();
		return Date.valueOf(localDate);
	}
}
