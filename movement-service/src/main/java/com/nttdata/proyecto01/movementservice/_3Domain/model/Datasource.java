package com.nttdata.proyecto01.movementservice._3Domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Datasource {
  List<CreditDTO> lista;
}
