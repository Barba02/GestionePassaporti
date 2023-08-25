package com.filippoBarbieri.gestionePassaporti.controller;


import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import com.filippoBarbieri.gestionePassaporti.entity.IdSlot;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.service.SlotService;

@RestController
@RequestMapping("/slot")
public class SlotController extends Controller {
    @Autowired
    private SlotService service;

    @PostMapping(path = "/inserisci", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> inserisciSlot(@RequestBody Slot s) {
        try {
            service.inserisciSlot(s);
            return new ResponseEntity<>("Slot inserito", HttpStatus.CREATED);
        }
        catch (DuplicateKeyException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.CONFLICT);
        }
        catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{sede}/{datetime}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getSlot(@PathVariable String sede, @PathVariable LocalDateTime datetime) {
        try {
            return new ResponseEntity<>(service.getSlot(new IdSlot(datetime, sede)), HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
        catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), "Sede inesistente"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{sede}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getSlots(@PathVariable String sede,
                                           @RequestParam(required = false) LocalDateTime from,
                                           @RequestParam(required = false) LocalDateTime to) {
        try {
            return new ResponseEntity<>(service.getSlots(sede, from, to), HttpStatus.OK);
        }
        catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), "Sede inesistente"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{sede}/{datetime}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> eliminaSlot(@PathVariable String sede, @PathVariable LocalDateTime datetime) {
        try {
            service.eliminaSlot(new IdSlot(datetime, sede));
            return new ResponseEntity<>("Slot eliminato", HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
        catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), "Sede inesistente"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{sede}/{datetime}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> modificaSlot(@PathVariable String sede, @PathVariable LocalDateTime datetime, @RequestBody Slot s) {
        try {
            return new ResponseEntity<>(service.modificaSlot(new IdSlot(datetime, sede), s), HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
        catch(IllegalAccessException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), "Sede inesistente"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
