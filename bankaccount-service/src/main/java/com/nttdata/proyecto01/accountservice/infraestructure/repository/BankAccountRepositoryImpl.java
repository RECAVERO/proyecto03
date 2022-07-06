package com.nttdata.proyecto01.accountservice.infraestructure.repository;

import com.nttdata.proyecto01.accountservice.domain.contract.AccountRepository;
import com.nttdata.proyecto01.accountservice.domain.model.AccountDto;
import com.nttdata.proyecto01.accountservice.infraestructure.mongodb.AccountRepositoryDb;
import com.nttdata.proyecto01.accountservice.util.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BankAccountRepositoryImpl implements AccountRepository {

  private final AccountRepositoryDb accountRepositoryMongoDb;

  public BankAccountRepositoryImpl(AccountRepositoryDb bankAccountRepositoryMongoDb) {
    accountRepositoryMongoDb = bankAccountRepositoryMongoDb;
  }


  @Override
  public Flux<AccountDto> getListBankAccount() {
    return accountRepositoryMongoDb.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<AccountDto> getBankAccountById(String id) {
    return accountRepositoryMongoDb.findById(id).map(Convert::entityToDto);
  }

  @Override
  public Mono<AccountDto> saveBankAccount(Mono<AccountDto> accountDto) {
    return accountDto.map(Convert::dtoToEntity)
        .flatMap(accountRepositoryMongoDb::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<AccountDto> updateBankAccount(Mono<AccountDto> accountDto, String id) {
    return accountRepositoryMongoDb.findById(id)
        .flatMap(p -> accountDto.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(accountRepositoryMongoDb::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<Void> deleteBankAccountById(String id) {
    return accountRepositoryMongoDb.deleteById(id);
  }

}
