package com.filippoBarbieri.gestionePassaporti.controller;


import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.dto.ModificaDTO;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import com.filippoBarbieri.gestionePassaporti.service.CittadinoService;

@RestController
@RequestMapping("/cittadino")
public class CittadinoController extends Controller {
    @Autowired
    private CittadinoService service;

    @PostMapping(path = "/registra", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> registraCittadino(@RequestBody Cittadino c) {
        try {
            service.registraCittadino(c);
            return new ResponseEntity<>("Cittadino registrato", HttpStatus.CREATED);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_ACCEPTABLE);
        }
        catch (DuplicateKeyException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.CONFLICT);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{cf}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getCittadino(@PathVariable String cf) {
        try {
            Cittadino c = service.getCittadino(cf);
            c.getPassword().hide();
            return new ResponseEntity<>(c, HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{cf}/riserva", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> riservaSlot(@PathVariable String cf, @RequestBody Long[] id) {
        try {
            Slot s = service.riservaSlot(id[0], cf);
            s.getCittadino().getPassword().hide();
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
        catch(IllegalStateException | IllegalAccessException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{cf}/slots", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getSlots(@PathVariable String cf) {
        try {
            List<Slot> l = service.getSlots(cf);
            l.forEach(s -> s.getCittadino().getPassword().hide());
            return new ResponseEntity<>(l, HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{cf}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> modificaCittadino(@PathVariable String cf, @RequestBody Cittadino c) {
        try {
            ModificaDTO<Cittadino> newCitt = service.modificaCittadino(cf, c);
            newCitt.getObj().getPassword().hide();
            return new ResponseEntity<>(newCitt, HttpStatus.OK);
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
