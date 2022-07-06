package com.nttdata.proyecto01.movementservice._3Domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasourceDto {
  private String status;
  private String msg;
  private List<MovementDto> movement;
  private List<CreditDTO> credit;
}
