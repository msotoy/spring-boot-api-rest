package com.bcp.reactive.web;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bcp.reactive.service.moneda.MonedaService;
import com.bcp.reactive.servicedto.request.AddMonedaRequest;
import com.bcp.reactive.servicedto.request.UpdateMonedaRequest;
import com.bcp.reactive.servicedto.response.MonedaResponse;
import com.bcp.reactive.webdto.request.UpdateMonedaWebRequest;
import com.bcp.reactive.webdto.response.BaseWebResponse;
import com.bcp.reactive.webdto.response.MonedaWebResponse;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/moneda")
public class MonedaRestController {

    @Autowired
    private MonedaService monedaService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    ) public Single<ResponseEntity<BaseWebResponse>> addMoneda(
        @RequestBody AddMonedaRequest addMonedaRequest) {
        return monedaService.addMoneda(addMonedaRequest).subscribeOn(Schedulers.io()).map(
            s -> ResponseEntity.created(URI.create("/api/moneda/" + s))
                .body(BaseWebResponse.successWithData(s)));
    }

    @PutMapping(
            value = "/{codigo}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> updateMoneda(@PathVariable(value = "codigo") String codigo,
                                                              @RequestBody UpdateMonedaWebRequest updateMonedaWebRequest) {
        return monedaService.updateMoneda(toUpdateMonedaRequest(codigo, updateMonedaWebRequest))
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }

    private UpdateMonedaRequest toUpdateMonedaRequest(String codigo, UpdateMonedaWebRequest updateMonedaWebRequest) {
        UpdateMonedaRequest updateMonedaRequest = new UpdateMonedaRequest();
        BeanUtils.copyProperties(updateMonedaWebRequest, updateMonedaRequest);
        updateMonedaRequest.setCodigo(codigo);
        return updateMonedaRequest;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse<List<MonedaWebResponse>>>> getAllMonedas(@RequestParam(value = "limit", defaultValue = "50") int limit,
                                                                                      @RequestParam(value = "page", defaultValue = "0") int page) {
        return monedaService.getAllMonedas(limit, page)
                .subscribeOn(Schedulers.io())
                .map(monedaResponses -> ResponseEntity.ok(BaseWebResponse.successWithData(toMonedaWebResponseList(monedaResponses))));
    }

    private List<MonedaWebResponse> toMonedaWebResponseList(List<MonedaResponse> monedaResponseList) {
        return monedaResponseList
                .stream()
                .map(this::toMonedaWebResponse)
                .collect(Collectors.toList());
    }

    private MonedaWebResponse toMonedaWebResponse(MonedaResponse monedaResponse) {
        MonedaWebResponse monedaWebResponse = new MonedaWebResponse();
        BeanUtils.copyProperties(monedaResponse, monedaWebResponse);
        return monedaWebResponse;
    }

    @GetMapping(
            value = "/{codigo}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse<MonedaWebResponse>>> getMonedaDetail(@PathVariable(value = "codigo") String codigo) {
        return monedaService.getMonedaDetail(codigo)
                .subscribeOn(Schedulers.io())
                .map(monedaResponse -> ResponseEntity.ok(BaseWebResponse.successWithData(toMonedaWebResponse(monedaResponse))));
    }

    @DeleteMapping(
            value = "/{codigo}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> deleteMoneda(@PathVariable(value = "codigo") String codigo) {
        return monedaService.deleteMoneda(codigo)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }

}
