package br.ufrn.imd.bd.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.bd.model.VendedorHasEvento;
import br.ufrn.imd.bd.repository.interfaces.VendedorEventoRepository;

@Repository
public class VendedorEventoRepositoryImpl implements VendedorEventoRepository {
	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "VENDEDOR_has_EVENTO";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

	@Override
	public void solicitarVenda(VendedorHasEvento vendedorEvento) throws SQLException {
		String sql = String.format("INSERT INTO %s (VENDEDOR_id, EVENTO_id, is_aprovado) VALUES (?, ?, ?)",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, vendedorEvento.getVendedorId());
			ps.setLong(2, vendedorEvento.getEventoId());
			ps.setNull(3, Types.BOOLEAN); // Solicitação pendente
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void aprovarVenda(Long vendedorId, Long eventoId, Boolean isAprovado) throws SQLException {
		String sql = String.format("UPDATE %s SET is_aprovado = ? WHERE VENDEDOR_id = ? AND EVENTO_id = ?",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setBoolean(1, isAprovado);
			ps.setLong(2, vendedorId);
			ps.setLong(3, eventoId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public VendedorHasEvento findByVendedorIdAndEventoId(Long vendedorId, Long eventoId) throws SQLException {
		String sql = String.format("SELECT * FROM %s WHERE VENDEDOR_id = ? AND EVENTO_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, vendedorId);
			ps.setLong(2, eventoId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToVendedorEvento(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	@Override
	public List<VendedorHasEvento> findAllByEventoIdAndIsAprovado(Long eventoId, Boolean isAprovado)
			throws SQLException {
		List<VendedorHasEvento> vendedores = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s WHERE EVENTO_id = ? AND is_aprovado = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, eventoId);
			ps.setBoolean(2, isAprovado);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					vendedores.add(mapRowToVendedorEvento(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return vendedores;
	}

	@Override
	public List<VendedorHasEvento> findAllByEventoIdAndIsAprovadoIsNull(Long eventoId) throws SQLException {
		List<VendedorHasEvento> vendedores = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s WHERE EVENTO_id = ? AND is_aprovado IS NULL", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, eventoId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					vendedores.add(mapRowToVendedorEvento(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return vendedores;
	}

	private VendedorHasEvento mapRowToVendedorEvento(ResultSet rs) throws SQLException {
		VendedorHasEvento vendedorEvento = new VendedorHasEvento();
		vendedorEvento.setVendedorId(rs.getLong("VENDEDOR_id"));
		vendedorEvento.setEventoId(rs.getLong("EVENTO_id"));
		vendedorEvento.setIsApproved(rs.getBoolean("is_aprovado"));
		return vendedorEvento;
	}
}
