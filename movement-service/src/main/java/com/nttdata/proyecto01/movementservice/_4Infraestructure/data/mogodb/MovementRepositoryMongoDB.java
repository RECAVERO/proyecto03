package com.nttdata.proyecto01.movementservice._4Infraestructure.data.mogodb;


import com.nttdata.proyecto01.movementservice._3Domain.model.MovementDto;
import com.nttdata.proyecto01.movementservice._4Infraestructure.data.document.Movement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface MovementRepositoryMongoDB extends ReactiveMongoRepository<Movement,String> {

  Flux<MovementDto> findByIdClientAndNumberCuent(String idClient, String numberCuent);

  Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idProduct, String dateStart, String dateEnd);

  Flux<MovementDto> findByIdClientAndIdTypeAndIdProductAndNumberCard(String idClient, String idType, String idProduct, String numberCard);

  Flux<MovementDto> findByIdClientAndIdTypeAndIdProductAndNumberCardAndcategory(String idClient, String idType, String idProduct, String numberCard, int category);

}
