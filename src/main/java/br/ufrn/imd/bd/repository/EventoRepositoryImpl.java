package br.ufrn.imd.bd.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.bd.model.Evento;
import br.ufrn.imd.bd.repository.interfaces.EventosRepository;

@Repository
public class EventoRepositoryImpl implements EventosRepository {

	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "EVENTO";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

	@Override
	public List<Evento> findAll() {
		List<Evento> eventos = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s", getTableName());

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				eventos.add(mapRowToEvento(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eventos;
	}

	@Override
	public Evento findById(Long id) {
		String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToEvento(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(Evento evento) {
		String sql = String.format(
				"INSERT INTO %s (id, titulo, preco_ingresso, descricao, data_horario_inicio, data_horario_fim, qtd_ingressos) VALUES (?, ?, ?, ?, ?, ?, ?)",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, evento.getId());
			ps.setString(2, evento.getTitle());
			ps.setBigDecimal(3, BigDecimal.valueOf(evento.getIngressoPrice()));
			ps.setString(4, evento.getDescription());
			ps.setDate(5, convertToSqlDate(evento.getStartDateTime()));
			ps.setDate(6, convertToSqlDate(evento.getEndDateTime()));
			ps.setInt(7, evento.getQtyIngressos());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


    @Override
    public void update(Evento evento) {
        String sql = String.format("UPDATE %s SET titulo = ?, preco_ingresso = ?, descricao = ?, data_horario_inicio = ?, data_horario_fim = ?, qtd_ingressos = ? WHERE id = ?", getTableName());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, evento.getTitle());
            ps.setBigDecimal(2, BigDecimal.valueOf(evento.getIngressoPrice()));
            ps.setString(3, evento.getDescription());
            ps.setDate(4, convertToSqlDate(evento.getStartDateTime()));
            ps.setDate(5, convertToSqlDate(evento.getEndDateTime()));
            ps.setInt(6, evento.getQtyIngressos());
            ps.setLong(7, evento.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", getTableName());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private Evento mapRowToEvento(ResultSet rs) throws SQLException {
		Evento evento = new Evento();
		evento.setId(rs.getLong("id"));
		evento.setTitle(rs.getString("titulo"));
		evento.setIngressoPrice(rs.getBigDecimal("preco_ingresso").doubleValue());
		evento.setDescription(rs.getString("descricao"));
		evento.setStartDateTime(
				rs.getDate("data_horario_inicio").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		evento.setEndDateTime(
				rs.getDate("data_horario_fim").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		evento.setQtyIngressos(rs.getInt("qtd_ingressos"));
		return evento;
	}
}