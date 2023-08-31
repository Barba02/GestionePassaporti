package com.filippoBarbieri.gestionePassaporti.controller;


import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /*TODO: refactor*/
    @GetMapping(path = "/{obj}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getMapping(@PathVariable String obj,
                                             @RequestParam(required = false) LocalDateTime from,
                                             @RequestParam(required = false) LocalDateTime to,
                                             @RequestParam(required = false) String stato) {

        try {
            return getSlot(Long.parseLong(obj));
        }
        catch(NumberFormatException e) {
            return getSlots(obj, stato, from, to);
        }
    }
    public ResponseEntity<Object> getSlot(Long id) {
        try {
            return new ResponseEntity<>(service.getSlot(id), HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Object> getSlots(String sede, String stato, LocalDateTime from, LocalDateTime to) {
        try {
            return new ResponseEntity<>(service.getSlots(sede, stato, from, to), HttpStatus.OK);
        }
        catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> eliminaSlot(@PathVariable Long id) {
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
    public ResponseEntity<Object> modificaSlot(@PathVariable Long id, @RequestBody Slot s) {
        try {
            return new ResponseEntity<>(service.modificaSlot(id, s), HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
        catch(IllegalAccessException | IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
