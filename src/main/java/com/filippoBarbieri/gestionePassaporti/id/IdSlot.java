package com.filippoBarbieri.gestionePassaporti.id;


import java.util.Objects;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
public class IdSlot implements Serializable {
    private LocalDateTime datetime;
    @Enumerated(EnumType.STRING)
    private Sede sede;

    public IdSlot() {}

    public IdSlot(LocalDateTime datetime, Sede sede) {
        this.datetime = datetime;
        this.sede = sede;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdSlot idSlot = (IdSlot) o;
        return Objects.equals(datetime, idSlot.datetime) && sede == idSlot.sede;
    }

    @Override
    public int hashCode() {
        return Objects.hash(datetime, sede);
    }
}