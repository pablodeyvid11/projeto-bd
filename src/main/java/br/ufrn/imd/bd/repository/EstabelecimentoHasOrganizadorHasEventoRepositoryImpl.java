package br.ufrn.imd.bd.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.bd.model.EstabelecimentoHasOrganizadorHasEvento;
import br.ufrn.imd.bd.repository.interfaces.EstabelecimentoHasOrganizadorHasEventoRepository;

@Repository
public class EstabelecimentoHasOrganizadorHasEventoRepositoryImpl
		implements EstabelecimentoHasOrganizadorHasEventoRepository {
	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "ESTABELECIMENTO_has_EVENTO_has_ORGANIZADOR";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

	@Override
	public void save(EstabelecimentoHasOrganizadorHasEvento relacao) {
		String sql = String.format("INSERT INTO %s (ESTABELECIMENTO_cnpj, ORGANIZADOR_id, EVENTO_id) VALUES (?, ?, ?)",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, relacao.getEstabelecimentoId());
			ps.setLong(2, relacao.getOrganizadorId());
			ps.setLong(3, relacao.getEventoId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteByEventoId(Long eventoId) {
		String sql = String.format("DELETE FROM %s WHERE EVENTO_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, eventoId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
    public boolean isEventoCreatedByOrganizador(Long eventoId, Long organizadorId) {
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE EVENTO_id = ? AND ORGANIZADOR_id = ?", getTableName());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, eventoId);
            ps.setLong(2, organizadorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
