package com.nttdata.proyecto01.creditservice.infraestructure.data.mongodb;

import com.nttdata.proyecto01.creditservice.domain.model.CreditDto;
import com.nttdata.proyecto01.creditservice.infraestructure.data.document.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CreditRespositoryMongo extends ReactiveMongoRepository<Credit,String> {
  Flux<CreditDto> findByIdClientAndIdTypeAndIdProduct(String idClient, String idType, String idProduct);

}
