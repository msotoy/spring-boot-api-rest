package com.bcp.reactive.webdto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConvertidorWebResponse {
	private double montoOriginal;
    private double montoConvertido;
    private String monedaOrigen;
    private String monedaDestino;
    private double tipoCambio;
}
