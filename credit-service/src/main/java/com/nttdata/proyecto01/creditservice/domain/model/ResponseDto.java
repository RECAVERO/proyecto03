package com.nttdata.proyecto01.creditservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
  private String status;
  private String msg;
  private CreditDto creditDto;
}