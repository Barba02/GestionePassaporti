package com.filippoBarbieri.gestionePassaporti.controller;


import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.dto.ModificaDTO;
import com.filippoBarbieri.gestionePassaporti.entity.Dipendente;
import com.filippoBarbieri.gestionePassaporti.service.DipendenteService;

@RestController
@RequestMapping("/dipendente")
public class DipendenteController extends Controller {
    @Autowired
    private DipendenteService service;

    @GetMapping(path = "/{username}/slots", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getSlots(@PathVariable String username) {
        try {
            List<Slot> l = service.getSlots(username);
            l.stream().filter(s -> s.getCittadino() != null).forEach(s -> s.getCittadino().getPassword().hide());
            return new ResponseEntity<>(l, HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{username}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> modificaCittadino(@PathVariable String username, @RequestBody Dipendente d) {
        try {
            ModificaDTO<Dipendente> newDip = service.modificaDipendente(username, d);
            newDip.getObj().getPassword().hide();
            return new ResponseEntity<>(newDip, HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
        catch(IllegalAccessException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
