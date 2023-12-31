package com.filippoBarbieri.gestionePassaporti.service;


import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.security.SecureRandom;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.enums.Tipo;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.enums.Stato;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.dto.ModificaDTO;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.entity.Dipendente;
import com.filippoBarbieri.gestionePassaporti.repository.SlotRepository;
import com.filippoBarbieri.gestionePassaporti.repository.CittadinoRepository;
import com.filippoBarbieri.gestionePassaporti.repository.DipendenteRepository;

@Service
@Transactional
public class SlotService {
    @Autowired
    private SlotRepository slotRepo;
    @Autowired
    private CittadinoRepository cittadinoRepo;
    @Autowired
    private DipendenteRepository dipendenteRepo;

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

    public List<Slot> getSlots(String s, String st, String t, LocalDateTime from, LocalDateTime to) throws IllegalArgumentException {
        List<Slot> lista;
        try {
            Sede sede = Sede.valueOf(s.toUpperCase());
            if (from != null && to != null)
                lista = slotRepo.findAllBySedeAndDatetimeBetween(sede, from, to);
            else if (from != null) {
                lista = slotRepo.findAllBySedeAndDatetime(sede, from);
                lista.addAll(slotRepo.findAllBySedeAndDatetimeAfter(sede, from));
            }
            else if (to != null) {
                lista = slotRepo.findAllBySedeAndDatetimeBefore(sede, to);
                lista.addAll(slotRepo.findAllBySedeAndDatetime(sede, to));
            }
            else
                lista = slotRepo.findAllBySede(sede);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Sede non valida");
        }
        if (st != null) {
            try {
                Stato stato = Stato.valueOf(st.toUpperCase());
                lista = lista.stream().filter(el -> stato.equals(el.getStato())).collect(Collectors.toList());
            }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Stato non valido");
            }
        }
        if (t != null) {
            try {
                Tipo tipo = Tipo.valueOf(t.toUpperCase());
                lista = lista.stream().filter(el -> tipo.equals(el.getTipo())).collect(Collectors.toList());
            }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Tipo non valido");
            }
        }
        return lista;
    }

    public ModificaDTO<Slot> modificaSlot(Long id, Slot s) throws NoSuchElementException, IllegalAccessException, IllegalArgumentException {
        boolean sentinella = false;
        if (s.getCittadino() != null && s.getCittadino().getAnagrafica() == null) {
            sentinella = true;
            s.setCittadino(null);
        }
        else {
            if (s.getCittadino() != null)
                s.setCittadino(cittadinoRepo.findByAnagrafica_Cf(
                                s.getCittadino().getAnagrafica().getCf())
                        .orElse(null));
        }
        ModificaDTO<Slot> mod = new ModificaDTO<>(getSlot(id));
        mod.modifica(List.of(new String[]{"tipo", "stato", "cittadino", "dipendente"}), s);
        if (sentinella)
            mod.getObj().setCittadino(null);
        slotRepo.save(mod.getObj());
        return mod;
    }

    public List<Sede> getListaSedi(String t, String st) {
        List<Sede> list = new ArrayList<>();
        for (Sede s : Sede.values())
            list.addAll(getSlots(s.toString(), st, t, null, null)
                    .stream()
                    .map(Slot::getSede)
                    .toList());
        return list.stream().distinct().toList();
    }

    private static final LocalTime[] slotTimes = {
            LocalTime.of(8, 0), LocalTime.of(8, 30),
            LocalTime.of(9, 0), LocalTime.of(9, 30),
            LocalTime.of(10, 0), LocalTime.of(10, 30),
            LocalTime.of(11, 0), LocalTime.of(11, 30),
            LocalTime.of(14, 0), LocalTime.of(14, 30),
            LocalTime.of(15, 0), LocalTime.of(15, 30),
            LocalTime.of(16, 0), LocalTime.of(16, 30),
            LocalTime.of(17, 0), LocalTime.of(17, 30)
    };
    public void salvaSlot(LocalDate giorno) {
        for (Map.Entry<Sede, List<Dipendente>> entry : dipendenteRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(Dipendente::getSede))
                .entrySet()) {
            for (Dipendente d : entry.getValue()) {
                if (d.getDisponibilita().contains(giorno.getDayOfWeek().name())) {
                    for (LocalTime t : slotTimes) {
                        LocalDateTime dt = giorno.atTime(t);
                        slotRepo.save(new Slot(dt, entry.getKey(), d));
                    }
                }
            }
        }
    }
    @Scheduled(fixedRate = 86400000)
    public void generaSlot() {
        Slot s = slotRepo.findFirstByOrderByDatetimeDesc();
        if (s == null) {
            LocalDate domani = LocalDate.now().plusDays(1);
            for (int i = 0; i < 7; i++) {
                salvaSlot(domani);
                domani = domani.plusDays(1);
            }
        }
        else {
            LocalDate settimanaProx = LocalDate.now().plusDays(7);
            while (settimanaProx.isAfter(s.getDatetime().toLocalDate())) {
                salvaSlot(settimanaProx);
                settimanaProx = settimanaProx.minusDays(1);
            }
        }
    }

    @Scheduled(fixedRate = 7200000)
    public void chiudiSlotPassati() {
        Slot fake = new Slot();
        fake.setStato(Stato.CHIUSO);
        for (Sede s : Sede.values()) {
            List<Slot> slotPassati = slotRepo.findAllBySedeAndDatetimeBefore(s, LocalDateTime.now().minusMinutes(30));
            slotPassati.forEach(slot -> {
                try {
                    if (slot.getTipo() == Tipo.RILASCIO) {
                        Cittadino c = slot.getCittadino();
                        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                        StringBuilder stringaCasuale = new StringBuilder();
                        SecureRandom random = new SecureRandom();
                        for (int i = 0; i < 9; i++)
                            stringaCasuale.append(chars.charAt(random.nextInt(chars.length())));
                        c.setPassaporto(String.valueOf(stringaCasuale));
                        c.setScadenza_passaporto(LocalDate.now().plusYears(10));
                        cittadinoRepo.save(c);
                    }
                    else if (slot.getTipo() == Tipo.RINNOVO) {
                        Cittadino c = slot.getCittadino();
                        c.setScadenza_passaporto(c.getScadenza_passaporto().plusYears(10));
                        cittadinoRepo.save(c);
                    }
                    modificaSlot(slot.getId(), fake);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        }
    }
}
