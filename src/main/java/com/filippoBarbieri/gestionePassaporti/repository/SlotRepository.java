package com.filippoBarbieri.gestionePassaporti.repository;


import java.util.List;
import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SlotRepository extends JpaRepository<Slot, LocalDateTime> {
    List<Slot> findAllByDatetimeBetween(LocalDateTime from, LocalDateTime to);
}
