package com.nttdata.proyecto01.clientservice.application.rest;

import com.nttdata.proyecto01.clientservice.domain.interfaces.ClientService;
import com.nttdata.proyecto01.clientservice.domain.models.ClientDto;
import com.nttdata.proyecto01.clientservice.domain.models.MessageDto;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/client")
public class ClientController {
  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  public Flux<ClientDto> getListClient() {
    return this.clientService.findAllClient();
  }

  @GetMapping("/{id}")
  public Mono<Map<String, Object>> getListClient(@PathVariable String id) {
    Map<String, Object> result = new HashMap<>();
    return this.clientService.findByIdClient(id).flatMap(cli -> {
      MessageDto messageDto = new MessageDto();
      ClientDto client = new ClientDto();
      if (cli.getId() == null) {
        messageDto.setStatus(HttpStatus.NOT_FOUND.toString());
        messageDto.setMsg("there is no record");
        result.put("Data", messageDto);
      } else {
        messageDto.setStatus(HttpStatus.CREATED.toString());
        messageDto.setMsg("successful query");
        client.setId(cli.getId());
        client.setIdClient(cli.getIdClient());
        client.setNames(cli.getNames());
        messageDto.setClient(client);
        result.put("Data", messageDto);
      }
      return Mono.just(result);
    });
  }

  @PostMapping
  public Mono<ClientDto> addClient(@RequestBody Mono<ClientDto> clientDto) {
    return this.clientService.saveClient(clientDto);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deleteClient(@PathVariable String id) {
    return this.clientService.deleteByIdClient(id);
  }

  @PutMapping("/{id}")
  public Mono<ClientDto> updateClient(@RequestBody Mono<ClientDto> cli, @PathVariable String id) {
    return this.clientService.updateClient(cli, id);
  }
}
