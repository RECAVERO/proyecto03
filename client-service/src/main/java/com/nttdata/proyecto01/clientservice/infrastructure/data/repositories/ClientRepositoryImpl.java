package com.nttdata.proyecto01.clientservice.infrastructure.data.repositories;

import com.nttdata.proyecto01.clientservice.domain.models.ClientDto;
import com.nttdata.proyecto01.clientservice.infrastructure.data.interfaces.ClientRepository;
import com.nttdata.proyecto01.clientservice.infrastructure.data.mongodb.ClientReactiveMongoRepository;
import com.nttdata.proyecto01.clientservice.utils.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

  private final ClientReactiveMongoRepository reactiveMongoRepository;

  public ClientRepositoryImpl(ClientReactiveMongoRepository clientReactiveMongoRepository) {
    this.reactiveMongoRepository = clientReactiveMongoRepository;
  }

  @Override
  public Flux<ClientDto> findAllClient() {
    return this.reactiveMongoRepository.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<ClientDto> saveClient(Mono<ClientDto> clientDto) {
    return clientDto.map(Convert::dtoToEntity)
        .flatMap(reactiveMongoRepository::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<ClientDto> findByIdClient(String id) {
    return this.reactiveMongoRepository.findById(id)
                .map(Convert::entityToDto).defaultIfEmpty(new ClientDto());
  }

  @Override
  public Mono<ClientDto> updateClient(Mono<ClientDto> clientDto, String id) {
    return  reactiveMongoRepository.findById(id)
        .flatMap(p -> clientDto.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(reactiveMongoRepository::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<Void> deleteByIdClient(String id) {
    return this.reactiveMongoRepository.deleteById(id);
  }

}
