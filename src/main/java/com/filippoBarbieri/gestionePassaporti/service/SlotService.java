package com.filippoBarbieri.gestionePassaporti.service;


import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import com.filippoBarbieri.gestionePassaporti.enums.Tipo;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.enums.Stato;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.dto.ModificaDTO;
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

    public List<Slot> getSlots(String s, LocalDateTime from, LocalDateTime to) throws IllegalArgumentException {
        Sede sede = Sede.valueOf(s.toUpperCase());
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
        mod.modifica(List.of(new String[]{"tipo", "stato", "cittadino", "dipendente"}), s);
        slotRepo.save(mod.getObj());
        return mod;
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
}
