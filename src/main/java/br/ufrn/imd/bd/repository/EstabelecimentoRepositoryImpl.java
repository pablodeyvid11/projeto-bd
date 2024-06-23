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

import br.ufrn.imd.bd.model.Estabelecimento;
import br.ufrn.imd.bd.repository.interfaces.EstabelecimentoRepository;

@Repository
public class EstabelecimentoRepositoryImpl implements EstabelecimentoRepository {
    @Autowired
    private DataSource dataSource;

    @Override
    public String getTableName() {
        return "ESTABELECIMENTO";
    }

    @Override
    public DataSource getDatasource() {
        return dataSource;
    }

    @Override
    public List<Estabelecimento> findAll() {
        List<Estabelecimento> estabelecimentos = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s", getTableName());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                estabelecimentos.add(mapRowToEstabelecimento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estabelecimentos;
    }

    @Override
    public Estabelecimento findById(String cnpj) {
        String sql = String.format("SELECT * FROM %s WHERE cnpj = ?", getTableName());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cnpj);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToEstabelecimento(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Estabelecimento estabelecimento) {
        String sql = String.format(
                "INSERT INTO %s (cnpj, nome, endereco_bairro, endereco_rua, endereco_cep, endereco_numero, organizador_criador_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                getTableName());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estabelecimento.getCnpj());
            ps.setString(2, estabelecimento.getName());
            ps.setString(3, estabelecimento.getAddressBairro());
            ps.setString(4, estabelecimento.getAddressRua());
            ps.setString(5, estabelecimento.getAddressCep());
            ps.setString(6, estabelecimento.getAddressNumero());
            ps.setLong(7, estabelecimento.getOrganizadorCriadorId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Estabelecimento estabelecimento) {
        String sql = String.format(
                "UPDATE %s SET nome = ?, endereco_bairro = ?, endereco_rua = ?, endereco_cep = ?, endereco_numero = ?, organizador_criador_id = ? WHERE cnpj = ?",
                getTableName());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estabelecimento.getName());
            ps.setString(2, estabelecimento.getAddressBairro());
            ps.setString(3, estabelecimento.getAddressRua());
            ps.setString(4, estabelecimento.getAddressCep());
            ps.setString(5, estabelecimento.getAddressNumero());
            ps.setLong(6, estabelecimento.getOrganizadorCriadorId());
            ps.setString(7, estabelecimento.getCnpj());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String cnpj) {
        String sql = String.format("DELETE FROM %s WHERE cnpj = ?", getTableName());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cnpj);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Estabelecimento mapRowToEstabelecimento(ResultSet rs) throws SQLException {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setCnpj(rs.getString("cnpj"));
        estabelecimento.setName(rs.getString("nome"));
        estabelecimento.setAddressBairro(rs.getString("endereco_bairro"));
        estabelecimento.setAddressRua(rs.getString("endereco_rua"));
        estabelecimento.setAddressCep(rs.getString("endereco_cep"));
        estabelecimento.setAddressNumero(rs.getString("endereco_numero"));
        estabelecimento.setOrganizadorCriadorId(rs.getLong("organizador_criador_id"));
        return estabelecimento;
    }
}
