package com.dio.jacksonandbinder.controller.response;

import org.springframework.hateoas.RepresentationModel;

public class SoldadoListResponse extends RepresentationModel {

    private Long id;
    private String nome;
    private String raca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
