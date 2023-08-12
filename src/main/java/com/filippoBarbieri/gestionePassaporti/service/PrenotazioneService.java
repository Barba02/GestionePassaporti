package com.filippoBarbieri.gestionePassaporti.service;


import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import com.filippoBarbieri.gestionePassaporti.entity.IdPrenotazione;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.entity.Prenotazione;
import com.filippoBarbieri.gestionePassaporti.repository.SlotRepository;
import com.filippoBarbieri.gestionePassaporti.repository.CittadinoRepository;
import com.filippoBarbieri.gestionePassaporti.repository.PrenotazioneRepository;

import java.util.NoSuchElementException;

@Service
@Transactional
public class PrenotazioneService {
    @Autowired
    private SlotRepository slotRepo;
    @Autowired
    private CittadinoRepository cittadinoRepo;
    @Autowired
    private PrenotazioneRepository prenotazioneRepo;

    public void inserisciPrenotazione(Cittadino c, Slot s) throws DuplicateKeyException {
        IdPrenotazione pid = new IdPrenotazione(c, s);
        if (prenotazioneRepo.existsById(pid))
            throw new DuplicateKeyException("Prenotazione già inserita");
        prenotazioneRepo.save(new Prenotazione(pid));
    }

    public void eliminaPrenotazione(Cittadino c, Slot s) throws NoSuchElementException {
        IdPrenotazione pid = new IdPrenotazione(c, s);
        if (!prenotazioneRepo.existsById(pid))
            throw new NoSuchElementException("Prenotazione inesistente");
        Prenotazione p = prenotazioneRepo.getReferenceById(pid);
        prenotazioneRepo.delete(p);
    }

    /* implementabile come inserisci+elimina
    public Prenotazione cambiaSlot(Cittadino c, Slot oldSlot, Slot newSlot) throws Exception {
        IdPrenotazione pid = new IdPrenotazione(c, oldSlot);
        if (!prenotazioneRepo.existsById(pid))
            throw new NoSuchElementException("Prenotazione inesistente");
        pid = new IdPrenotazione(c, newSlot);
        if (prenotazioneRepo.existsById(pid))
            throw new DuplicateKeyException("Prenotazione già inserita");
        Prenotazione p = prenotazioneRepo.getReferenceById(pid);
        p.setId(pid);
        prenotazioneRepo.save(p);
        return p;
    } */
}
