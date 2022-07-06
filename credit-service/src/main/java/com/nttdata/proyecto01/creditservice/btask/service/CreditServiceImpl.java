package com.nttdata.proyecto01.creditservice.btask.service;

import com.nttdata.proyecto01.creditservice.btask.interfaces.CreditService;
import com.nttdata.proyecto01.creditservice.domain.contract.CreditRepository;
import com.nttdata.proyecto01.creditservice.domain.model.CreditDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {

  private final CreditRepository creditRepository;


  public CreditServiceImpl(CreditRepository creditRepository) {
    this.creditRepository = creditRepository;
  }

  @Override
  public Flux<CreditDto> getListCredit() {
    return creditRepository.getListCredit();
  }

  @Override
  public Mono<CreditDto> getCreditById(String id) {
    return creditRepository.getCreditById(id);
  }

  @Override
  public Mono<CreditDto> saveCredit(Mono<CreditDto> creditDto) {
    return creditRepository.saveCredit(creditDto);
  }

  @Override
  public Mono<CreditDto> updateCredit(Mono<CreditDto> creditDto,
                                      String id ) {
    return creditRepository.updateCredit(creditDto, id);
  }


  @Override
  public Mono<Void> deleteCreditById(String id) {
    return creditRepository.deleteCreditById(id);
  }

  @Override
  public Flux<CreditDto> getListByIdClient(String idClient) {
    return creditRepository.getListByIdClient(idClient);
  }

  @Override
  public Flux<CreditDto> getListCreditByIdClientAndIdProduct(String idClient, String idType, String idProduct) {
    return creditRepository.getListCreditByIdClientAndIdProduct(idClient, idType, idProduct);
  }

  @Override
  public Mono<CreditDto> getListCreditByIdClientAndIdTypeAndIdProduct(String idClient,
                                                                      String idType,
                                                                      String idProduct) {
    return creditRepository.getListCreditByIdClientAndIdTypeAndIdProduct(idClient, idType, idProduct);
  }

  @Override
  public Mono<CreditDto> getListCreditAll(String idClient, String idType, String idProduct, String numberCuent) {
    return creditRepository.getListCreditAll(idClient,idType,idProduct,numberCuent);
  }

  @Override
  public Flux<CreditDto> getListCreditByIdClient(String idClient) {
    return creditRepository.getListCreditByIdClient(idClient);
  }

  @Override
  public Mono<CreditDto> updateCreditReportIntervalDay(Mono<CreditDto> creditDto) {
    return null;
  }

  @Override
  public Flux<CreditDto> getListCreditByIdClientAndIdProductAll(String idClient, String idType, String idProduct) {
    return this.creditRepository.getListCreditByIdClientAndIdProduct(idClient,idType,idProduct);
  }

  @Override
  public Flux<CreditDto> getListCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idProduct, String dateStart, String dateEnd) {
    return this.creditRepository.getListCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(idClient,idType,idProduct,dateStart,dateEnd);
  }

  @Override
  public Mono<CreditDto> getCreditByNumberCuent(String numberCuent) {
    return this.creditRepository.getCreditByNumberCuent(numberCuent);
  }

  @Override
  public Mono<CreditDto> findByIdClientAndIdProductAndIdType(String idClient, String idProduct, String idType) {
    return this.creditRepository.findByIdClientAndIdProductAndIdType(idClient, idProduct, idType);
  }


  @Override
  public Mono<CreditDto> getListCreditByIdClientAndIdTypeAndIdProductAndNumberCuent(String idClient, String idType, String idProduct, String numberCuent) {
    return this.creditRepository.getListCreditByIdClientAndIdTypeAndIdProductAndNumberCuent(idClient,idType,idProduct,numberCuent);
  }

  @Override
  public Mono<CreditDto> getAccountMain(String idClient, String numberCard, int category) {
    return this.creditRepository.getAccountMain(idClient,numberCard,category);
  }


}
