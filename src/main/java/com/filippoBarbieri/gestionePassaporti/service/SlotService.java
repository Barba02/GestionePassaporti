package com.filippoBarbieri.gestionePassaporti.service;


import java.util.List;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.enums.Tipo;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.enums.Stato;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.dto.ModificaDTO;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.repository.SlotRepository;
import com.filippoBarbieri.gestionePassaporti.repository.CittadinoRepository;

@Service
@Transactional
public class SlotService {
    @Autowired
    private SlotRepository slotRepo;
    @Autowired
    private CittadinoRepository cittadinoRepo;

    public void inserisciSlot(Slot s) throws DuplicateKeyException, IllegalArgumentException {
        if (!s.getDatetime().isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Impossibile inserire slot nel passato");
        if (!Arrays.asList(Sede.values()).contains(s.getSede()))
            throw new IllegalArgumentException("Sede inesistente");
        if (!Arrays.asList(Tipo.values()).contains(s.getTipo()))
            throw new IllegalArgumentException("Tipo inesistente");
        if (!Arrays.asList(Stato.values()).contains(s.getStato()))
            throw new IllegalArgumentException("Stato inesistente");
        slotRepo.save(s);
    }

    public void eliminaSlot(Long id) throws NoSuchElementException {
        slotRepo.delete(getSlot(id));
    }

    public Slot getSlot(Long id) throws NoSuchElementException {
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

    public ModificaDTO<Slot> modificaSlot(Long id, Slot s) throws NoSuchElementException, IllegalAccessException, IllegalArgumentException {
        s.setCittadino(cittadinoRepo
                .findByAnagrafica_Cf(s.getCittadino()
                        .getAnagrafica()
                        .getCf())
                .orElse(null));
        ModificaDTO<Slot> mod = new ModificaDTO<>(getSlot(id));
        mod.modifica(List.of(new String[]{"tipo", "stato", "cittadino"}), s);
        slotRepo.save(mod.getObj());
        return mod;
    }

    public List<Slot> getSlotsNumber(String s, LocalDateTime dt, String st) throws IllegalArgumentException {
        Sede sede;
        try {
            sede = Sede.valueOf(s);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Sede inesistente");
        }
        if (st != null) {
            Stato stato;
            try {
                 stato = Stato.valueOf(st);
            }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Tipo inesistente");
            }
            return slotRepo.findAllByDatetimeAndSedeAndStato(dt, sede, stato);
        }
        return slotRepo.findAllByDatetimeAndSede(dt, sede);
    }
}
