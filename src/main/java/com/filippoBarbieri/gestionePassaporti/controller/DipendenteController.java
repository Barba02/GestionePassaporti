package com.filippoBarbieri.gestionePassaporti.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.service.DipendenteService;

@RestController
@RequestMapping("/dipendente")
public class DipendenteController extends Controller {
    @Autowired
    private DipendenteService service;


}
