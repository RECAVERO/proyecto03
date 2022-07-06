package com.nttdata.proyecto01.creditservice.domain.contract;

import com.nttdata.proyecto01.creditservice.domain.model.CreditDto;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditRepository {
    Flux<CreditDto> getListCredit();
    Mono<CreditDto> getCreditById(String id);
    Mono<CreditDto> saveCredit(Mono<CreditDto> creditDTOMono);

    Mono<CreditDto> updateCredit(Mono<CreditDto> creditDTOMono, String id);
    Mono<Void> deleteCreditById(String id);

    Flux<CreditDto> getListByIdClient(String idClient);
    Flux<CreditDto> getListCreditByIdClientAndIdProduct(String idClient, String idType, String idProduct);
    Mono<CreditDto> getListCreditByIdClientAndIdTypeAndIdProduct(String idClient, String idType, String idProduct);

    Mono<CreditDto> getListCreditAll(String idClient, String idType, String idProduct, String numberCuent);

    Flux<CreditDto> getListCreditByIdClient(String idClient);

    Mono<CreditDto> updateCreditReportIntervalDay(@RequestBody Mono<CreditDto> creditDto);

    Flux<CreditDto> getListCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idProduct, String dateStart, String dateEnd);

    Mono<CreditDto> getCreditByNumberCuent(String numberCuent);

    Mono<CreditDto> findByIdClientAndIdProductAndIdType(String idClient, String idProduct, String idType);
    Mono<CreditDto> getListCreditByIdClientAndIdTypeAndIdProductAndNumberCuent(String idClient, String idType, String idProduct, String numberCuent);

    Mono<CreditDto> getAccountMain(String idClient, String numberCard, int category);
}
