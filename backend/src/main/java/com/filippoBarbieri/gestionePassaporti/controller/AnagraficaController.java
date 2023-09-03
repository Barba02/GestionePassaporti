package com.filippoBarbieri.gestionePassaporti.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Anagrafica;
import com.filippoBarbieri.gestionePassaporti.service.AnagraficaService;

@RestController
@RequestMapping("/anagrafica")
public class AnagraficaController extends Controller {
    @Autowired
    private AnagraficaService service;

    @PostMapping(path = "/cf", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> retrieveCf(@RequestBody Anagrafica a) {
        return new ResponseEntity<>(service.retrieveCf(a), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> login(String[] cred) {
        return new ResponseEntity<>("Metodo non permesso", HttpStatus.FORBIDDEN);
    }
}
