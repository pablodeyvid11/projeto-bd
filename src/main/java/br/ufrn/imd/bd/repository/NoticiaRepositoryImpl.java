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

import br.ufrn.imd.bd.model.Noticia;
import br.ufrn.imd.bd.repository.interfaces.NoticiaRepository;

@Repository
public class NoticiaRepositoryImpl implements NoticiaRepository {

	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "NOTICIA";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

    @Override
    public List<Noticia> findAllByEventoId(Long eventoId) {
        List<Noticia> noticias = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s WHERE EVENTO_id = ?", getTableName());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, eventoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    noticias.add(mapRowToNoticia(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noticias;
    }

	private Noticia mapRowToNoticia(ResultSet rs) throws SQLException {
		Noticia noticia = new Noticia();
		noticia.setId(rs.getLong("id"));
		noticia.setTitle(rs.getString("titulo"));
		noticia.setDate(rs.getDate("data_horario").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		noticia.setText(rs.getString("texto"));
		noticia.setMidiaPath(rs.getString("midia_path"));
		noticia.setEventoId(rs.getLong("EVENTO_id"));
		return noticia;
	}
}