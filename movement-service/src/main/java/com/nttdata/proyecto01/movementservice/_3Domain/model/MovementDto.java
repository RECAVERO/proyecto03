package com.nttdata.proyecto01.movementservice._3Domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementDto {
    private String id;
    private String operation;
    private String idClient;
    private String idType;
    private String idProduct;
    private String numberCuent;
    private float amount;
    private String creationDate;
    private int status;
    private String numberCard;
    private int category;
}
