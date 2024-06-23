package br.ufrn.imd.bd.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.bd.model.FesteiroAvaliaEvento;
import br.ufrn.imd.bd.repository.interfaces.AvaliacaoRepository;

@Repository
public class AvaliacaoRepositoryImpl implements AvaliacaoRepository {
	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "FESTEIRO_avalia_EVENTO";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

	@Override
	public List<FesteiroAvaliaEvento> findAll() {
		List<FesteiroAvaliaEvento> avaliacoes = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				avaliacoes.add(mapRowToAvaliacao(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return avaliacoes;
	}

	@Override
	public List<FesteiroAvaliaEvento> findAllByEventoId(Long eventoId) {
		List<FesteiroAvaliaEvento> avaliacoes = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s WHERE EVENTO_id = ?", getTableName());

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, eventoId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					avaliacoes.add(mapRowToAvaliacao(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return avaliacoes;
	}

	@Override
	public Double findAverageRatingByEventoId(Long eventoId) {
		String sql = String.format("SELECT AVG(nota) AS average_rating FROM %s WHERE EVENTO_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, eventoId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getDouble("average_rating");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public FesteiroAvaliaEvento findByEventoIdAndFesteiroId(Long eventoId, Long festeiroId) {
		String sql = String.format("SELECT * FROM %s WHERE EVENTO_id = ? AND FESTEIRO_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, eventoId);
			ps.setLong(2, festeiroId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToAvaliacao(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(FesteiroAvaliaEvento avaliacao) {
		String sql = String.format("INSERT INTO %s (EVENTO_id, FESTEIRO_id, nota, comentario) VALUES (?, ?, ?, ?)",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, avaliacao.getEventoId());
			ps.setLong(2, avaliacao.getFesteiroId());
			ps.setBigDecimal(3, BigDecimal.valueOf(avaliacao.getRating()));
			ps.setString(4, avaliacao.getComment());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(FesteiroAvaliaEvento avaliacao) {
		String sql = String.format("UPDATE %s SET nota = ?, comentario = ? WHERE EVENTO_id = ? AND FESTEIRO_id = ?",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setBigDecimal(1, BigDecimal.valueOf(avaliacao.getRating()));
			ps.setString(2, avaliacao.getComment());
			ps.setLong(3, avaliacao.getEventoId());
			ps.setLong(4, avaliacao.getFesteiroId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long eventoId, Long festeiroId) {
		String sql = String.format("DELETE FROM %s WHERE EVENTO_id = ? AND FESTEIRO_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, eventoId);
			ps.setLong(2, festeiroId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private FesteiroAvaliaEvento mapRowToAvaliacao(ResultSet rs) throws SQLException {
		FesteiroAvaliaEvento avaliacao = new FesteiroAvaliaEvento();
		avaliacao.setEventoId(rs.getLong("EVENTO_id"));
		avaliacao.setFesteiroId(rs.getLong("FESTEIRO_id"));
		avaliacao.setRating(rs.getDouble("nota"));
		avaliacao.setComment(rs.getString("comentario"));
		return avaliacao;
	}
}
