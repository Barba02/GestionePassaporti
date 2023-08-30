package com.filippoBarbieri.gestionePassaporti.entity;


import java.util.Objects;
import jakarta.persistence.Embeddable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.codec.digest.DigestUtils;

@Embeddable
public class Password {
    private String password;

    public Password() {}

    public Password(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public void hide() {
        password = "*".repeat(16);
    }

    @JsonIgnore
    public void hashPassword() {
        this.password = DigestUtils.sha256Hex(password);
    }

    @JsonIgnore
    public boolean isValid() {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()-[{}]:;',?/*~$^+=<>]).{8,}$");
    }

    @Override
    @JsonIgnore
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }
}
