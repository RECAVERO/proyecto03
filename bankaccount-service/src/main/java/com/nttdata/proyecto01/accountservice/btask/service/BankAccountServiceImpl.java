package com.nttdata.proyecto01.accountservice.btask.service;

import com.nttdata.proyecto01.accountservice.btask.interfaces.BankAccountService;
import com.nttdata.proyecto01.accountservice.domain.contract.AccountRepository;
import com.nttdata.proyecto01.accountservice.domain.model.AccountDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountServiceImpl implements BankAccountService {
  private final AccountRepository accountRepository;

  public BankAccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Flux<AccountDto> getListBankAccount() {
    return accountRepository.getListBankAccount();
  }

  @Override
  public Mono<AccountDto> getBankAccountById(String id) {
    return accountRepository.getBankAccountById(id);
  }

  @Override
  public Mono<AccountDto> saveBankAccount(Mono<AccountDto> accountDto) {
    return accountRepository.saveBankAccount(accountDto);
  }

  @Override
  public Mono<AccountDto> updateBankAccount(Mono<AccountDto> accountDto, String id) {
    return accountRepository.updateBankAccount(accountDto, id);
  }

  @Override
  public Mono<Void> deleteBankAccountById(String id) {
    return accountRepository.deleteBankAccountById(id);
  }

}
