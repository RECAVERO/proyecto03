package com.nttdata.proyecto01.movementservice._3Domain.contract;

import com.nttdata.proyecto01.movementservice._3Domain.model.MovementDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementRepository {
    Flux<MovementDto> getListMovement();
    Mono<MovementDto> getMovementById(String id);
    Mono<MovementDto> saveMovement(Mono<MovementDto> movementDTOMono);
    Mono<MovementDto> updateMovement(Mono<MovementDto> movementDTOMono, String id);
    Mono<Void> deleteMovementById(String id);
    Flux<MovementDto> getListRecordMovement(String idClient, String numberCuent);

    Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idProduct, String dateStart, String dateEnd);

    Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndNumberCard(String idClient, String idType, String idProduct, String numberCard);

    Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndNumberCardAndcategory(String idClient, String idType, String idProduct, String numberCard, int category);

}
