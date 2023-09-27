package com.filippoBarbieri.gestionePassaporti.service;


import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.enums.Tipo;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Notifica;
import org.springframework.transaction.annotation.Transactional;
import com.filippoBarbieri.gestionePassaporti.repository.NotificaRepository;

@Service
@Transactional
public class NotificaService {
    @Autowired
    private NotificaRepository notificaRepo;
    @Autowired
    private CittadinoService cittadinoService;
    @Autowired
    private SlotService slotService;

    public void inserisciNotifica(Notifica n) throws NoSuchElementException, IllegalArgumentException {
        try {
            n.setSede(Sede.valueOf(n.getSede().toString().toUpperCase()));
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Sede non valida");
        }
        try {
            n.setTipo(Tipo.valueOf(n.getTipo().toString().toUpperCase()));
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo non valida");
        }
        n.setCittadino(cittadinoService.getCittadino(n.getCittadino().getAnagrafica().getCf()));
        notificaRepo.save(n);
    }

    public boolean verifica(Long id) {
        Notifica n = notificaRepo.getReferenceById(id);
        List<Slot> list = slotService.getSlots(n.getSede().toString(),
                "Libero",
                n.getTipo().toString(),
                n.getInizio() != null ? n.getInizio().atStartOfDay() : null,
                n.getFine() != null ? n.getFine().atStartOfDay() : null);
        return !list.isEmpty();
    }
}
