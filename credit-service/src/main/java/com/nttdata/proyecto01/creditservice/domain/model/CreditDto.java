package com.nttdata.proyecto01.creditservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDto {
    private String id;
    private String idClient;
    private String idType;
    private String idProduct;
    private String numberCuent;
    private float balance;
    private String creationDate;
    private int status;
    private String numberCard;
    private int category;
}
