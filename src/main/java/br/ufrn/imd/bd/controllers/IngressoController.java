package br.ufrn.imd.bd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.services.IngressoService;
import br.ufrn.imd.bd.services.UserService;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {

    @Autowired
    private IngressoService ingressoService;

    @Autowired
    private UserService userService;

    @PostMapping("/comprar")
    public String comprarIngresso(@RequestParam Long eventoId,
                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        Long festeiroId = userService.findUserFromToken(authorizationHeader).getId();
        return ingressoService.comprarIngresso(eventoId, festeiroId);
    }
}