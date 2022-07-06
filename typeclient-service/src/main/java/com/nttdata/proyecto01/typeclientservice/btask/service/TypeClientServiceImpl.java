package com.nttdata.proyecto01.typeclientservice.btask.service;

import com.nttdata.proyecto01.typeclientservice.btask.interfaces.TypeClientService;
import com.nttdata.proyecto01.typeclientservice.domain.contract.TypeClientRepository;
import com.nttdata.proyecto01.typeclientservice.domain.model.TypeDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TypeClientServiceImpl implements TypeClientService {
  private final TypeClientRepository typeClientRepository;

  public TypeClientServiceImpl(TypeClientRepository typeClientRepository) {
    this.typeClientRepository = typeClientRepository;
  }

  @Override
  public Flux<TypeDto> getListTypeClient() {
    return this.typeClientRepository.getListTypeClient();
  }

  @Override
  public Mono<TypeDto> getTypeClientById(String id) {
    return this.typeClientRepository.getTypeClientById(id);
  }

  @Override
  public Mono<TypeDto> saveTypeClient(Mono<TypeDto> typeClientDtoMono) {
    return this.typeClientRepository.saveTypeClient(typeClientDtoMono);
  }

  @Override
  public Mono<TypeDto> updateTypeClient(Mono<TypeDto> typeClientDtoMono, String id) {
    return this.typeClientRepository.updateTypeClient(typeClientDtoMono, id);
  }

  @Override
  public Mono<Void> deleteTypeClientById(String id) {
    return this.typeClientRepository.deleteTypeClientById(id);
  }

}
