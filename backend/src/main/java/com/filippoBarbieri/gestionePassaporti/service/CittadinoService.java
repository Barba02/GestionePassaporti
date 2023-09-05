package com.filippoBarbieri.gestionePassaporti.service;


import java.time.LocalDate;
import java.util.*;

import com.filippoBarbieri.gestionePassaporti.enums.Stato;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.enums.Tipo;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.dto.ModificaDTO;
import com.filippoBarbieri.gestionePassaporti.entity.Password;
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
    @Autowired
    private SlotService slotService;

    public void registraCittadino(Cittadino c) throws NoSuchElementException, DuplicateKeyException, IllegalArgumentException {
        if (!anagraficaRepo.existsById(c.getAnagrafica().getCf()))
            throw new NoSuchElementException("Cittadino inesistente");
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
        mod.modificaPassword(c.getPassword());
        cittadinoRepo.save(mod.getObj());
        return mod;
    }

    public Cittadino login(String cf, String password) throws NoSuchElementException, IllegalArgumentException {
        Cittadino c = getCittadino(cf.toUpperCase());
        Password psw = new Password(password);
        psw.hashPassword();
        if (!psw.equals(c.getPassword()))
            throw new IllegalArgumentException("Password errata");
        return c;
    }

    public List<Slot> getSlots(String cf) throws NoSuchElementException {
        return slotRepo.findAllByCittadino(getCittadino(cf));
    }

    public Slot riservaSlot(Long id, String cf) throws IllegalAccessException, IllegalStateException {
        Slot s = slotService.getSlot(id);
        Tipo tipo = s.getTipo();
        Cittadino c = getCittadino(cf);
        List<Slot> listaOrdinata = slotRepo.findByCittadinoOrderByDatetimeDesc(c);
        Slot last = (listaOrdinata.isEmpty()) ? null : listaOrdinata.get(0);
        if (!s.getStato().equals(Stato.LIBERO))
            throw new IllegalStateException("Non è possibile prenotare uno slot non libero");
        if (tipo.equals(Tipo.RITIRO) && (last == null || last.getTipo().equals(Tipo.RITIRO) || !last.getStato().equals(Stato.CHIUSO) || last.getDatetime().isAfter(s.getDatetime().minusDays(30))))
           throw new IllegalStateException("Prima di un ritiro è necessario richiedere un rinnovo/rilascio");
        if (!tipo.equals(Tipo.RITIRO) && last != null && (!last.getTipo().equals(Tipo.RITIRO) || !last.getStato().equals(Stato.CHIUSO)))
            throw new IllegalStateException("Non è possibile prenotare due rinnovo/rilascio consecutivi");
        if (tipo.equals(Tipo.RILASCIO) && c.getPassaporto() != null)
            throw new IllegalStateException("Il cittadino è già in possesso di un passaporto");
        if (tipo.equals(Tipo.RINNOVO) && c.getScadenza_passaporto().isBefore(s.getDatetime().toLocalDate().minusMonths(6)))
            throw new IllegalStateException("Non è possibile richiedere rinnovo se mancano più di sei mesi dalla scadenza");
        s.setCittadino(c);
        s.setStato(Stato.OCCUPATO);
        return slotService.modificaSlot(id, s).getObj();
    }
}
