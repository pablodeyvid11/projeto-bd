package br.ufrn.imd.bd.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.bd.model.VendedorProdutos;
import br.ufrn.imd.bd.repository.interfaces.ProdutoRepository;
import br.ufrn.imd.bd.repository.interfaces.VendedorRepository;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private VendedorRepository vendedorRepository;

	@Transactional(rollbackFor = SQLException.class)
	public List<VendedorProdutos> getAllProdutos() throws SQLException {
		return produtoRepository.findAll();
	}

	@Transactional(rollbackFor = SQLException.class)
	public List<VendedorProdutos> getProdutosByVendedorId(Long vendedorId) throws SQLException {
		return produtoRepository.findByVendedorId(vendedorId);
	}

	@Transactional(rollbackFor = SQLException.class)
	public VendedorProdutos getProdutoByNomeAndVendedorId(String nome, Long vendedorId) throws SQLException {
		return produtoRepository.findByNomeAndVendedorId(nome, vendedorId);
	}

	@Transactional(rollbackFor = SQLException.class)
	public void createProduto(VendedorProdutos produto, Long vendedorId) throws SQLException {
		if (vendedorRepository.existsById(vendedorId)) {
			produto.setVendedorId(vendedorId);
			produtoRepository.save(produto);
		} else {
			throw new IllegalStateException("Somente vendedores podem criar produtos.");
		}
	}

	@Transactional(rollbackFor = SQLException.class)
	public void updateProduto(VendedorProdutos produto, Long vendedorId) throws SQLException {
		if (vendedorRepository.existsById(vendedorId)) {
			if (produto.getVendedorId().equals(vendedorId)) {
				produtoRepository.update(produto);
			} else {
				throw new IllegalStateException("Vendedor só pode atualizar seus próprios produtos.");
			}
		} else {
			throw new IllegalStateException("Somente vendedores podem atualizar produtos.");
		}
	}

	@Transactional(rollbackFor = SQLException.class)
	public void deleteProduto(String nome, Long vendedorId) throws SQLException {
		if (vendedorRepository.existsById(vendedorId)) {
			VendedorProdutos produto = produtoRepository.findByNomeAndVendedorId(nome, vendedorId);
			if (produto != null && produto.getVendedorId().equals(vendedorId)) {
				produtoRepository.delete(nome, vendedorId);
			} else {
				throw new IllegalStateException("Vendedor só pode deletar seus próprios produtos.");
			}
		} else {
			throw new IllegalStateException("Somente vendedores podem deletar produtos.");
		}
	}
}
