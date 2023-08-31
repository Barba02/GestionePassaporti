package com.filippoBarbieri.gestionePassaporti.service;


import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Password;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.entity.Dipendente;
import com.filippoBarbieri.gestionePassaporti.repository.DipendenteRepository;

@Service
@Transactional
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepo;

    public Dipendente getDipendente(String username) throws NoSuchElementException {
        Dipendente d = dipendenteRepo.findById(username).orElse(null);
        if (d == null)
            throw new NoSuchElementException("Dipendente inesistente");
        return d;
    }

    public Dipendente login(String username, String password) throws NoSuchElementException, IllegalArgumentException {
        Dipendente d = getDipendente(username);
        Password psw = new Password(password);
        psw.hashPassword();
        if (!psw.equals(d.getPassword()))
            throw new IllegalArgumentException("Password errata");
        return d;
    }
}
