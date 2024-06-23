package br.ufrn.imd.bd.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.bd.model.Noticia;
import br.ufrn.imd.bd.repository.NoticiaRepositoryImpl;

@Service
public class NoticiaService {

	@Autowired
	private NoticiaRepositoryImpl noticiaRepository;

	public List<Noticia> getNoticiasByEventoId(Long eventoId) {
		return noticiaRepository.findAllByEventoId(eventoId);
	}
}