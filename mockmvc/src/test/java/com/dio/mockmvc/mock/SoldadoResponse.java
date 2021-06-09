package com.dio.mockmvc.mock;

import com.dio.mockmvc.dto.Soldado;
public class SoldadoResponse {
    public static Soldado create(){
        Soldado soldado = new Soldado();
        soldado.setArma("Espada");
        soldado.setIdade(24);
        soldado.setNome("João");
        soldado.setRaca("Humano");
        soldado.setStatus("vivo");

        return soldado;
    }
}