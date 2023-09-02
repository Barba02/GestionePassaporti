package com.filippoBarbieri.gestionePassaporti.service;


import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import com.filippoBarbieri.gestionePassaporti.dto.ModificaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Password;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.entity.Dipendente;
import com.filippoBarbieri.gestionePassaporti.repository.SlotRepository;
import com.filippoBarbieri.gestionePassaporti.repository.DipendenteRepository;

@Service
@Transactional
public class DipendenteService {
    @Autowired
    private SlotRepository slotRepo;
    @Autowired
    private DipendenteRepository dipendenteRepo;

    public Dipendente getDipendente(String username) throws NoSuchElementException {
        Dipendente d = dipendenteRepo.findById(username).orElse(null);
        if (d == null)
            throw new NoSuchElementException("Dipendente inesistente");
        return d;
    }

    public Dipendente login(String username, String password) throws NoSuchElementException, IllegalArgumentException {
        Dipendente d = getDipendente(username.toLowerCase());
        Password psw = new Password(password);
        psw.hashPassword();
        if (!psw.equals(d.getPassword()))
            throw new IllegalArgumentException("Password errata");
        return d;
    }

    public ModificaDTO<Dipendente> modificaDipendente(String username, Dipendente d) throws NoSuchElementException, IllegalAccessException {
        ModificaDTO<Dipendente> mod = new ModificaDTO<>(getDipendente(username));
        mod.modifica(List.of(new String[]{"sede", "disponibilita"}), d);
        mod.modificaPassword(d.getPassword());
        dipendenteRepo.save(mod.getObj());
        return mod;
    }

    public List<Slot> getSlots(String username) throws NoSuchElementException {
        return slotRepo.findAllByDipendente(getDipendente(username));
    }
}
