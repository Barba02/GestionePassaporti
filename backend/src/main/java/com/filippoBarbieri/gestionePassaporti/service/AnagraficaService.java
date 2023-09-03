package com.filippoBarbieri.gestionePassaporti.service;


import java.time.LocalDate;
import org.springframework.stereotype.Service;
import com.filippoBarbieri.gestionePassaporti.enums.Sesso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.entity.Anagrafica;
import com.filippoBarbieri.gestionePassaporti.repository.AnagraficaRepository;

@Service
@Transactional
public class AnagraficaService {
    @Autowired
    private AnagraficaRepository anagraficaRepo;

    public String retrieveCf(Anagrafica a) {
        return new Anagrafica(a.getNome(), a.getCognome(), a.getSesso(), a.getData_nascita(), a.getLuogo_nascita(), a.getProvincia_nascita()).getCf();
    }
}
