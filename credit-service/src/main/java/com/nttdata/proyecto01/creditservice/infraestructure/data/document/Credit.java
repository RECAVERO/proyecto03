package com.nttdata.proyecto01.creditservice.infraestructure.data.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Credits")
public class Credit {
    @Id
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
