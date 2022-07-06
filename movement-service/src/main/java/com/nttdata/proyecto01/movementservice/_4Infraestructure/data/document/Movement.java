package com.nttdata.proyecto01.movementservice._4Infraestructure.data.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Movements")
public class Movement {
    @Id
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
