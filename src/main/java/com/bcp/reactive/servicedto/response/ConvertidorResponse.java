package com.bcp.reactive.servicedto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConvertidorResponse {
	private double montoOriginal;
    private double montoConvertido;
    private String monedaOrigen;
    private String monedaDestino;
    private double tipoCambio;
}
