package com.bcp.reactive.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "moneda")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Moneda {
    @Id
    @Column
    private String codigo;

    @Column
    private String nombre;
    
    @Column
    private String simbolo;
    
    @Column
    private double tipocambio;
    
    @Column
    private int estado;
}
