package com.filippoBarbieri.gestionePassaporti.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.service.DipendenteService;

@RestController
@RequestMapping("/dipendente")
public class DipendenteController extends Controller {
    @Autowired
    private DipendenteService service;

    @PostMapping(path = "/login", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> login(@RequestBody String[] cred) {
        return loginTemplate(cred[0], cred[1]);
    }
}
