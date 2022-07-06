package com.nttdata.proyecto01.clientservice.domain.interfaces;

import com.nttdata.proyecto01.clientservice.domain.models.ClientDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ClientService {
  Flux<ClientDto> findAllClient();

  Mono<ClientDto> saveClient(Mono<ClientDto> clientDto);

  Mono<ClientDto> findByIdClient(String id);

  Mono<Void> deleteByIdClient(String id);

  Mono<ClientDto> updateClient(Mono<ClientDto> clientDto, String id);

}
