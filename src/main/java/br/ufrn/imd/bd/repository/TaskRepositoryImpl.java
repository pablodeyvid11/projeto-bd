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

	private Task mapRowToTask(ResultSet rs) throws SQLException {
		Task task = new Task();
		task.setId(rs.getLong("id"));
		task.setName(rs.getString("nome"));
		task.setDescription(rs.getString("descricao"));
		task.setInitialDeadline(
				rs.getDate("prazo_inicial").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		task.setFinalDeadline(rs.getDate("prazo_fim").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		task.setPoints(rs.getDouble("pontuacao"));
		task.setEventId(rs.getLong("EVENTO_id"));
		return task;
	}
}
