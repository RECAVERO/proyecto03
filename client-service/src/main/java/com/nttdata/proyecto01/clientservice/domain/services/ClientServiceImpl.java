package com.nttdata.proyecto01.clientservice.domain.services;

import com.nttdata.proyecto01.clientservice.domain.interfaces.ClientService;
import com.nttdata.proyecto01.clientservice.domain.models.ClientDto;
import com.nttdata.proyecto01.clientservice.infrastructure.data.interfaces.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;

  private final WebClient.Builder webClientBuilder;

  public ClientServiceImpl(ClientRepository clientRepository, WebClient.Builder webClientBuilder) {
    this.clientRepository = clientRepository;
    this.webClientBuilder = webClientBuilder;
  }

  @Override
  public Flux<ClientDto> findAllClient() {
    return this.clientRepository.findAllClient();
  }

  @Override
  public Mono<ClientDto> saveClient(Mono<ClientDto> clientDto) {
    return this.clientRepository.saveClient(clientDto);
  }

  @Override
  public Mono<Void> deleteByIdClient(String id) {

    return this.clientRepository.deleteByIdClient(id);
  }

  @Override
  public Mono<ClientDto> updateClient(Mono<ClientDto> clientDto, String id) {
    return this.clientRepository.updateClient(clientDto, id);
  }

  @Override
  public Mono<ClientDto> findByIdClient(String id) {
    return this.clientRepository.findByIdClient(id);
  }

}
