package com.dio.jacksonandbinder.repository;

import com.dio.jacksonandbinder.entities.SoldadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldadoRepository extends JpaRepository<SoldadoEntity, Long> {



}
