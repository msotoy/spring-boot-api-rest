package com.bcp.reactive.webdto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonedaWebResponse {
	private String codigo;
    private String nombre;
    private String simbolo;
    private double tipocambio;
    private int estado;
}
