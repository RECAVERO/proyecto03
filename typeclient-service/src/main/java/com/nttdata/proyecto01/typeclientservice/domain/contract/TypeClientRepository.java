package com.nttdata.proyecto01.typeclientservice.domain.contract;

import com.nttdata.proyecto01.typeclientservice.domain.model.TypeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeClientRepository {
  Flux<TypeDto> getListTypeClient();

  Mono<TypeDto> getTypeClientById(String id);

  Mono<TypeDto> saveTypeClient(Mono<TypeDto> typeClientDtoMono);

  Mono<TypeDto> updateTypeClient(Mono<TypeDto> typeClientDtoMono, String id);

  Mono<Void> deleteTypeClientById(String id);

}
