package br.ufrn.imd.bd.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.bd.model.Ingresso;
import br.ufrn.imd.bd.repository.interfaces.IngressoRepository;

@Repository
public class IngressoRepositoryImpl implements IngressoRepository {
	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "INGRESSO";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

	@Override
	public void save(Ingresso ingresso) throws SQLException {
		String sql = String.format("INSERT INTO %s (token, EVENTO_id, FESTEIRO_id) VALUES (?, ?, ?)", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, ingresso.getToken());
			ps.setLong(2, ingresso.getEventoId());
			ps.setLong(3, ingresso.getFesteiroId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int countByEventoId(Long eventoId) throws SQLException {
		String sql = String.format("SELECT COUNT(*) FROM %s WHERE EVENTO_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, eventoId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return 0;
	}

	@Override
	public Ingresso findByEventoIdAndFesteiroId(Long eventoId, Long festeiroId) throws SQLException {
		String sql = String.format("SELECT * FROM %s WHERE EVENTO_id = ? AND FESTEIRO_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, eventoId);
			ps.setLong(2, festeiroId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToIngresso(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	private Ingresso mapRowToIngresso(ResultSet rs) throws SQLException {
		Ingresso ingresso = new Ingresso();
		ingresso.setToken(rs.getString("token"));
		ingresso.setEventoId(rs.getLong("EVENTO_id"));
		ingresso.setFesteiroId(rs.getLong("FESTEIRO_id"));
		return ingresso;
	}
}
