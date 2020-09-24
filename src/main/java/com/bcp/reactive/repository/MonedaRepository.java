package com.bcp.reactive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcp.reactive.entity.Moneda;

@Repository
public interface MonedaRepository extends JpaRepository<Moneda, String> {
    
}
