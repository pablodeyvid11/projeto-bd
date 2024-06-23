package br.ufrn.imd.bd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.Noticia;
import br.ufrn.imd.bd.services.NoticiaService;

@RestController
@RequestMapping("/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @GetMapping("/evento/{eventoId}")
    public List<Noticia> getNoticiasByEventoId(@PathVariable Long eventoId) {
        return noticiaService.getNoticiasByEventoId(eventoId);
    }
}