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

import br.ufrn.imd.bd.model.Task;
import br.ufrn.imd.bd.repository.interfaces.TaskRepository;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "TASK";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

	@Override
	public List<Task> findAllByEventoId(Long id) {
		List<Task> tasks = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s WHERE EVENTO_id = %d", getTableName(), id);

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				tasks.add(mapRowToTask(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	@Override
	public List<Task> findAll() {
		List<Task> tasks = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				tasks.add(mapRowToTask(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	@Override
	public Task findById(Long id) {
		String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToTask(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(Task task) {
		String sql = String.format(
				"INSERT INTO %s (id, nome, descricao, pontuacao, prazo_inicial, prazo_fim, EVENTO_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, task.getId());
			ps.setString(2, task.getName());
			ps.setString(3, task.getDescription());
			ps.setBigDecimal(4, BigDecimal.valueOf(task.getPoints()));
			ps.setDate(5, convertToSqlDate(task.getInitialDeadline()));
			ps.setDate(6, convertToSqlDate(task.getFinalDeadline()));
			ps.setLong(7, task.getEventId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Task task) {
		String sql = String.format(
				"UPDATE %s SET nome = ?, descricao = ?, pontuacao = ?, prazo_inicial = ?, prazo_fim = ? WHERE id = ?",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, task.getName());
			ps.setString(2, task.getDescription());
			ps.setBigDecimal(3, BigDecimal.valueOf(task.getPoints()));
			ps.setDate(4, convertToSqlDate(task.getInitialDeadline()));
			ps.setDate(5, convertToSqlDate(task.getFinalDeadline()));
			ps.setLong(6, task.getId());
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

	private Task mapRowToTask(ResultSet rs) throws SQLException {
		Task task = new Task();
		task.setId(rs.getLong("id"));
		task.setName(rs.getString("nome"));
		task.setDescription(rs.getString("descricao"));
		task.setInitialDeadline(rs.getTimestamp("prazo_inicial").toLocalDateTime());
		task.setFinalDeadline(rs.getTimestamp("prazo_fim").toLocalDateTime());
		task.setPoints(rs.getDouble("pontuacao"));
		task.setEventId(rs.getLong("EVENTO_id"));
		return task;
	}
}
