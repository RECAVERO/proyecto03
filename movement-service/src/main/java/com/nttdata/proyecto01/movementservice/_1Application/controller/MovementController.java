package com.nttdata.proyecto01.movementservice._1Application.controller;

import com.nttdata.proyecto01.movementservice._2Task.interfaces.MovementService;
import com.nttdata.proyecto01.movementservice._3Domain.model.*;
import com.nttdata.proyecto01.movementservice._4Infraestructure.data.document.Movement;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/Movement")
public class MovementController {
  private final MovementService _movementService;

  public MovementController(MovementService movementService) {
    _movementService = movementService;
  }
  @GetMapping
  public Flux<MovementDto> getListMovement(){
    return _movementService.getListMovement();
  }
  @GetMapping("/{id}")
  public Mono<MovementDto> getMovementById(@PathVariable String id){
    return _movementService.getMovementById(id);
  }
  @GetMapping("/record/{idClient}/{numberCuent}")
  public Flux<MovementDto> recordMovement(@PathVariable String idClient,
                                          @PathVariable String numberCuent){
    return this._movementService.getListRecordMovement(idClient,numberCuent);
  }

  @CircuitBreaker(name="creditDeposit",fallbackMethod = "fallBackDepositMovement")
  @PostMapping("/deposit")
  public Mono<Map<String, Object>> depositMovement(@RequestBody Mono<MovementDto> movementDto){
    Map<String, Object> result=new HashMap<>();
    return movementDto.flatMap(movement->{
      return _movementService.getListCredit(movement.getIdClient(),movement.getIdType(),movement.getIdProduct(),movement.getNumberCuent()).flatMap(c->{
        ResponseDto responseDto = new ResponseDto();
          if(c.getId() == null){
            responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
            responseDto.setMsg("No Hay registros con es criterio de busqueda");
            result.put("Data",responseDto);
            return Mono.just(result);
          }else{
            CreditDTO cred=new CreditDTO();
            cred.setId(c.getId());
            cred.setIdClient(c.getIdClient());
            cred.setIdType(c.getIdType());
            cred.setIdProduct(c.getIdProduct());
            cred.setNumberCuent(c.getNumberCuent());
            cred.setBalance(movement.getAmount());
            return _movementService.updateCreditDeposit(Mono.just(cred)).flatMap(credit->{
              return _movementService.saveMovement(Mono.just(movement)).flatMap(h->{
                responseDto.setStatus(HttpStatus.CREATED.toString());
                responseDto.setMsg("Se Registro Correctamente");
                responseDto.setMovementDto(h);
                result.put("Data",responseDto);
                return Mono.just(result);
              });
            });

          }
      });
    });
  }

  @CircuitBreaker(name="creditWithdrawal",fallbackMethod = "fallBackWithdrawalMovement")
  @PostMapping("/withdrawal")
  public Mono<Map<String, Object>> withdrawalMovement(@RequestBody Mono<MovementDto> movementDto){
    Map<String, Object> result=new HashMap<>();
    return movementDto.flatMap(movement->{
      return _movementService.getListCredit(movement.getIdClient(),movement.getIdType(),movement.getIdProduct(),movement.getNumberCuent()).flatMap(c->{
        ResponseDto responseDto = new ResponseDto();
        if(c.getId() == null){
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMsg("No Hay registros con es criterio de busqueda");
          result.put("Data",responseDto);
          return Mono.just(result);
        }else{
          CreditDTO cred=new CreditDTO();
          cred.setId(c.getId());
          cred.setIdClient(c.getIdClient());
          cred.setIdType(c.getIdType());
          cred.setIdProduct(c.getIdProduct());
          cred.setNumberCuent(c.getNumberCuent());
          cred.setBalance(movement.getAmount());
          return _movementService.updateCreditWithdrawal(Mono.just(cred)).flatMap(credit->{
            return _movementService.saveMovement(Mono.just(movement)).flatMap(h->{
              responseDto.setStatus(HttpStatus.CREATED.toString());
              responseDto.setMsg("Se Registro Correctamente");
              responseDto.setMovementDto(h);
              result.put("Data",responseDto);
              return Mono.just(result);
            });
          });

        }
      });
    });
  }

  @PutMapping("/{id}")
  public Mono<MovementDto> updateMovement(@RequestBody Mono<MovementDto> movementDTOMono, @PathVariable String id){
    return _movementService.updateMovement(movementDTOMono,id);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deleteMovementById(@PathVariable String id){
    return _movementService.deleteMovementById(id);
  }

  public Mono<Map<String, Object>> fallBackDepositMovement(@RequestBody Mono<MovementDto> movementDto, RuntimeException e){
    Map<String, Object> result=new HashMap<>();
    ResponseDto responseDto = new ResponseDto();
    responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
    responseDto.setMsg("No se puede hacer depositos; porque el servicio no esta disponible por el momento");
    result.put("Datasource",responseDto);
    return Mono.just(result);
  }

  public Mono<Map<String, Object>> fallBackWithdrawalMovement(@RequestBody Mono<MovementDto> movementDto, RuntimeException e){
    Map<String, Object> result=new HashMap<>();
    ResponseDto responseDto = new ResponseDto();
    responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
    responseDto.setMsg("No se puede hacer retiros; porque el servicio no esta disponible por el momento");
    result.put("Datasource",responseDto);
    return Mono.just(result);
  }


  @PostMapping("/Programmed/IntervalDay")
  public Mono<Datasource> saveIntervalDayProgrammed(@RequestBody Mono<CreditDTO> creditDTOMono){
    return this._movementService.updateCreditIntervalProgrammed(creditDTOMono);
  }

  //@CircuitBreaker(name="products",fallbackMethod = "fallBackWithCreationDateBetween")
  @GetMapping("/Products/All/{idClient}/{idType}/{idProduct}/{dateStart}/{dateEnd}")
  public Mono<DatasourceDto> findByIdClientAndIdTypeAndIdProductAndCreationDateBetween(@PathVariable String idClient,
                                                    @PathVariable String idType,
                                                    @PathVariable String idProduct,
                                                    @PathVariable String dateStart,
                                                    @PathVariable String dateEnd){
    Map<String, Object> result=new HashMap<>();
    Mono<List<MovementDto>> flux= this._movementService.getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(idClient,idType,idProduct,dateStart,dateEnd).collectList();
    List<MovementDto> listMovemet=new ArrayList<>();
    return flux.flatMap(lista->{
      lista.forEach((v)->{
        MovementDto cv=new MovementDto();
        cv.setId(v.getId());
        cv.setOperation(v.getOperation());
        cv.setIdClient(v.getIdClient());
        cv.setIdType(v.getIdType());
        cv.setIdProduct(v.getIdProduct());
        cv.setNumberCuent(v.getNumberCuent());
        cv.setAmount(v.getAmount());
        cv.setCreationDate(v.getCreationDate());
        cv.setStatus(v.getStatus());
        listMovemet.add(cv);
      });

      DatasourceDto m=new DatasourceDto();
      m.setStatus(HttpStatus.OK.toString());
      m.setMsg("Ok");
      m.setMovement(listMovemet);

      Mono<List<CreditDTO>> fluxCredit=  this._movementService.getListaCreditByRangeDate(idClient,idType,idProduct,dateStart,dateEnd).collectList();

      List<CreditDTO> listCredit=new ArrayList<>();

      return fluxCredit.flatMap(listax->{
        listax.forEach((v)->{
          CreditDTO cv=new CreditDTO();
          cv.setId(v.getId());
          cv.setIdClient(v.getIdClient());
          cv.setIdType(v.getIdType());
          cv.setIdProduct(v.getIdProduct());
          cv.setNumberCuent(v.getNumberCuent());
          cv.setBalance(v.getBalance());
          cv.setCreationDate(v.getCreationDate());
          cv.setStatus(v.getStatus());
          listCredit.add(cv);
        });
        m.setCredit(listCredit);
        return Mono.just(m);
      });


      //return Mono.just(m);
    });
    //return this._movementService.getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(idClient,idType,idProduct,dateStart,dateEnd);
    }
  @GetMapping("/Products/getAll/{idClient}/{idType}/{idProduct}/{dateStart}/{dateEnd}")
  public Mono<Datasource> getListaCreditByRangeDate(@PathVariable String idClient,
                                                                                       @PathVariable String idType,
                                                                                       @PathVariable String idProduct,
                                                                                       @PathVariable String dateStart,
                                                                                       @PathVariable String dateEnd){

    List<CreditDTO> list3 = new ArrayList<>();
    CreditDTO credito=new CreditDTO();



    Mono<List<CreditDTO>> flux= this._movementService.getListaCreditByRangeDate(idClient,idType, idProduct,dateStart,dateEnd).collectList();


    return    flux.flatMap(f->{
      //System.out.println(f);

      f.forEach((v)->{
        CreditDTO cv=new CreditDTO();
        cv.setId(v.getId());
        cv.setIdClient(v.getIdClient());
        cv.setIdType(v.getIdType());
        cv.setIdProduct(v.getIdProduct());
        cv.setNumberCuent(v.getNumberCuent());
        cv.setBalance(v.getBalance());
        cv.setCreationDate(v.getCreationDate());
        cv.setStatus(v.getStatus());
        //cv.setReportIntervalDay(v.getReportIntervalDay());

        list3.add(cv);
      });
      Datasource data=new Datasource();
      data.setLista(list3);
      //System.out.println(data.getLista());
      return Mono.just(data);
    });



    //return this._movementService.getListaCreditByRangeDate(idClient,idType,idProduct,dateStart,dateEnd);
  }


  public Mono<DatasourceDto> fallBackWithCreationDateBetween(@PathVariable String idClient,
                                                             @PathVariable String idType,
                                                             @PathVariable String idProduct,
                                                             @PathVariable String dateStart,
                                                             @PathVariable String dateEnd){
    DatasourceDto m=new DatasourceDto();
    m.setStatus(HttpStatus.NOT_FOUND.toString());
    m.setMsg("Service No esta disponible por ahora");
    return Mono.just(m);
  }

  @PostMapping("/report")
  public Flux<MovementDto> getReportMovementLast(@RequestBody Mono<MovementDto> movementDto){
    ResponseDto responseListDto=new ResponseDto();
    List<MovementDto> lista=new ArrayList<>();

      return this._movementService.getListMovementByIdClientAndIdTypeAndIdProductAndNumberCard("cli01","typ01","pro01","01-34444-66666-8888");

  }

  @PostMapping("/payment")
  public Mono<Map<String, Object>> paymentMovement(@RequestBody Mono<MovementDto> movementDto){
    Map<String, Object> result=new HashMap<>();
    return movementDto.flatMap(movement->{
      return _movementService.getListCredit(movement.getIdClient(),movement.getIdType(),movement.getIdProduct(),movement.getNumberCuent()).flatMap(c->{
        ResponseDto responseDto = new ResponseDto();
        if(c.getId() == null){
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMsg("No Hay registros con es criterio de busqueda");
          result.put("Data",responseDto);
          return Mono.just(result);
        }else{
          CreditDTO cred=new CreditDTO();
          cred.setId(c.getId());
          cred.setIdClient(c.getIdClient());
          cred.setIdType(c.getIdType());
          cred.setIdProduct(c.getIdProduct());
          cred.setNumberCuent(c.getNumberCuent());
          cred.setBalance(movement.getAmount());
          movement.setOperation("Pago Debito");
          Date fechaCre=new Date();
          String fecha=new SimpleDateFormat("yyyy-MM-dd").format(fechaCre);
          movement.setCreationDate(fecha);
          return _movementService.updateCreditWithdrawal(Mono.just(cred)).flatMap(credit->{
            return _movementService.saveMovement(Mono.just(movement)).flatMap(h->{
              responseDto.setStatus(HttpStatus.CREATED.toString());
              responseDto.setMsg("Se Registro Correctamente");
              responseDto.setMovementDto(h);
              result.put("Data",responseDto);
              return Mono.just(result);
            });
          });

        }
      });
    });
  }
  

}
