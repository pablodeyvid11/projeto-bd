package br.ufrn.imd.bd.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.bd.repository.interfaces.VendedorRepository;

@Repository
public class VendedorRepositoryImpl implements VendedorRepository {

	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "VENDEDOR";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

	@Override
	public void save(Long id, String descricao) {
		String sql = String.format("INSERT INTO %s (id, descricao) VALUES (?, ?)", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			ps.setString(2, descricao);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateVendedor(Long id, String descricao) {
		String sql = String.format("UPDATE %s SET descricao = ? WHERE id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, descricao);
			ps.setLong(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteVendedor(Long id) {
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
