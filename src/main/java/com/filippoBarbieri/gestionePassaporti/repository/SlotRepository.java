package com.filippoBarbieri.gestionePassaporti.repository;


import org.springframework.stereotype.Repository;
import com.filippoBarbieri.gestionePassaporti.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {
}
