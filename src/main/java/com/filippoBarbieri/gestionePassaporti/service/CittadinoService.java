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

    public void inserisciCittadino(Cittadino c) throws NoSuchElementException, DuplicateKeyException {
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

    public void modificaFlags(Cittadino c, Boolean[] vals) {
        if (!cittadinoRepo.existsById(c.getCf()))
            throw new NoSuchElementException("Cittadino non registrato");
        /*TODO: termina*/
    }
}
