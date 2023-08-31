package com.filippoBarbieri.gestionePassaporti.controller;


import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.service.DipendenteService;

@RestController
@RequestMapping("/dipendente")
public class DipendenteController extends Controller {
    @Autowired
    private DipendenteService service;

    @GetMapping(path = "/{username}/slots", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getSlots(@PathVariable String username) {
        try {
            return new ResponseEntity<>(service.getSlots(username), HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
}
