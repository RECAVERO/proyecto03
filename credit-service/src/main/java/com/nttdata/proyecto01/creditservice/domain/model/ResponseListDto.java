package com.nttdata.proyecto01.creditservice.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseListDto {
  private String status;
  private String msg;
  private List<CreditDto> listCreditDto;
}
