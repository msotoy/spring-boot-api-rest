package com.bcp.reactive.service.moneda;

import io.reactivex.Completable;
import io.reactivex.Single;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bcp.reactive.entity.Moneda;
import com.bcp.reactive.repository.MonedaRepository;
import com.bcp.reactive.servicedto.request.AddMonedaRequest;
import com.bcp.reactive.servicedto.request.UpdateMonedaRequest;
import com.bcp.reactive.servicedto.response.MonedaResponse;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonedaServiceImpl implements MonedaService {

    @Autowired
    private MonedaRepository monedaRepository;

    @Override
    public Single<String> addMoneda(AddMonedaRequest addMonedaRequest) {
        return saveMonedaToRepository(addMonedaRequest);
    }

    private Single<String> saveMonedaToRepository(AddMonedaRequest addMonedaRequest) {
        return Single.create(singleSubscriber -> {
            Optional<Moneda> optionalMoneda = monedaRepository.findById(addMonedaRequest.getCodigo());
            if (optionalMoneda.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                String addedMonedaCode = monedaRepository.save(toMoneda(addMonedaRequest)).getCodigo();
                singleSubscriber.onSuccess(addedMonedaCode);
            }
        });
    }

    private Moneda toMoneda(AddMonedaRequest addMonedaRequest) {
        Moneda moneda = new Moneda();
        BeanUtils.copyProperties(addMonedaRequest, moneda);
        return moneda;
    }

    @Override
    public Completable updateMoneda(UpdateMonedaRequest updateMonedaRequest) {
        return updateMonedaToRepository(updateMonedaRequest);
    }

    private Completable updateMonedaToRepository(UpdateMonedaRequest updateMonedaRequest) {
        return Completable.create(completableSubscriber -> {
            Optional<Moneda> optionalMoneda = monedaRepository.findById(updateMonedaRequest.getCodigo());
            if (!optionalMoneda.isPresent())
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                Moneda moneda = optionalMoneda.get();
                moneda.setNombre(updateMonedaRequest.getNombre());
                moneda.setSimbolo(updateMonedaRequest.getSimbolo());
                moneda.setTipocambio(updateMonedaRequest.getTipocambio());
                moneda.setEstado(updateMonedaRequest.getEstado());
                monedaRepository.save(moneda);
                completableSubscriber.onComplete();
            }
        });
    }

    @Override
    public Single<List<MonedaResponse>> getAllMonedas(int limit, int page) {
        return findAllMonedasInRepository(limit, page)
                .map(this::toMonedaResponseList);
    }

    private Single<List<Moneda>> findAllMonedasInRepository(int limit, int page) {
        return Single.create(singleSubscriber -> {
            List<Moneda> monedas = monedaRepository.findAll(PageRequest.of(page, limit)).getContent();
            singleSubscriber.onSuccess(monedas);
        });
    }

    private List<MonedaResponse> toMonedaResponseList(List<Moneda> monedaList) {
        return monedaList
                .stream()
                .map(this::toMonedaResponse)
                .collect(Collectors.toList());
    }

    private MonedaResponse toMonedaResponse(Moneda moneda) {
        MonedaResponse monedaResponse = new MonedaResponse();
        BeanUtils.copyProperties(moneda, monedaResponse);
        return monedaResponse;
    }

    @Override
    public Single<MonedaResponse> getMonedaDetail(String codigo) {
        return findMonedaDetailInRepository(codigo);
    }

    private Single<MonedaResponse> findMonedaDetailInRepository(String codigo) {
        return Single.create(singleSubscriber -> {
            Optional<Moneda> optionalMoneda = monedaRepository.findById(codigo);
            if (!optionalMoneda.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                MonedaResponse monedaResponse = toMonedaResponse(optionalMoneda.get());
                singleSubscriber.onSuccess(monedaResponse);
            }
        });
    }

    @Override
    public Completable deleteMoneda(String codigo) {
        return deleteMonedaInRepository(codigo);
    }

    private Completable deleteMonedaInRepository(String codigo) {
        return Completable.create(completableSubscriber -> {
            Optional<Moneda> optionalMoneda = monedaRepository.findById(codigo);
            if (!optionalMoneda.isPresent())
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                monedaRepository.delete(optionalMoneda.get());
                completableSubscriber.onComplete();
            }
        });
    }
}
