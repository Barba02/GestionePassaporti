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
        if (!Cittadino.isValid(c.getPassword()))
            throw new IllegalArgumentException("La password non rispetta i parametri di sicurezza");
        c.setPassword(Cittadino.hashPassword(c.getPassword()));
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
        mod.modifica(List.of(new String[]{"ts", "figli_minori", "diplomatico", "di_servizio", "cie", "passaporto", "scadenza_passaporto"}), c);
        Cittadino old = mod.getObj();
        String psw = c.getPassword();
        String hash = Cittadino.hashPassword(psw);
        if (psw != null && !old.getPassword().equals(hash)) {
            if (Cittadino.isValid(psw)) {
                mod.setUpdated(mod.getUpdated() + "|password");
                old.setPassword(hash);
            }
            else
                mod.setUpdated(mod.getUpdated() + "|password not valid");
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
