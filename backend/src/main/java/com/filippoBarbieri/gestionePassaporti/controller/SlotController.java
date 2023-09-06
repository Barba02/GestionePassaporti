package com.filippoBarbieri.gestionePassaporti.controller;


import java.util.List;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import com.filippoBarbieri.gestionePassaporti.dto.ModificaDTO;
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

    @GetMapping(path = "/sedi", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getSedi(@RequestParam(required = false) String tipo,
                                             @RequestParam(required = false) String stato) {
        return new ResponseEntity<>(service.getListaSedi(tipo, stato), HttpStatus.OK);
    }

    @GetMapping(path = "/{obj}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getMapping(@PathVariable String obj,
                                             @RequestParam(required = false) LocalDateTime from,
                                             @RequestParam(required = false) LocalDateTime to,
                                             @RequestParam(required = false) String stato,
                                             @RequestParam(required = false) String tipo) {

        try {
            return getSlot(Long.parseLong(obj));
        }
        catch(NumberFormatException e) {
            return getSlots(obj, stato, tipo, from, to);
        }
    }
    public ResponseEntity<Object> getSlot(Long id) {
        try {
            Slot s = service.getSlot(id);
            if (s.getCittadino() != null)
                s.getCittadino().getPassword().hide();
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
        catch(NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Object> getSlots(String sede, String stato, String tipo, LocalDateTime from, LocalDateTime to) {
        try {
            List<Slot> l = service.getSlots(sede, stato, tipo, from, to);
            l.stream().filter(s -> s.getCittadino() != null).forEach(s -> s.getCittadino().getPassword().hide());
            return new ResponseEntity<>(l, HttpStatus.OK);
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
    public ResponseEntity<Object> modificaSlot(@PathVariable Long id, @RequestBody Slot slot) {
        try {
            ModificaDTO<Slot> s = service.modificaSlot(id, slot);
            if (s.getObj().getCittadino() != null)
                s.getObj().getCittadino().getPassword().hide();
            return new ResponseEntity<>(s, HttpStatus.OK);
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

    @Override
    public ResponseEntity<Object> login(String[] cred) {
        return new ResponseEntity<>("Metodo non permesso", HttpStatus.FORBIDDEN);
    }
}
