package br.ufrn.imd.bd.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public List<Noticia> findAllByEventoId(Long eventoId) throws SQLException {
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
			throw e;
		}
		return noticias;
	}

	@Override
	public List<Noticia> findAll() throws SQLException {
		List<Noticia> noticias = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				noticias.add(mapRowToNoticia(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return noticias;
	}

	@Override
	public Noticia findById(Long id) throws SQLException {
		String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToNoticia(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	@Override
	public void save(Noticia noticia) throws SQLException {
		String sql = String.format(
				"INSERT INTO %s (id, titulo, data_horario, texto, midia_path, EVENTO_id) VALUES (?, ?, ?, ?, ?, ?)",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, noticia.getId());
			ps.setString(2, noticia.getTitle());
			ps.setDate(3, convertToSqlDate(noticia.getDate()));
			ps.setString(4, noticia.getText());
			ps.setString(5, noticia.getMidiaPath());
			ps.setLong(6, noticia.getEventoId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void update(Noticia noticia) throws SQLException {
		String sql = String.format("UPDATE %s SET titulo = ?, data_horario = ?, texto = ?, midia_path = ? WHERE id = ?",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, noticia.getTitle());
			ps.setDate(2, convertToSqlDate(noticia.getDate()));
			ps.setString(3, noticia.getText());
			ps.setString(4, noticia.getMidiaPath());
			ps.setLong(5, noticia.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void delete(Long id) throws SQLException {
		String sql = String.format("DELETE FROM %s WHERE id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private Noticia mapRowToNoticia(ResultSet rs) throws SQLException {
		Noticia noticia = new Noticia();
		noticia.setId(rs.getLong("id"));
		noticia.setTitle(rs.getString("titulo"));
		noticia.setDate(rs.getTimestamp("data_horario").toLocalDateTime());
		noticia.setText(rs.getString("texto"));
		noticia.setMidiaPath(rs.getString("midia_path"));
		noticia.setEventoId(rs.getLong("EVENTO_id"));
		return noticia;
	}
}