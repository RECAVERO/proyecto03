package com.nttdata.proyecto01.clientservice.infrastructure.data.mongodb;

import com.nttdata.proyecto01.clientservice.infrastructure.data.documents.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientReactiveMongoRepository extends ReactiveMongoRepository<Client, String> {

}
