package com.filippoBarbieri.gestionePassaporti.controller;


import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.id.IdSlot;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
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
    }

    @GetMapping(path = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getSlot(@PathVariable IdSlot id) {
        try {
            return new ResponseEntity<>(service.getSlot(id), HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{sede}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getSlot(@PathVariable Sede sede) {
        return new ResponseEntity<>(service.getSlotsAt(sede), HttpStatus.OK);
    }

    /*TODO: date mancanti*/
    @GetMapping(path = "/{from}&{to}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getSlot(@PathVariable LocalDateTime from, @PathVariable LocalDateTime to) {
        return new ResponseEntity<>(service.getSlotsBeetwen(from, to), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> eliminaSlot(@PathVariable IdSlot id) {
        try {
            service.eliminaSlot(id);
            return new ResponseEntity<>("Slot eliminato", HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> modificaSlot(@PathVariable IdSlot id, @RequestBody Slot s) {
        try {
            return new ResponseEntity<>(service.modificaSlot(id, s), HttpStatus.OK);
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
