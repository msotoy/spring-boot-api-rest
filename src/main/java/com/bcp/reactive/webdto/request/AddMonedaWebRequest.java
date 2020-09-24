package com.bcp.reactive.webdto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddMonedaWebRequest {
    private String codigo;
    private String nombre;
    private String simbolo;
    private double tipocambio;
    private int estado;
}
