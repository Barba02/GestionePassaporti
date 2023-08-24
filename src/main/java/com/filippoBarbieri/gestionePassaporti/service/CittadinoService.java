package com.filippoBarbieri.gestionePassaporti.service;


import java.util.*;
import java.lang.reflect.Field;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.dto.ModificaCittadinoDTO;
import com.filippoBarbieri.gestionePassaporti.repository.CittadinoRepository;
import com.filippoBarbieri.gestionePassaporti.repository.AnagraficaRepository;

@Service
@Transactional
public class CittadinoService {
    @Autowired
    private CittadinoRepository cittadinoRepo;
    @Autowired
    private AnagraficaRepository anagraficaRepo;

    public void registraCittadino(Cittadino c) throws NoSuchElementException, DuplicateKeyException, IllegalArgumentException {
        if (!anagraficaRepo.existsById(c.getCf()))
            throw new NoSuchElementException("Cittadino non presente nel database");
        if (cittadinoRepo.existsById(c.getCf()))
            throw new DuplicateKeyException("Cittadino già registrato");
        if (!Cittadino.isValid(c.getPassword()))
            throw new IllegalArgumentException("La password non rispetta i parametri di sicurezza");
        c.setPassword(Cittadino.hashPassword(c.getPassword()));
        cittadinoRepo.save(c);
    }

    public Cittadino getCittadino(String cf) throws NoSuchElementException {
        Cittadino c = cittadinoRepo.findById(cf).orElse(null);
        if (c == null)
            throw new NoSuchElementException("Cittadino non registrato");
        return c;
    }

    public ModificaCittadinoDTO modificaCittadino(String cf, Cittadino c) throws NoSuchElementException, IllegalAccessException {
        Cittadino old = getCittadino(cf);
        StringBuilder news = new StringBuilder();
        for (Field f : Cittadino.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.getName().equals("ts") || f.getName().equals("figli_minori") || f.getName().equals("diplomatico") || f.getName().equals("di_servizio")) {
                if (f.get(c) != null && !f.get(c).equals(f.get(old))) {
                    news.append(f.getName()).append("|");
                    f.set(old, f.get(c));
                }
            }
        }
        String psw = c.getPassword();
        String hash = Cittadino.hashPassword(psw);
        if (psw != null && !old.getPassword().equals(hash)) {
            if (Cittadino.isValid(psw)) {
                news.append("password|");
                old.setPassword(hash);
            }
            else
                news.append("password not valid|");
        }
        cittadinoRepo.save(old);
        return new ModificaCittadinoDTO(old, news.toString());
    }
}
