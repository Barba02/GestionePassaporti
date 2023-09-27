package com.filippoBarbieri.gestionePassaporti.controller;


import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Notifica;
import com.filippoBarbieri.gestionePassaporti.service.NotificaService;

@RestController
@RequestMapping("/notifica")
public class NotificaController extends Controller {
    @Autowired
    private NotificaService service;

    @PostMapping(path = "/inserisci", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> inserisci(@RequestBody Notifica n) {
        try {
            service.inserisciNotifica(n);
            return new ResponseEntity<>("Notifica inserita", HttpStatus.CREATED);
        }
        catch (NoSuchElementException | IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(path = "/{id}/verifica", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> inserisci(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.verifica(id), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public ResponseEntity<Object> login(String[] cred) {
        return new ResponseEntity<>("Metodo non permesso", HttpStatus.FORBIDDEN);
    }
}
