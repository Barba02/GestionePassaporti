package com.filippoBarbieri.gestionePassaporti.service;


import java.util.List;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.id.IdSlot;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.dto.ModificaDTO;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.repository.SlotRepository;

@Service
@Transactional
public class SlotService {
    @Autowired
    private SlotRepository slotRepo;

    public void inserisciSlot(Slot s) throws DuplicateKeyException {
        if (!s.getDatetime().isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Impossibile inserire slot nel passato");
        if (slotRepo.existsById(s.getId()))
            throw new DuplicateKeyException("Slot già inserito");
        slotRepo.save(s);
    }

    public void eliminaSlot(IdSlot id) throws NoSuchElementException {
        slotRepo.delete(getSlot(id));
    }

    public Slot getSlot(IdSlot id) throws NoSuchElementException {
        Slot s = slotRepo.findById(id).orElse(null);
        if (s == null)
            throw new NoSuchElementException("Slot inesistente");
        return s;
    }

    public List<Slot> getSlots(String s, LocalDateTime from, LocalDateTime to) throws IllegalArgumentException {
        Sede sede = Sede.valueOf(s);
        if (from != null && to != null)
            return slotRepo.findAllBySedeAndDatetimeBetween(sede, from, to);
        if (from != null)
            return slotRepo.findAllBySedeAndDatetimeAfter(sede, from);
        if (to != null)
            return slotRepo.findAllBySedeAndDatetimeBefore(sede, to);
        return slotRepo.findAllBySede(sede);
    }

    public ModificaDTO<Slot> modificaSlot(IdSlot id, Slot s) throws NoSuchElementException, IllegalAccessException {
        ModificaDTO<Slot> mod = new ModificaDTO<>(getSlot(id));
        mod.modifica(List.of(new String[]{"tipo", "stato"}), s);
        slotRepo.save(mod.getObj());
        return mod;
    }
}
