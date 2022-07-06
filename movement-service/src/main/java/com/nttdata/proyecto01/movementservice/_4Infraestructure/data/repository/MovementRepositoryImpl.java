package com.nttdata.proyecto01.movementservice._4Infraestructure.data.repository;

import com.nttdata.proyecto01.movementservice._3Domain.contract.MovementRepository;
import com.nttdata.proyecto01.movementservice._3Domain.model.MovementDto;
import com.nttdata.proyecto01.movementservice._4Infraestructure.data.mogodb.MovementRepositoryMongoDB;
import com.nttdata.proyecto01.movementservice._5Utils.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public class MovementRepositoryImpl implements MovementRepository {
    private final MovementRepositoryMongoDB _movementRepositoryMongoDB;

    public MovementRepositoryImpl(MovementRepositoryMongoDB movementRepositoryMongoDB) {
        _movementRepositoryMongoDB = movementRepositoryMongoDB;
    }

    @Override
    public Flux<MovementDto> getListMovement() {
        return _movementRepositoryMongoDB.findAll().map(Convert::entityToDto);
    }

    @Override
    public Mono<MovementDto> getMovementById(String id) {
        return _movementRepositoryMongoDB.findById(id).map(Convert::entityToDto);
    }

    @Override
    public Mono<MovementDto> saveMovement(Mono<MovementDto> movementDTOMono) {
        return movementDTOMono.map(Convert::DtoToEntity)
                .flatMap(_movementRepositoryMongoDB::insert)
                .map(Convert::entityToDto);
    }

    @Override
    public Mono<MovementDto> updateMovement(Mono<MovementDto> movementDTOMono, String id) {
        return _movementRepositoryMongoDB.findById(id)
                .flatMap(p -> movementDTOMono.map(Convert::DtoToEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(_movementRepositoryMongoDB::save)
                .map(Convert::entityToDto);
    }

    @Override
    public Mono<Void> deleteMovementById(String id) {
        return _movementRepositoryMongoDB.deleteById(id);
    }

    @Override
    public Flux<MovementDto> getListRecordMovement(String idClient, String numberCuent) {
        return _movementRepositoryMongoDB.findByIdClientAndNumberCuent(idClient,numberCuent);
    }

    @Override
    public Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idProduct, String dateStart, String dateEnd) {
        return this._movementRepositoryMongoDB.getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(idClient,idType,idProduct,dateStart,dateEnd);
    }

    @Override
    public Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndNumberCard(String idClient, String idType, String idProduct, String numberCard) {
        return this._movementRepositoryMongoDB.findByIdClientAndIdTypeAndIdProductAndNumberCard(idClient,idType,idProduct,numberCard);
    }

    @Override
    public Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndNumberCardAndcategory(String idClient, String idType, String idProduct, String numberCard, int category) {
        return this._movementRepositoryMongoDB.findByIdClientAndIdTypeAndIdProductAndNumberCardAndcategory(idClient, idType, idProduct, numberCard, category);
    }

}
