package com.filippoBarbieri.gestionePassaporti.service;


import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.repository.SlotRepository;

@Service
@Transactional
public class SlotService {
    @Autowired
    private SlotRepository slotRepo;

    public void inserisciSlot(Slot s) throws DuplicateKeyException {
        if (slotRepo.existsById(s.getDatetime()))
            throw new DuplicateKeyException("Slot già inserito");
        slotRepo.save(s);
    }

    public void eliminaSlot(Slot s) throws NoSuchElementException {
        if (!slotRepo.existsById(s.getDatetime()))
            throw new NoSuchElementException("Slot inesistente");
        slotRepo.delete(s);
    }

    /* public void modificaStato(Slot s, Stato st) throws NoSuchElementException {
        if (slotRepo.findByDataAndOra(s.getData(), s.getOra()).isEmpty())
            throw new NoSuchElementException("Prenotazione inesistente");
        s.setStato(st);
        slotRepo.save(s);
    }

    public void modificaTipo(Slot s, Tipo t) throws NoSuchElementException {
        if (slotRepo.findByDataAndOra(s.getData(), s.getOra()).isEmpty())
            throw new NoSuchElementException("Prenotazione inesistente");
        s.setTipo(t);
        slotRepo.save(s);
    } */
}
