package com.bcp.reactive.servicedto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonedaResponse {
    private String codigo;
    private String nombre;
    private String simbolo;
    private double tipocambio;
    private int estado;
}
