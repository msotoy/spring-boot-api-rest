package com.bcp.reactive.service.moneda;

import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.List;

import com.bcp.reactive.servicedto.request.AddMonedaRequest;
import com.bcp.reactive.servicedto.request.UpdateMonedaRequest;
import com.bcp.reactive.servicedto.response.MonedaResponse;

public interface MonedaService {
    Single<String> addMoneda(AddMonedaRequest addMonedaRequest);

    Completable updateMoneda(UpdateMonedaRequest updateMonedaRequest);

    Single<List<MonedaResponse>> getAllMonedas(int limit, int page);

    Single<MonedaResponse> getMonedaDetail(String id);

    Completable deleteMoneda(String id);
}
