package com.filippoBarbieri.gestionePassaporti.service;


import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.dto.ModificaDTO;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.repository.SlotRepository;
import com.filippoBarbieri.gestionePassaporti.repository.CittadinoRepository;
import com.filippoBarbieri.gestionePassaporti.repository.AnagraficaRepository;

@Service
@Transactional
public class CittadinoService {
    @Autowired
    private SlotRepository slotRepo;
    @Autowired
    private CittadinoRepository cittadinoRepo;
    @Autowired
    private AnagraficaRepository anagraficaRepo;

    public void registraCittadino(Cittadino c) throws NoSuchElementException, DuplicateKeyException, IllegalArgumentException {
        if (!anagraficaRepo.existsById(c.getAnagrafica().getCf()))
            throw new NoSuchElementException("Cittadino non presente nel database");
        if (cittadinoRepo.existsByAnagrafica_Cf(c.getAnagrafica().getCf()))
            throw new DuplicateKeyException("Cittadino già registrato");
        if (!c.getPassword().isValid())
            throw new IllegalArgumentException("La password non rispetta i parametri di sicurezza");
        c.getPassword().hashPassword();
        cittadinoRepo.save(c);
    }

    public Cittadino getCittadino(String cf) throws NoSuchElementException {
        Cittadino c = cittadinoRepo.findByAnagrafica_Cf(cf).orElse(null);
        if (c == null)
            throw new NoSuchElementException("Cittadino non registrato");
        return c;
    }

    public ModificaDTO<Cittadino> modificaCittadino(String cf, Cittadino c) throws NoSuchElementException, IllegalAccessException {
        ModificaDTO<Cittadino> mod = new ModificaDTO<>(getCittadino(cf));
        mod.modifica(List.of(new String[]{"figli_minori", "diplomatico", "cie", "passaporto", "scadenza_passaporto"}), c);
        Cittadino old = mod.getObj();
        if (c.getPassword() != null && c.getPassword().isValid()) {
            c.getPassword().hashPassword();
            if (!c.getPassword().equals(old.getPassword())) {
                old.setPassword(c.getPassword());
                mod.setUpdated(mod.getUpdated() + "|password");
            }
        }
        cittadinoRepo.save(old);
        return mod;
    }

    public List<Slot> getSlots(String cf) throws NoSuchElementException {
        if (!cittadinoRepo.existsByAnagrafica_Cf(cf))
            throw new NoSuchElementException("Cittadino non registrato");
        List<Slot> l = slotRepo.findAllByCittadino_Anagrafica_Cf(cf);
        l.stream().parallel().forEach(s -> s.setCittadino(null));
        return l;
    }
}
