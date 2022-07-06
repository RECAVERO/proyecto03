package com.nttdata.proyecto01.typeclientservice.infraestructure.data.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "TypesClients")
public class Type {
  @Id
  private String id;
  private String idType;
  private String typeClient;
}
