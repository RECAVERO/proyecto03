package com.nttdata.proyecto01.movementservice._2Task.interfaces;

import com.nttdata.proyecto01.movementservice._3Domain.model.CreditDTO;
import com.nttdata.proyecto01.movementservice._3Domain.model.Datasource;
import com.nttdata.proyecto01.movementservice._3Domain.model.MovementDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    Flux<MovementDto> getListMovement();
    Mono<MovementDto> getMovementById(String id);
    Mono<MovementDto> saveMovement(Mono<MovementDto> movementDTOMono);
    Mono<MovementDto> updateMovement(Mono<MovementDto> movementDTOMono, String id);
    Mono<Void> deleteMovementById(String id);
    Mono<CreditDTO> getListCredit(String idClient,String idType, String idProduct, String numberCuent);
    Mono<CreditDTO> updateCreditDeposit(Mono<CreditDTO> creditDto);
    Mono<CreditDTO> updateCreditWithdrawal(Mono<CreditDTO> creditDto);
    Flux<MovementDto> getListRecordMovement(String idClient, String numberCuent);

    Mono<Datasource> updateCreditIntervalProgrammed(Mono<CreditDTO> creditDTOMono);


    Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idProduct, String dateStart, String dateEnd);

    Flux<CreditDTO> getListaCreditByRangeDate(String idClient, String idType, String idProduct,String dateStart,String dateEnd);

    Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndNumberCard(String idClient, String idType, String idProduct, String numberCard);
    Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndNumberCardAndcategory(String idClient, String idType, String idProduct, String numberCard, int category);

}
