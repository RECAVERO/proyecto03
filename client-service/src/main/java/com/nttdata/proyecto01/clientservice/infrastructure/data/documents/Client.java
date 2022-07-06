package com.nttdata.proyecto01.clientservice.infrastructure.data.documents;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Clients")
public class Client {
  @Id
  private String id;
  @NotEmpty
  private String idClient;
  @NotEmpty
  private String names;
  @NotEmpty
  private String email;
}
