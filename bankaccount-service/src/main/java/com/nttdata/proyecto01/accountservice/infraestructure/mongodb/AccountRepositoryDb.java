package com.nttdata.proyecto01.accountservice.infraestructure.mongodb;

import com.nttdata.proyecto01.accountservice.infraestructure.document.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountRepositoryDb extends ReactiveMongoRepository<Account, String> {

}
