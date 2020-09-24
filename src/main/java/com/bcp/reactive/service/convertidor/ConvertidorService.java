package com.bcp.reactive.service.convertidor;

import io.reactivex.Single;
import com.bcp.reactive.servicedto.response.ConvertidorResponse;
import com.bcp.reactive.webdto.request.ConvertidorWebRequest;

public interface ConvertidorService {

    Single<ConvertidorResponse> convertirMoneda(ConvertidorWebRequest convertidorWebRequest);
}
