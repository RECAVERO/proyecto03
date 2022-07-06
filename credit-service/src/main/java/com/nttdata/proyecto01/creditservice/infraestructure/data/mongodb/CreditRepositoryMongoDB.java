package com.nttdata.proyecto01.creditservice.infraestructure.data.mongodb;

import com.nttdata.proyecto01.creditservice.domain.model.CreditDto;
import com.nttdata.proyecto01.creditservice.infraestructure.data.document.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditRepositoryMongoDB extends ReactiveMongoRepository<Credit,String> {
    Flux<CreditDto> findByIdClient(String idClient);

    Flux<CreditDto> findByIdClientAndIdProduct(String idClient, String idProduct);

    Mono<CreditDto> findByIdClientAndIdTypeAndIdProduct(String idClient, String idType, String idProduct);

    Mono<CreditDto> findByIdClientAndIdTypeAndIdProductAndNumberCuent(String idClient, String idType, String idProduct, String numberCuent);

  Flux<CreditDto> getListCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idProduct, String dateStart, String dateEnd);
  Mono<CreditDto> findByIdClientAndIdProductAndIdType(String idClient, String idProduct, String idType);
  Mono<CreditDto> findByNumberCuent(String numberCuent);

  Mono<CreditDto> findByIdClientAndNumberCardAndCategory(String idClient, String numberCard, int category);

}
