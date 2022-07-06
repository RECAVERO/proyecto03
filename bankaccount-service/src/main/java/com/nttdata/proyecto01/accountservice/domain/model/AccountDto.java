package com.nttdata.proyecto01.accountservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
  private String id;
  private String idAccount;
  private String typeAccount;
}
