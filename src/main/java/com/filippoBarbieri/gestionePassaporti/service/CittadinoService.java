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
        System.out.println(anagraficaRepo.existsById("BRBFPP02R01E349J"));
        /* TODO: risolvere (exists, find) */
        if (cittadinoRepo.existsById(c.getCf()))
            throw new DuplicateKeyException("Cittadino già registrato");
        if (!anagraficaRepo.existsById(c.getCf()))
            throw new NoSuchElementException("Cittadino non presente nel database");
        cittadinoRepo.save(c);
    }

    public Cittadino getCittadino(String cf) throws NoSuchElementException {
        if (!cittadinoRepo.existsById(cf))
            throw new NoSuchElementException("Cittadino non registrato");
        return cittadinoRepo.getReferenceById(cf);
    }

    public void modificaCittadino(String cf, Map<String, Object> newAttribs) throws NoSuchElementException, NoSuchFieldException {
        if (!cittadinoRepo.existsById(cf))
            throw new NoSuchElementException("Cittadino non registrato");
        StringBuilder notUpdated = new StringBuilder();
        Cittadino c = cittadinoRepo.getReferenceById(cf);
        for (Map.Entry<String, Object> e : newAttribs.entrySet()) {
            switch (e.getKey()) {
                case "ts" -> c.setTs((String) e.getValue());
                case "password" -> c.setPassword((String) e.getValue());
                case "di_servizio" -> c.setDi_servizio((Boolean) e.getValue());
                case "diplomatico" -> c.setDiplomatico((Boolean) e.getValue());
                case "figli_minori" -> c.setFigli_minori((Boolean) e.getValue());
                default -> notUpdated.append(e.getKey()).append(", ");
            }
        }
        /* TODO: dto restituito */
        if (!notUpdated.isEmpty())
            throw new NoSuchFieldException(notUpdated.substring(0, notUpdated.length()-2) +
                    " inesistenti o non modificabili; altri dati aggiornati");
    }
}
