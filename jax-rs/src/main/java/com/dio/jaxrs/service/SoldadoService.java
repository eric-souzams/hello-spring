package com.dio.jaxrs.service;

import com.dio.jaxrs.controller.request.SoldadoEditRequest;
import com.dio.jaxrs.dto.Soldado;
import org.springframework.stereotype.Service;

@Service
public class SoldadoService {

    public Soldado getSoldier(String cpf) {
        Soldado soldado = new Soldado();
        soldado.setName("Pedrin do Vrau");
        soldado.setType("Elfo");
        soldado.setArma("Arco e Flecha");
        soldado.setCpf(cpf);

        return soldado;
    }

    public void saveSoldier(Soldado soldado) {

    }

    public void updateSoldier(String cpf, SoldadoEditRequest soldadoEditRequest) {

    }

    public void removeSoldier(String cpf) {

    }
}
