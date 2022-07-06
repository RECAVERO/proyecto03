package com.nttdata.proyecto01.typeclientservice.btask.interfaces;

import com.nttdata.proyecto01.typeclientservice.domain.model.TypeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeClientService {
  Flux<TypeDto> getListTypeClient();

  Mono<TypeDto> getTypeClientById(String id);

  Mono<TypeDto> saveTypeClient(Mono<TypeDto> typeClientDtoMono);

  Mono<TypeDto> updateTypeClient(Mono<TypeDto> typeDtoMono, String id);

  Mono<Void> deleteTypeClientById(String id);

}
