package com.nttdata.proyecto01.typeclientservice.infraestructure.data.mongodb;

import com.nttdata.proyecto01.typeclientservice.infraestructure.data.document.Type;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;



public interface TypeRepository extends ReactiveMongoRepository<Type, String> {

}
