package com.filippoBarbieri.gestionePassaporti.dto;


import java.util.List;
import java.lang.reflect.Field;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ModificaDTO<T> {
    private T obj;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updated;

    public ModificaDTO(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void modifica(List<String> modifiables, T newObj) throws IllegalAccessException {
        updated = "";
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (modifiables.contains(f.getName())) {
                if (f.get(newObj) != null && !f.get(newObj).equals(f.get(obj))) {
                    updated += f.getName() + "|";
                    f.set(obj, f.get(newObj));
                }
            }
        }
        updated = (updated.isEmpty()) ? null : updated.substring(0, updated.length() - 1);
    }
}
