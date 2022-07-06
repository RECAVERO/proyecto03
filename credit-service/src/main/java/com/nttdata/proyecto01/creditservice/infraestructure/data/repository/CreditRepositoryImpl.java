package com.nttdata.proyecto01.creditservice.infraestructure.data.repository;

import com.nttdata.proyecto01.creditservice.domain.contract.CreditRepository;
import com.nttdata.proyecto01.creditservice.domain.model.CreditDto;
import com.nttdata.proyecto01.creditservice.infraestructure.data.mongodb.CreditRepositoryMongoDB;
import com.nttdata.proyecto01.creditservice.infraestructure.data.mongodb.CreditRespositoryMongo;
import com.nttdata.proyecto01.creditservice.util.convert.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CreditRepositoryImpl implements CreditRepository {
  private final CreditRepositoryMongoDB creditRepository;
  private final CreditRespositoryMongo creditRespositoryMongo;
  private static final Logger log = LoggerFactory.getLogger(CreditRepositoryImpl.class);

  public CreditRepositoryImpl(CreditRepositoryMongoDB creditRepository, CreditRespositoryMongo creditRespositoryMongo) {
    this.creditRepository = creditRepository;
    this.creditRespositoryMongo = creditRespositoryMongo;
  }

  @Override
  public Flux<CreditDto> getListCredit() {
    return this.creditRepository.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<CreditDto> getCreditById(String id) {
    return this.creditRepository.findById(id).map(Convert::entityToDto);
  }

  @Override
  public Mono<CreditDto> saveCredit(Mono<CreditDto> creditDto) {
    return creditDto.map(Convert::DtoToEntity)
        .flatMap(creditRepository::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<CreditDto> updateCredit(Mono<CreditDto> creditDto, String id) {
    return creditRepository.findById(id)
        .flatMap(p -> creditDto.map(Convert::DtoToEntity)
            .doOnNext(e ->
                e.setId(id)
            ))
        .flatMap(creditRepository::save)
        .map(Convert::entityToDto);
  }

  private Mono<CreditDto> updateCreditLocal(Mono<CreditDto> creditDto, String id) {
    return creditRepository.findById(id)
        .flatMap(p -> creditDto.map(Convert::DtoToEntity)
            .doOnNext(e ->
                e.setId(id)
            ))
        .flatMap(creditRepository::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<Void> deleteCreditById(String id) {
    return creditRepository.deleteById(id);
  }

  @Override
  public Flux<CreditDto> getListByIdClient(String idClient) {
    return creditRepository.findByIdClient(idClient);
  }



  @Override
  public Mono<CreditDto> getListCreditByIdClientAndIdTypeAndIdProduct(String idClient,
                                                                      String idType, String idProduct) {

    return creditRepository.findByIdClientAndIdTypeAndIdProduct(idClient, idType, idProduct)
        .defaultIfEmpty(new CreditDto());

  }

  @Override
  public Mono<CreditDto> getListCreditAll(String idClient, String idType, String idProduct, String numberCuent) {
    return this.creditRepository.findByIdClientAndIdTypeAndIdProductAndNumberCuent(idClient, idType, idProduct, numberCuent).defaultIfEmpty(new CreditDto());
  }

  @Override
  public Flux<CreditDto> getListCreditByIdClient(String idClient) {
    return creditRepository.findByIdClient(idClient);
  }

  @Override
  public Mono<CreditDto> updateCreditReportIntervalDay(Mono<CreditDto> creditDto) {
    return null;
  }

  @Override
  public Flux<CreditDto> getListCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idProduct, String dateStart, String dateEnd) {
    return this.creditRepository.getListCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(idClient, idType, idProduct,dateStart, dateEnd);
  }

  @Override
  public Flux<CreditDto> getListCreditByIdClientAndIdProduct(String idClient, String idType, String idProduct) {
    return creditRespositoryMongo.findByIdClientAndIdTypeAndIdProduct(idClient, idType, idProduct);
  }

  @Override
  public Mono<CreditDto> getCreditByNumberCuent(String numberCuent) {
    return this.creditRepository.findByNumberCuent(numberCuent).defaultIfEmpty(new CreditDto());
  }

  @Override
  public Mono<CreditDto> findByIdClientAndIdProductAndIdType(String idClient, String idProduct, String idType) {
    return this.creditRepository.findByIdClientAndIdProductAndIdType(idClient, idProduct, idType).defaultIfEmpty(new CreditDto());
  }

  @Override
  public Mono<CreditDto> getListCreditByIdClientAndIdTypeAndIdProductAndNumberCuent(String idClient, String idType, String idProduct, String numberCuent) {
    return this.creditRepository.findByIdClientAndIdTypeAndIdProductAndNumberCuent(idClient,idType,idProduct,numberCuent).defaultIfEmpty(new CreditDto());
  }

  @Override
  public Mono<CreditDto> getAccountMain(String idClient, String numberCard, int category) {
    return this.creditRepository.findByIdClientAndNumberCardAndCategory(idClient, numberCard, category).defaultIfEmpty(new CreditDto());
  }

}
