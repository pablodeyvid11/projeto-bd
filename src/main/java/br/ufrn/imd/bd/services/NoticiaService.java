package br.ufrn.imd.bd.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.Noticia;
import br.ufrn.imd.bd.repository.interfaces.EstabelecimentoHasOrganizadorHasEventoRepository;
import br.ufrn.imd.bd.repository.interfaces.NoticiaRepository;
import br.ufrn.imd.bd.repository.interfaces.OrganizadorRepository;

@Service
public class NoticiaService {

	@Autowired
	private NoticiaRepository noticiaRepository;

	@Autowired
	private EstabelecimentoHasOrganizadorHasEventoRepository estabelecimentoHasOrganizadorHasEventoRepository;

	@Autowired
	private OrganizadorRepository organizadorRepository;

	public List<Noticia> getNoticiasByEventoId(Long eventoId) throws SQLException {
		return noticiaRepository.findAllByEventoId(eventoId);
	}

	public List<Noticia> getAllNoticias() throws SQLException {
		return noticiaRepository.findAll();
	}

	public Noticia getNoticiaById(Long id) throws SQLException {
		return noticiaRepository.findById(id);
	}

	public void createNoticia(Noticia noticia, Long organizadorId) throws SQLException {
		if (organizadorRepository.existsById(organizadorId)) {
			if (estabelecimentoHasOrganizadorHasEventoRepository.isEventoCreatedByOrganizador(noticia.getEventoId(),
					organizadorId)) {
				noticia.setId(noticiaRepository.getNextId());
				noticia.setDate(LocalDateTime.now());
				noticiaRepository.save(noticia);
			} else {
				throw new IllegalStateException("Organizador só pode criar notícias em eventos que ele criou.");
			}
		} else {
			throw new IllegalStateException("Somente organizadores podem criar notícias.");
		}
	}

	public void updateNoticia(Noticia noticia, Long organizadorId) throws SQLException {
		if (organizadorRepository.existsById(organizadorId)) {
			if (estabelecimentoHasOrganizadorHasEventoRepository.isEventoCreatedByOrganizador(noticia.getEventoId(),
					organizadorId)) {
				noticia.setDate(LocalDateTime.now());
				noticiaRepository.update(noticia);
			} else {
				throw new IllegalStateException("Organizador só pode atualizar notícias em eventos que ele criou.");
			}
		} else {
			throw new IllegalStateException("Somente organizadores podem atualizar notícias.");
		}
	}

	public void deleteNoticia(Long noticiaId, Long organizadorId) throws SQLException {
		Noticia noticia = noticiaRepository.findById(noticiaId);
		if (noticia != null && organizadorRepository.existsById(organizadorId)) {
			if (estabelecimentoHasOrganizadorHasEventoRepository.isEventoCreatedByOrganizador(noticia.getEventoId(),
					organizadorId)) {
				noticiaRepository.delete(noticiaId);
			} else {
				throw new IllegalStateException("Organizador só pode deletar notícias em eventos que ele criou.");
			}
		} else {
			throw new IllegalStateException("Somente organizadores podem deletar notícias.");
		}
	}
}