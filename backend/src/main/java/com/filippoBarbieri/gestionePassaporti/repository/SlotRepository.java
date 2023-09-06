package com.filippoBarbieri.gestionePassaporti.repository;


import java.util.List;
import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import com.filippoBarbieri.gestionePassaporti.entity.Dipendente;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findAllBySede(Sede sede);
    Slot findFirstByOrderByDatetimeDesc();
    List<Slot> findByCittadinoOrderByDatetimeDesc(Cittadino cittadino);
    List<Slot> findAllByCittadino(Cittadino cittadino);
    List<Slot> findAllByDipendente(Dipendente dipendente);
    List<Slot> findAllBySedeAndDatetime(Sede sede, LocalDateTime from);
    List<Slot> findAllBySedeAndDatetimeBefore(Sede sede, LocalDateTime to);
    List<Slot> findAllBySedeAndDatetimeAfter(Sede sede, LocalDateTime from);
    List<Slot> findAllBySedeAndDatetimeBetween(Sede sede, LocalDateTime from, LocalDateTime to);
}
