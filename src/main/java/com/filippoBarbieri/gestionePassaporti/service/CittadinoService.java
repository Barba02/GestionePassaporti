package com.filippoBarbieri.gestionePassaporti.service;


import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
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
        if (!anagraficaRepo.existsById(c.getCf()))
            throw new NoSuchElementException("Cittadino non presente nel database");
        if (cittadinoRepo.existsById(c.getCf()))
            throw new DuplicateKeyException("Cittadino già registrato");
        c.setPassword(DigestUtils.sha256Hex(c.getPassword()));
        cittadinoRepo.save(c);
    }

    public void modificaPassword(Cittadino c, String password) throws NoSuchElementException {
        if (!cittadinoRepo.existsById(c.getCf()))
            throw new NoSuchElementException("Cittadino non registrato");
        c.setPassword(DigestUtils.sha256Hex(password));
        cittadinoRepo.save(c);
    }

    public Cittadino getCittadino(String cf) throws NoSuchElementException {
        if (!cittadinoRepo.existsById(cf))
            throw new NoSuchElementException("Cittadino non registrato");
        return cittadinoRepo.getReferenceById(cf);
    }

    /* public void modificaTs(Cittadino c, String ts) {
        if (!cittadinoRepo.existsById(c.getCf()))
            throw new NoSuchElementException("Cittadino non registrato");
        c.setTs(ts);
        cittadinoRepo.save(c);
    }

    public void modificaFlags(Cittadino c, Boolean[] vals) {
        if (!cittadinoRepo.existsById(c.getCf()))
            throw new NoSuchElementException("Cittadino non registrato");
        c.setDi_servizio(vals[0]);
        c.setDiplomatico(vals[1]);
        c.setFigli_minori(vals[2]);
        cittadinoRepo.save(c);
    } */
}
