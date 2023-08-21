package com.filippoBarbieri.gestionePassaporti.service;


import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.repository.CittadinoRepository;
import com.filippoBarbieri.gestionePassaporti.repository.AnagraficaRepository;

@Service
@Transactional
public class CittadinoService {
    @Autowired
    private CittadinoRepository cittadinoRepo;
    @Autowired
    private AnagraficaRepository anagraficaRepo;

    public void registraCittadino(Cittadino c) throws NoSuchElementException, DuplicateKeyException {
        String s = c.getCf();
        if (!anagraficaRepo.existsById(s))
            throw new NoSuchElementException("Cittadino non presente nel database");
        if (cittadinoRepo.existsById(c.getCf()))
            throw new DuplicateKeyException("Cittadino già registrato");
        cittadinoRepo.save(c);
    }

    public Cittadino getCittadino(String cf) throws NoSuchElementException {
        Optional<Cittadino> c = cittadinoRepo.findById(cf);
        if (c.isEmpty())
            throw new NoSuchElementException("Cittadino non registrato");
        return c.get();
    }
}
