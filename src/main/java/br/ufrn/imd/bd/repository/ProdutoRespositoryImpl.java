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

import br.ufrn.imd.bd.model.VendedorProdutos;
import br.ufrn.imd.bd.repository.interfaces.ProdutoRepository;

@Repository
public class ProdutoRespositoryImpl implements ProdutoRepository {
	@Autowired
	private DataSource dataSource;

	@Override
	public String getTableName() {
		return "VENDEDOR_PRODUTOS";
	}

	@Override
	public DataSource getDatasource() {
		return dataSource;
	}

	@Override
	public List<VendedorProdutos> findAll() {
		List<VendedorProdutos> produtos = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				produtos.add(mapRowToProduto(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produtos;
	}

	@Override
	public List<VendedorProdutos> findByVendedorId(Long vendedorId) {
		List<VendedorProdutos> produtos = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s WHERE VENDEDOR_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, vendedorId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					produtos.add(mapRowToProduto(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produtos;
	}

	@Override
	public VendedorProdutos findByNomeAndVendedorId(String nome, Long vendedorId) {
		String sql = String.format("SELECT * FROM %s WHERE produto_nome = ? AND VENDEDOR_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, nome);
			ps.setLong(2, vendedorId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToProduto(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(VendedorProdutos produto) {
		String sql = String.format("INSERT INTO %s (VENDEDOR_id, produto_nome, descricao) VALUES (?, ?, ?)",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, produto.getVendedorId());
			ps.setString(2, produto.getProductName());
			ps.setString(3, produto.getDescription());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(VendedorProdutos produto) {
		String sql = String.format("UPDATE %s SET descricao = ? WHERE VENDEDOR_id = ? AND produto_nome = ?",
				getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, produto.getDescription());
			ps.setLong(2, produto.getVendedorId());
			ps.setString(3, produto.getProductName());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String nome, Long vendedorId) {
		String sql = String.format("DELETE FROM %s WHERE produto_nome = ? AND VENDEDOR_id = ?", getTableName());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, nome);
			ps.setLong(2, vendedorId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private VendedorProdutos mapRowToProduto(ResultSet rs) throws SQLException {
		VendedorProdutos produto = new VendedorProdutos();
		produto.setVendedorId(rs.getLong("VENDEDOR_id"));
		produto.setProductName(rs.getString("produto_nome"));
		produto.setDescription(rs.getString("descricao"));
		return produto;
	}
}
