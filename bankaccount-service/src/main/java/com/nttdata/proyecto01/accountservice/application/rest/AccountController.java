package com.nttdata.proyecto01.accountservice.application.rest;

import com.nttdata.proyecto01.accountservice.btask.interfaces.BankAccountService;
import com.nttdata.proyecto01.accountservice.domain.model.AccountDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/account")
public class AccountController {
  private final BankAccountService accountService;

  public AccountController(BankAccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping
  public Flux<AccountDto> getListBankAccount() {
    return this.accountService.getListBankAccount();
  }

  @GetMapping("/{id}")
  public Mono<AccountDto> getListBankAccount(@PathVariable String id) {
    return this.accountService.getBankAccountById(id);
  }

  @PostMapping
  public Mono<AccountDto> saveBankAccount(@RequestBody Mono<AccountDto> accountDto) {
    return this.accountService.saveBankAccount(accountDto);
  }

  @PutMapping("/{id}")
  public Mono<AccountDto> updateAccount(@RequestBody Mono<AccountDto> accountDto,
                                        @PathVariable("id") String id) {
    return this.accountService.updateBankAccount(accountDto, id);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deleteBankAccount(@PathVariable("id") String id) {
    return this.accountService.deleteBankAccountById(id);
  }

}
