package br.ufrn.imd.bd.repository;

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
public class EventoRepositoryImpl implements EventosRepository{

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
	
    private Evento mapRowToEvento(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setId(rs.getLong("id"));
        evento.setTitle(rs.getString("titulo"));
        evento.setIngressoPrice(rs.getBigDecimal("preco_ingresso").doubleValue());
        evento.setDescription(rs.getString("descricao"));
        evento.setStartDateTime(rs.getDate("data_horario_inicio").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        evento.setEndDateTime(rs.getDate("data_horario_fim").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        evento.setQtyIngressos(rs.getInt("qtd_ingressos"));
        return evento;
    }
}