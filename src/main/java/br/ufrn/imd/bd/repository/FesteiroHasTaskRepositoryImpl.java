package br.ufrn.imd.bd.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.bd.model.FesteiroHasTask;
import br.ufrn.imd.bd.repository.interfaces.FesteiroHasTaskRepository;

@Repository
public class FesteiroHasTaskRepositoryImpl implements FesteiroHasTaskRepository {

	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "FESTEIRO_has_TASK";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

	@Override
	public void save(FesteiroHasTask festeiroHasTask) {
		String sql = String.format(
				"INSERT INTO %s (FESTEIRO_id, TASK_id, pontos_ganhos, is_validado) VALUES (?, ?, ?, ?)",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, festeiroHasTask.getFesteiroId());
			ps.setLong(2, festeiroHasTask.getTaskId());
			ps.setBigDecimal(3, BigDecimal.valueOf(festeiroHasTask.getPointsWin()));
			ps.setBoolean(4, festeiroHasTask.getIsValidated());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean existsByFesteiroIdAndTaskId(Long festeiroId, Long taskId) {
		String sql = String.format("SELECT COUNT(*) FROM %s WHERE FESTEIRO_id = ? AND TASK_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, festeiroId);
			ps.setLong(2, taskId);
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

	@Override
	public void update(FesteiroHasTask festeiroHasTask) {
		String sql = String.format(
				"UPDATE %s SET pontos_ganhos = ?, is_validado = ? WHERE TASK_id = ? AND FESTEIRO_id = ?",
				getTableName());

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setDouble(1, festeiroHasTask.getPointsWin());
			ps.setBoolean(2, festeiroHasTask.getIsValidated());
			ps.setLong(3, festeiroHasTask.getTaskId());
			ps.setLong(4, festeiroHasTask.getFesteiroId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public FesteiroHasTask findByTaskIdAndFesteiroId(Long taskId, Long festeiroId) {
		String sql = String.format("SELECT * FROM %s WHERE TASK_id = ? AND FESTEIRO_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, taskId);
			ps.setLong(2, festeiroId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToFesteiroHasTask(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private FesteiroHasTask mapRowToFesteiroHasTask(ResultSet rs) throws SQLException {
		FesteiroHasTask festeiroHasTask = new FesteiroHasTask();
		festeiroHasTask.setTaskId(rs.getLong("TASK_id"));
		festeiroHasTask.setFesteiroId(rs.getLong("FESTEIRO_id"));
		festeiroHasTask.setPointsWin(rs.getDouble("pontos_ganhos"));
		festeiroHasTask.setIsValidated(rs.getBoolean("is_validado"));
		return festeiroHasTask;
	}
}
