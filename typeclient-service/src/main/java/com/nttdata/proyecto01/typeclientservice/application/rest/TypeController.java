package com.nttdata.proyecto01.typeclientservice.application.rest;

import com.nttdata.proyecto01.typeclientservice.btask.interfaces.TypeClientService;
import com.nttdata.proyecto01.typeclientservice.domain.model.TypeDto;
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
@RequestMapping("/typeClient")
public class TypeController {
  private final TypeClientService typeClientService;

  public TypeController(TypeClientService typeClientService) {
    this.typeClientService = typeClientService;
  }

  @GetMapping
  public Flux<TypeDto> getListTypeClient() {
    return this.typeClientService.getListTypeClient();
  }

  @GetMapping("/{id}")
  public Mono<TypeDto> getListTypeClient(@PathVariable("id") String id) {
    return this.typeClientService.getTypeClientById(id);
  }

  @PostMapping
  public Mono<TypeDto> saveClient(@RequestBody Mono<TypeDto> typeDto) {
    return this.typeClientService.saveTypeClient(typeDto);
  }

  @PutMapping("/{id}")
  public Mono<TypeDto> updateType(@RequestBody Mono<TypeDto> type, @PathVariable("id") String id) {
    return this.typeClientService.updateTypeClient(type, id);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deleteTypeClient(@PathVariable("id") String id) {
    return this.typeClientService.deleteTypeClientById(id);
  }

}
