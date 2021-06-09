package com.dio.jaxrs.controller;

import com.dio.jaxrs.controller.request.SoldadoEditRequest;
import com.dio.jaxrs.dto.Soldado;
import com.dio.jaxrs.service.SoldadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/soldado")
public class SoldadoController {

    private SoldadoService soldadoService;

    public SoldadoController(SoldadoService soldadoService) {
        this.soldadoService = soldadoService;
    }

    @GetMapping(value = "/{cpf}")
    public ResponseEntity<Soldado> getById(@PathVariable("cpf") String cpf) {
        Soldado soldier = soldadoService.getSoldier(cpf);

        return ResponseEntity.status(HttpStatus.OK).body(soldier);
    }

    @PostMapping
    public ResponseEntity<Void> createSoldier(@RequestBody Soldado soldier) {
        soldadoService.saveSoldier(soldier);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{cpf}")
    public ResponseEntity<Soldado> editSoldier(@PathVariable("cpf") String cpf,
                                               @RequestBody SoldadoEditRequest soldadoEditRequest) {
        soldadoService.updateSoldier(cpf, soldadoEditRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{cpf}")
    public ResponseEntity<Void> deleteSoldier(@PathVariable("cpf") String cpf) {
        soldadoService.removeSoldier(cpf);

        return ResponseEntity.ok().build();
    }

}
