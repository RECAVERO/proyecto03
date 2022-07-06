package com.nttdata.proyecto01.movementservice._2Task.service;

import com.nttdata.proyecto01.movementservice._2Task.interfaces.MovementService;
import com.nttdata.proyecto01.movementservice._3Domain.contract.MovementRepository;
import com.nttdata.proyecto01.movementservice._3Domain.model.CreditDTO;
import com.nttdata.proyecto01.movementservice._3Domain.model.Datasource;
import com.nttdata.proyecto01.movementservice._3Domain.model.MovementDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {
    private final MovementRepository _movementRepository;
    private final WebClient.Builder _webClientBuilder;
    public MovementServiceImpl(MovementRepository movementRepository, WebClient.Builder webClientBuilder) {
        _movementRepository=movementRepository;
        _webClientBuilder = webClientBuilder;
    }

    @Override
    public Flux<MovementDto> getListMovement() {
        return _movementRepository.getListMovement();
    }

    @Override
    public Mono<MovementDto> getMovementById(String id) {
        return _movementRepository.getMovementById(id);
    }

    @Override
    public Mono<MovementDto> saveMovement(Mono<MovementDto> movementDTOMono) {

        return _movementRepository.saveMovement(movementDTOMono);
    }

    @Override
    public Mono<MovementDto> updateMovement(Mono<MovementDto> movementDTOMono, String id) {
        return _movementRepository.updateMovement(movementDTOMono,id);
    }

    @Override
    public Mono<Void> deleteMovementById(String id) {
        return _movementRepository.deleteMovementById(id);
    }

    @Override
    public Mono<CreditDTO> getListCredit(String idClient,String idType, String idProduct, String numberCuent) {
        Mono<CreditDTO> creditDTOMono=_webClientBuilder.build()
                .get().uri("http://localhost:9004/credit/products/"+idClient+"/"+idType + "/"+idProduct + "/" + numberCuent)
                .retrieve().bodyToMono(CreditDTO.class);
        creditDTOMono.subscribe(c->{
            System.out.println("Codigo"+c.getId() +"Client" + c.getIdClient()+"type"+c.getIdType() +"product" + c.getIdProduct());
        });


        return creditDTOMono;
    }

    @Override
    public Mono<CreditDTO> updateCreditDeposit(Mono<CreditDTO> creditDto) {
        return creditDto.flatMap(credi->{
                return _webClientBuilder.build()
                    .post().uri("http://localhost:9004/credit/deposit")
                    .body(creditDto, CreditDTO.class)
                    .retrieve()
                    .bodyToMono(CreditDTO.class);
        });
    }
    @Override
    public Mono<CreditDTO> updateCreditWithdrawal(Mono<CreditDTO> creditDto) {
        return creditDto.flatMap(credi->{
            return _webClientBuilder.build()
                .post().uri("http://localhost:9004/credit/withdrawal")
                .body(creditDto, CreditDTO.class)
                .retrieve()
                .bodyToMono(CreditDTO.class);
        });
    }

    @Override
    public Mono<Datasource> updateCreditIntervalProgrammed(Mono<CreditDTO> creditDto) {
        return creditDto.flatMap(credi->{
            System.out.println(credi);
           return _webClientBuilder.build()
                .post().uri("http://localhost:9004/credit/products")
                .body(Mono.just(credi), CreditDTO.class)
                .retrieve()
                .bodyToMono(Datasource.class);
        });
    }
    @Override
    public Flux<CreditDTO> getListaCreditByRangeDate(String idClient, String idType, String idProduct, String dateStart, String dateEnd) {
            return _webClientBuilder.build()
                .get().uri("http://localhost:9004/credit/Products/"+ idClient +"/"+idType +"/"+idProduct +"/"+ dateStart +"/"+ dateEnd)
                .retrieve()
                .bodyToFlux(CreditDTO.class);

    }

    @Override
    public Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndNumberCard(String idClient, String idType, String idProduct, String numberCard) {
        return this._movementRepository.getListMovementByIdClientAndIdTypeAndIdProductAndNumberCard(idClient,idType,idProduct,numberCard);
    }



    @Override
    public Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idProduct, String dateStart, String dateEnd) {
        return this._movementRepository.getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(idClient,idType,idProduct,dateStart,dateEnd);
    }




    @Override
    public Flux<MovementDto> getListRecordMovement(String idClient, String numberCuent) {
        return _movementRepository.getListRecordMovement(idClient,numberCuent);
    }

    @Override
    public Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndNumberCardAndcategory(String idClient, String idType, String idProduct, String numberCard, int category) {
        return this._movementRepository.getListMovementByIdClientAndIdTypeAndIdProductAndNumberCardAndcategory(idClient,idType,idProduct,numberCard,category);
    }

}
