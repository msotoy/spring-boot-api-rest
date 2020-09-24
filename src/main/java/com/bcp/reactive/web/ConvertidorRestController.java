package com.bcp.reactive.web;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bcp.reactive.service.convertidor.ConvertidorService;
import com.bcp.reactive.servicedto.response.ConvertidorResponse;
import com.bcp.reactive.webdto.request.ConvertidorWebRequest;
import com.bcp.reactive.webdto.response.BaseWebResponse;
import com.bcp.reactive.webdto.response.ConvertidorWebResponse;

@RestController
@RequestMapping(value = "/api/convertidor")
public class ConvertidorRestController {

    @Autowired
    private ConvertidorService convertidorService;

    @GetMapping(
    		consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse<ConvertidorWebResponse>>> ConvertirMoneda(
    		@RequestBody ConvertidorWebRequest convertidorWebRequest) {
        return convertidorService.convertirMoneda(convertidorWebRequest)
                .subscribeOn(Schedulers.io())
                .map(convertidorResponse -> ResponseEntity.ok(BaseWebResponse.successWithData(toConvertidorWebResponse(convertidorResponse))));
    }

    private ConvertidorWebResponse toConvertidorWebResponse(ConvertidorResponse convertidorResponse) {
        ConvertidorWebResponse convertidorWebResponse = new ConvertidorWebResponse();
        BeanUtils.copyProperties(convertidorResponse, convertidorWebResponse);
        return convertidorWebResponse;
    }

}
