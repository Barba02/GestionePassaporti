package com.filippoBarbieri.gestionePassaporti.repository;


import java.sql.Date;
import java.sql.Time;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {
    Optional<Slot> findByDataAndOra(Date data, Time ora);
}
