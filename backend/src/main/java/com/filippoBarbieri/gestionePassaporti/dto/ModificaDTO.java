package com.filippoBarbieri.gestionePassaporti.dto;


import java.util.List;
import java.lang.reflect.Field;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.filippoBarbieri.gestionePassaporti.entity.Password;

public class ModificaDTO<T> {
    private T obj;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
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
        updated = (updated.isEmpty()) ? "" : updated.substring(0, updated.length() - 1);
    }

    public void modificaPassword(Password newPassword) throws IllegalAccessException {
        if (updated == null)
            updated = "";
        try {
            Field f = obj.getClass().getDeclaredField("password");
            f.setAccessible(true);
            if (newPassword != null && newPassword.isValid()) {
                newPassword.hashPassword();
                if (!newPassword.equals(f.get(obj))) {
                    f.set(obj, newPassword);
                    if (!updated.isEmpty())
                        updated += "|";
                    updated += "password";
                }
            }
        }
        catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }
    }
}
