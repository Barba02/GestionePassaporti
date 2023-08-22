package com.filippoBarbieri.gestionePassaporti.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;

public class ModificaCittadinoDTO {
    private Cittadino cittadino;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String news;

    public ModificaCittadinoDTO(Cittadino cittadino, String news) {
        this.cittadino = cittadino;
        this.news = (news.isEmpty()) ? null : news.substring(0, news.length()-1);
    }

    public Cittadino getCittadino() {
        return cittadino;
    }

    public void setCittadino(Cittadino cittadino) {
        this.cittadino = cittadino;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}
