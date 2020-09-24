package com.bcp.reactive.service.convertidor;

import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcp.reactive.repository.MonedaRepository;
import com.bcp.reactive.servicedto.response.ConvertidorResponse;
import com.bcp.reactive.webdto.request.ConvertidorWebRequest;
import com.bcp.reactive.entity.Moneda;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ConvertidorServiceImpl implements ConvertidorService {
    
    @Autowired
    private MonedaRepository monedaRepository;

    @Override
    public Single<ConvertidorResponse> convertirMoneda(ConvertidorWebRequest convertidorWebResponse) {
        return findConvertidorDetailInRepository(convertidorWebResponse.getMonto(), convertidorWebResponse.getMonedaOrigen(), convertidorWebResponse.getMonedaDestino());
    }

    private Single<ConvertidorResponse> findConvertidorDetailInRepository(double monto, String monedaOrigen, String monedaDestino) {
        return Single.create(singleSubscriber -> {
            Optional<Moneda> optionalMonedaOrigen = monedaRepository.findById(monedaOrigen);
            Optional<Moneda> optionalMonedaDestino = monedaRepository.findById(monedaDestino);
            if (!optionalMonedaOrigen.isPresent() || !optionalMonedaDestino.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
            	double tipoCambioOrigen = optionalMonedaOrigen.get().getTipocambio();
            	double tipoCambioDestino = optionalMonedaDestino.get().getTipocambio();
            	
            	double tipoCambio = tipoCambioDestino / tipoCambioOrigen;
            	double montoConvertido = monto * tipoCambio;
            	
                ConvertidorResponse convertidorResponse = new ConvertidorResponse();
                convertidorResponse.setMontoOriginal(monto);
                convertidorResponse.setMontoConvertido(montoConvertido);
                convertidorResponse.setMonedaOrigen(monedaOrigen);
                convertidorResponse.setMonedaDestino(monedaDestino);
                convertidorResponse.setTipoCambio(tipoCambio);
                
                singleSubscriber.onSuccess(convertidorResponse);
            }
        });
    }
    
}
