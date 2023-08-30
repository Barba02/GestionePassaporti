package com.filippoBarbieri.gestionePassaporti.service;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.repository.DipendenteRepository;

@Service
@Transactional
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepo;

}
