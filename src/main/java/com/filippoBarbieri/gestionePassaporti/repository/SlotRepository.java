package com.filippoBarbieri.gestionePassaporti.repository;


import java.util.List;
import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;
import com.filippoBarbieri.gestionePassaporti.entity.IdSlot;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SlotRepository extends JpaRepository<Slot, IdSlot> {
    List<Slot> findAllBySede(Sede sede);
    List<Slot> findAllBySedeAndDatetimeAfter(Sede sede, LocalDateTime from);
    List<Slot> findAllBySedeAndDatetimeBefore(Sede sede, LocalDateTime to);
    List<Slot> findAllBySedeAndDatetimeBetween(Sede sede, LocalDateTime from, LocalDateTime to);
}
