package com.nttdata.proyecto01.accountservice.infraestructure.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Accounts")
public class Account {
  @Id
  private String id;
  private String idAccount;
  private String typeAccount;

}
