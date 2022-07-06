package com.nttdata.proyecto01.accountservice.util.convert;

import com.nttdata.proyecto01.accountservice.domain.model.AccountDto;
import com.nttdata.proyecto01.accountservice.infraestructure.document.Account;
import org.springframework.beans.BeanUtils;

public class Convert {
  public static AccountDto entityToDto(Account account) {
    AccountDto accountDto = new AccountDto();
    BeanUtils.copyProperties(account, accountDto);
    return accountDto;
  }

  public static Account dtoToEntity(AccountDto accountDto) {
    Account account = new Account();
    BeanUtils.copyProperties(accountDto, account);
    return account;
  }
}
