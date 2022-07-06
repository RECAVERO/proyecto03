package com.nttdata.proyecto01.typeclientservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDto {
  private String id;
  private String idType;
  private String typeClient;

}
