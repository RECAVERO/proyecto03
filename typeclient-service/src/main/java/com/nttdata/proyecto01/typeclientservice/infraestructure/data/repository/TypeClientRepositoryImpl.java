package com.nttdata.proyecto01.typeclientservice.infraestructure.data.repository;

import com.nttdata.proyecto01.typeclientservice.domain.contract.TypeClientRepository;
import com.nttdata.proyecto01.typeclientservice.domain.model.TypeDto;
import com.nttdata.proyecto01.typeclientservice.infraestructure.data.mongodb.TypeRepository;
import com.nttdata.proyecto01.typeclientservice.utils.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TypeClientRepositoryImpl implements TypeClientRepository {
  private final TypeRepository typeRepository;

  public TypeClientRepositoryImpl(TypeRepository typeRepository) {
    this.typeRepository = typeRepository;
  }

  @Override
  public Flux<TypeDto> getListTypeClient() {
    return this.typeRepository.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<TypeDto> getTypeClientById(String id) {
    return this.typeRepository.findById(id).map(Convert::entityToDto);
  }

  @Override
  public Mono<TypeDto> saveTypeClient(Mono<TypeDto> typeClientDtoMono) {
    return typeClientDtoMono.map(Convert::dtoToEntity)
        .flatMap(typeRepository::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<TypeDto> updateTypeClient(Mono<TypeDto> typeClientDtoMono, String id) {
    return typeRepository.findById(id)
        .flatMap(p -> typeClientDtoMono.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(typeRepository::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<Void> deleteTypeClientById(String id) {
    return typeRepository.deleteById(id);
  }


}
