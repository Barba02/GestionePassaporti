package com.filippoBarbieri.gestionePassaporti.controller;


import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import com.filippoBarbieri.gestionePassaporti.service.CittadinoService;

@RestController
@RequestMapping("/cittadino")
public class CittadinoController extends Controller {
    @Autowired
    private CittadinoService service;
    /*TODO: uppercase, capitalize*/

    @PostMapping(path = "/registra", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> registraCittadino(@RequestBody Cittadino c) {
        try {
            service.registraCittadino(c);
            return new ResponseEntity<>("Cittadino registrato", HttpStatus.CREATED);
        }
        catch(NoSuchElementException | DuplicateKeyException e) {
            String exceptionName = e.getClass().getSimpleName();
            ErroreDTO dto = new ErroreDTO(exceptionName, e.getMessage());
            HttpStatus status = (exceptionName.equals("NoSuchElementException")) ?
                    HttpStatus.NOT_ACCEPTABLE :
                    HttpStatus.CONFLICT;
            return new ResponseEntity<>(dto, status);
        }
    }

    @GetMapping(path = "/{cf}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getCittadino(@PathVariable String cf) {
        try {
            /*TODO: dto, exception*/
            return new ResponseEntity<>(service.getCittadino(cf), HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            ErroreDTO dto = new ErroreDTO(e.getClass().getSimpleName(), e.getMessage());
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{cf}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> modificaCittadino(@PathVariable String cf, @RequestBody Map<String, Object> newAttribs) {
        try {
            service.modificaCittadino(cf, newAttribs);
            return new ResponseEntity<>("Dati cittadino aggiornati", HttpStatus.OK);
        }
        catch (NoSuchElementException | NoSuchFieldException e) {
            String exceptionName = e.getClass().getSimpleName();
            ErroreDTO dto = new ErroreDTO(exceptionName, e.getMessage());
            HttpStatus status = (exceptionName.equals("NoSuchElementException")) ?
                    HttpStatus.NOT_FOUND :
                    HttpStatus.NOT_MODIFIED;
            return new ResponseEntity<>(dto, status);
        }
    }
}
