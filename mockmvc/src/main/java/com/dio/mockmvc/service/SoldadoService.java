package com.dio.mockmvc.service;

import com.dio.mockmvc.dto.Soldado;
import org.springframework.stereotype.Service;

@Service
public class SoldadoService {

    public Soldado getSoldado(){
        return new Soldado();
    }

    public void salvarSoldado(Soldado soldado){
    }

}