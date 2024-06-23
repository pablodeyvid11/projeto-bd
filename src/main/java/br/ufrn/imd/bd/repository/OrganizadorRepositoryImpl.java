package br.ufrn.imd.bd.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.bd.repository.interfaces.OrganizadorRepository;

@Repository
public class OrganizadorRepositoryImpl implements OrganizadorRepository {

	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "ORGANIZADOR";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

	@Override
	public void save(Long id, String cargo) {
		String sql = String.format("INSERT INTO %s (id, cargo) VALUES (?, ?)", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			ps.setString(2, cargo);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateOrganizador(Long id, String cargo) {
		String sql = String.format("UPDATE %s SET cargo = ? WHERE id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, cargo);
			ps.setLong(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrganizador(Long id) {
		String sql = String.format("DELETE FROM %s WHERE id = ?", getTableName());

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
