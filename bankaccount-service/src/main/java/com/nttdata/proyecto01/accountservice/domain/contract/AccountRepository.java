package com.nttdata.proyecto01.accountservice.domain.contract;

import com.nttdata.proyecto01.accountservice.domain.model.AccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {

  Flux<AccountDto> getListBankAccount();

  Mono<AccountDto> getBankAccountById(String id);

  Mono<AccountDto> saveBankAccount(Mono<AccountDto> accountDto);

  Mono<AccountDto> updateBankAccount(Mono<AccountDto> accountDto, String id);

  Mono<Void> deleteBankAccountById(String id);

}
