package com.filippoBarbieri.gestionePassaporti.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import com.filippoBarbieri.gestionePassaporti.service.CittadinoService;

@RestController
@RequestMapping("/cittadino")
public class CittadinoController {
    @Autowired
    private CittadinoService service;

    @PostMapping(path = "/registra", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> registraCittadino(@RequestBody Cittadino c) {
        try {
            service.registraCittadino(c);
            return new ResponseEntity<>("Cittadino registrato", HttpStatus.CREATED);
        }
        catch(Exception e) {
            ErroreDTO dto = new ErroreDTO(e.getClass().getSimpleName(), e.getMessage());
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
        }
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErroreDTO> gestioneErroreCentralizzata(Exception e) {
        ErroreDTO dto = new ErroreDTO(e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
