package com.filippoBarbieri.gestionePassaporti.service;


import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.entity.Prenotazione;
import com.filippoBarbieri.gestionePassaporti.entity.IdPrenotazione;
import com.filippoBarbieri.gestionePassaporti.repository.SlotRepository;
import com.filippoBarbieri.gestionePassaporti.repository.CittadinoRepository;
import com.filippoBarbieri.gestionePassaporti.repository.PrenotazioneRepository;

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

    public Prenotazione getPrenotazione(Cittadino c, Slot s) throws NoSuchElementException {
        IdPrenotazione pid = new IdPrenotazione(c, s);
        if (!prenotazioneRepo.existsById(pid))
            throw new NoSuchElementException("Prenotazione inesistente");
        return prenotazioneRepo.getReferenceById(pid);
    }

    public List<Prenotazione> getListaPrenotazioni(Cittadino c) {
        return prenotazioneRepo.findAllByIdContaining(c);
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
