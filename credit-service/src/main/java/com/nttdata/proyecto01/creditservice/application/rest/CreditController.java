package com.nttdata.proyecto01.creditservice.application.rest;

import com.nttdata.proyecto01.creditservice.btask.interfaces.CreditService;
import com.nttdata.proyecto01.creditservice.domain.model.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/credit")
public class CreditController {
  private final CreditService creditService;

  public CreditController(CreditService creditService) {
    this.creditService = creditService;
  }

  @GetMapping
  public Flux<CreditDto> getListCredit() {
    return this.creditService.getListCredit();
  }

  @GetMapping("/{id}")
  public Mono<CreditDto> getCreditById(@PathVariable String id) {
    return this.creditService.getCreditById(id);
  }

  @PostMapping
  public Mono<Map<String, Object>> saveCredit(@RequestBody Mono<CreditDto> creditDto) {
    Map<String, Object> result = new HashMap<>();
    return creditDto.flatMap(credit->{
      if(credit.getIdType().equals("typ01")){
        return this.creditService.getListCreditAll(credit.getIdClient(), credit.getIdType(), credit.getIdProduct(), credit.getNumberCuent())
            .flatMap(c -> {
              if(c.getId()==null){
                return this.creditService.getListCreditByIdClientAndIdTypeAndIdProduct(credit.getIdClient(), credit.getIdType(), credit.getIdProduct())
                    .flatMap(product->{
                      if(product.getId()==null){
                        return this.creditService.saveCredit(Mono.just(credit)).flatMap(creditSave->{
                          ResponseDto res=new ResponseDto();
                          res.setStatus(HttpStatus.CREATED.toString());
                          res.setMsg("Se Registro Correctamente");
                          res.setCreditDto(creditSave);
                          result.put("Data",res);
                          return Mono.just(result);
                        });
                      }else{
                        ResponseDto res=new ResponseDto();
                        res.setStatus(HttpStatus.NOT_FOUND.toString());
                        res.setMsg("No se Registro; porque ya tiene una cuenta asociada a " + product.getIdProduct());
                        result.put("Data",res);
                        return Mono.just(result);
                      }
                    });
              }else{
                ResponseDto res=new ResponseDto();
                res.setStatus(HttpStatus.NOT_FOUND.toString());
                res.setMsg("No se Registro; porque ya tiene un numero de cuenta " + c.getNumberCuent());
                result.put("Data",res);
                return Mono.just(result);
              }

            });
      }else if(credit.getIdType().equals("typ02")){
        if(credit.getIdProduct().equals("pro02")){
          return this.creditService.getListCreditAll(credit.getIdClient(), credit.getIdType(), credit.getIdProduct(), credit.getNumberCuent())
              .flatMap(k->{
                    if(k.getId()==null){
                      return this.creditService.saveCredit(Mono.just(credit)).flatMap(creditSave->{
                        ResponseDto res=new ResponseDto();
                        res.setStatus(HttpStatus.CREATED.toString());
                        res.setMsg("Se Registro Correctamente");
                        res.setCreditDto(creditSave);
                        result.put("Data",res);
                        return Mono.just(result);
                      });
                    }else{
                      ResponseDto res=new ResponseDto();
                      res.setStatus(HttpStatus.NOT_FOUND.toString());
                      res.setMsg("Ya cuenta con un producto ");
                      result.put("Data",res);
                      return Mono.just(result);
                    }
            });
        }else {
          ResponseDto res=new ResponseDto();
          res.setStatus(HttpStatus.NOT_FOUND.toString());
          res.setMsg("Type de cliente no puede crear estos productos");
          result.put("Data",res);
          return Mono.just(result);
        }

      }else{
        ResponseDto res=new ResponseDto();
        res.setStatus(HttpStatus.NOT_FOUND.toString());
        res.setMsg("No Existe Tipo de Cliente");
        result.put("Data",res);
        return Mono.just(result);
      }

    });

    //return this.creditService.saveCredit(creditDto);
  }

  @PutMapping("/{id}")
  public Mono<CreditDto> updateCredit(@RequestBody Mono<CreditDto> creditDto,
                                      @PathVariable String id) {
    return this.creditService.updateCredit(creditDto, id);
  }
  @PostMapping("/deposit")
  public Mono<CreditDto> updateCreditDeposit(@RequestBody Mono<CreditDto> creditDto) {
    return creditDto.flatMap(credit -> {
      return this.creditService.getCreditById(credit.getId()).flatMap(c->{
          CreditDto cre=new CreditDto();
          cre.setId(credit.getId());
          cre.setIdClient(credit.getIdClient());
          cre.setIdType(credit.getIdType());
          cre.setIdProduct(credit.getIdProduct());
          cre.setNumberCuent(credit.getNumberCuent());
          cre.setBalance(c.getBalance() + credit.getBalance());
          System.out.println(c.getBalance() + credit.getBalance());
        return this.creditService.updateCredit(Mono.just(cre), credit.getId());
      });
    });
  }

  @PostMapping("/withdrawal")
  public Mono<CreditDto> updateCreditWithdrawal(@RequestBody Mono<CreditDto> creditDto) {
    return creditDto.flatMap(credit -> {
      return this.creditService.getCreditById(credit.getId()).flatMap(c->{
        CreditDto cre=new CreditDto();
        cre.setId(credit.getId());
        cre.setIdClient(credit.getIdClient());
        cre.setIdType(credit.getIdType());
        cre.setIdProduct(credit.getIdProduct());
        cre.setNumberCuent(credit.getNumberCuent());
        cre.setBalance(c.getBalance() - credit.getBalance());
        return this.creditService.updateCredit(Mono.just(cre), credit.getId());
      });
    });
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deleteCreditById(@PathVariable("id") String id) {
    return this.creditService.deleteCreditById(id);
  }

  @GetMapping("/operation/{id}")
  public Flux<CreditDto> getListByIdClient(@PathVariable("id") String id) {
    return this.creditService.getListByIdClient(id);
  }

  @GetMapping("/products/staff/{idClient}/{idType}/{idProduct}")
  public Mono<CreditDto> getListByStaff(@PathVariable String idClient,
                                        @PathVariable String idType,
                                        @PathVariable String idProduct) {
    return this.creditService.getListCreditByIdClientAndIdTypeAndIdProduct(idClient, idType, idProduct);
  }

  @GetMapping("/products/{idClient}/{idType}/{idProduct}/{numberCuent}")
  public Mono<CreditDto> getListByIdClient(@PathVariable String idClient,
                                           @PathVariable String idType,
                                           @PathVariable String idProduct,
                                           @PathVariable String numberCuent) {
    return this.creditService.getListCreditAll(idClient, idType, idProduct, numberCuent);
  }

  @GetMapping("/products/{idclient}")
  public Flux<CreditDto> getListCreditByIdClient(@PathVariable("idclient") String idclient) {
    return this.creditService.getListCreditByIdClient(idclient);
  }

  @GetMapping("/products/{idclient}/{idtype}/{idproduct}")
  public Flux<CreditDto> getListByIdClient(@PathVariable("idclient") String idclient,
                                           @PathVariable("idclient") String idtype,
                                           @PathVariable("idproduct") String idproduct) {
    return this.creditService.getListCreditByIdClientAndIdProduct(idclient, idtype, idproduct);
  }


  @PostMapping("/products")
  public Mono<Datasource> updateCreditReportIntervalDay(@RequestBody Mono<CreditDto> creditDto) {

    List<CreditDto> list3 = new ArrayList<>();
    CreditDto credito=new CreditDto();



    Mono<List<CreditDto>> flux= creditDto.flatMap(credit->{
      return this.creditService.getListCreditByIdClientAndIdProduct(credit.getIdClient(),credit.getIdType(), credit.getIdProduct()).flatMap(b->{
        CreditDto vvv=new CreditDto();
        vvv.setId(b.getId());
        vvv.setIdClient(b.getIdClient());
        vvv.setIdType(b.getIdType());
        vvv.setIdProduct(b.getIdProduct());
        vvv.setNumberCuent(b.getNumberCuent());
        vvv.setBalance(b.getBalance());
        //vvv.setReportIntervalDay(credit.getReportIntervalDay());
        return this.creditService.updateCredit(Mono.just(vvv), b.getId());
      }).collectList();
    });

    return    flux.flatMap(f->{
      //System.out.println(f);

      f.forEach((v)->{
        CreditDto cv=new CreditDto();
        cv.setId(v.getId());
        cv.setIdClient(v.getIdClient());
        cv.setIdType(v.getIdType());
        cv.setIdProduct(v.getIdProduct());
        cv.setNumberCuent(v.getNumberCuent());
        cv.setBalance(v.getBalance());
        //cv.setReportIntervalDay(v.getReportIntervalDay());

        list3.add(cv);
      });
      Datasource data=new Datasource();
      data.setCredit(list3);
      //System.out.println(data.getLista());
      return Mono.just(data);
    });
  }

  /*
  @PostMapping("/products")
  public Mono<Datasource> updateCreditReportIntervalDay(@RequestBody Mono<CreditDTO> creditDto) {
    List<CreditDTO> list3 = new ArrayList<>();
    CreditDTO credito=new CreditDTO();



    Mono<List<CreditDTO>> flux= creditDto.flatMap(credit->{
      credito.setReportIntervalDay(credit.getReportIntervalDay());
      return this.creditService.getListCreditByIdClientAndIdProduct(credit.getIdClient(),credit.getIdType(), credit.getIdProduct()).collectList();
    });

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
        cv.setReportIntervalDay(11);
        this.creditService.updateCredit(Mono.just(cv), cv.getId());
        list3.add(cv);
      });
      Datasource data=new Datasource();
      data.setLista(list3);
      System.out.println(data.getLista());
      return Mono.just(data);
    });

  }
*/

  @GetMapping("/Products/{idClient}/{idType}/{idProduct}/{dateStart}/{dateEnd}")
  public Flux<CreditDto> getListProductRangeDate(@PathVariable String idClient,
                                                 @PathVariable String idType,
                                                 @PathVariable String idProduct,
                                                 @PathVariable String dateStart,
                                                 @PathVariable String dateEnd){


    return this.creditService.getListCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(idClient,idType,idProduct,dateStart,dateEnd);
  }


  @PostMapping("/WithoutDebt")
  public Mono<ResponseDto> saveCreditWithoutDebt(@RequestBody Mono<CreditDto> creditDto) {
    ResponseDto resp = new ResponseDto();
    List<CreditDto> creditD = new ArrayList<>();
    return creditDto.flatMap(credit -> {
      return this.creditService.getCreditByNumberCuent(credit.getNumberCuent()).flatMap(m -> {
        if (m.getId() == null) {
          return this.creditService.findByIdClientAndIdProductAndIdType(credit.getIdClient(), credit.getIdProduct(), credit.getIdType()).flatMap(yy -> {
            System.out.println(yy.getIdType());
            if (yy.getIdType() == null) {
              return this.saveCreditAll(Mono.just(credit));
            } else {
              if (credit.getIdType().equals("typ01")) {
                return this.creditService.getListCreditByIdClientAndIdTypeAndIdProductAndNumberCuent(credit.getIdClient(), credit.getIdType(), credit.getIdProduct(), credit.getNumberCuent()).flatMap(uu -> {
                  if (uu.getId() == null) {
                    return this.creditService.findByIdClientAndIdProductAndIdType(credit.getIdClient(), credit.getIdProduct(), credit.getIdType()).flatMap(jj -> {
                      if (jj.getId() == null) {
                        return this.saveCreditAll(Mono.just(credit));
                      } else {
                        if (jj.getIdType().equals("typ01")) {
                          resp.setStatus(HttpStatus.CREATED.toString());
                          resp.setMsg("El cliente de tipo personal solo puede tener un producto");
                          return Mono.just(resp);
                        } else {
                          return this.saveCreditAll(Mono.just(credit));
                        }
                      }
                    });
                  } else {
                    resp.setStatus(HttpStatus.NOT_FOUND.toString());
                    resp.setMsg("Tipo de cliente solo puede tener un producto");
                    return Mono.just(resp);
                  }
                });
              } else {
                return this.creditService.getListCreditByIdClientAndIdTypeAndIdProductAndNumberCuent(credit.getIdClient(), credit.getIdType(), credit.getIdProduct(), credit.getNumberCuent()).flatMap(uu -> {
                  System.out.println("Entro typ02");
                  if (uu.getId() == null) {
                    return this.saveCreditAll(Mono.just(credit));
                  } else {
                    resp.setStatus(HttpStatus.NOT_FOUND.toString());
                    resp.setMsg("Ya tiene una numero de cuenta");
                    return Mono.just(resp);
                  }
                });
              }
            }
          });
        } else {
          resp.setStatus(HttpStatus.NOT_FOUND.toString());
          resp.setMsg("ya exite esa cuenta");
          return Mono.just(resp);
          //return this.creditService.saveCredit(Mono.just(credit));
        }
      });

    });
  }

  private Mono<ResponseDto> saveCreditAll(Mono<CreditDto> creditDto){
    ResponseDto resp=new ResponseDto();
    return creditDto.flatMap(credito->{
      return this.getCount(creditDto).flatMap(bb->{
        if(bb.getCountDeuda() == 0){
          return this.creditService.saveCredit(Mono.just(credito)).flatMap(k->{
            resp.setStatus(HttpStatus.CREATED.toString());
            resp.setMsg("Se guardo Cuenta");
            resp.setCreditDto(k);
            return Mono.just(resp);
          });
        }else{
          resp.setStatus(HttpStatus.NOT_FOUND.toString());
          resp.setMsg("Cliente con Deuda");
          return Mono.just(resp);
        }

      });

    });
  }

  private Mono<CountDto> getCount(Mono<CreditDto> creditDto){
    List<CreditDto> list3 = new ArrayList<>();
    CountDto c=new CountDto();
    Mono<List<CreditDto>> lista=  creditDto.flatMap(bb->{

      return this.creditService.getListByIdClient(bb.getIdClient()).filter(gt->{
        return gt.getStatus()==3;
      }).collectList();
    });

    return lista.flatMap(f->{
      //System.out.println(f);

      f.forEach((v)->{
        CreditDto cv=new CreditDto();
        cv.setId(v.getId());
        cv.setIdClient(v.getIdClient());
        cv.setIdType(v.getIdType());
        cv.setIdProduct(v.getIdProduct());
        cv.setNumberCuent(v.getNumberCuent());
        cv.setBalance(v.getBalance());
        cv.setStatus(v.getStatus());
        cv.setCreationDate(v.getCreationDate());
        list3.add(cv);
      });

      c.setCountDeuda(list3.size());
      return Mono.just(c);
    });

    //return this.creditService.getListByIdClient("cli01");

  }

  @PostMapping("/associate/account")
  public Flux<ResponseListDto> associateAccount(@RequestBody  Flux<CreditDto> requestDtoMono){
    ResponseListDto responseDto=new ResponseListDto();
    CreditDto auxiliar=new CreditDto();
    return requestDtoMono.flatMap(credit->{
      return this.creditService.getListCreditByIdClientAndIdTypeAndIdProductAndNumberCuent(credit.getIdClient(),credit.getIdType(),credit.getIdProduct(),credit.getNumberCuent()).flatMap(g->{

          if(g.getId()==null){
            responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
            responseDto.setMsg("No existe el producto : " + credit.getNumberCuent());
            return Mono.just(responseDto);
          }else{
            CreditDto creditDto=new CreditDto();
            creditDto.setId(g.getId());
            creditDto.setIdClient(g.getIdClient());
            creditDto.setIdType(g.getIdType());
            creditDto.setIdProduct(g.getIdProduct());
            creditDto.setNumberCuent(g.getNumberCuent());
            creditDto.setBalance(g.getBalance());
            creditDto.setStatus(g.getStatus());
            creditDto.setCreationDate(g.getCreationDate());
            String cardNumero="";
            cardNumero=g.getIdClient().substring(3)+"-34444"+"-66666" + "-8888" ;
            creditDto.setNumberCard(cardNumero);
            creditDto.setCategory(credit.getCategory());
            return this.updateCardDebit(Mono.just(creditDto),g.getId());

          }
      });

    });
  }

   private Mono<ResponseListDto> updateCardDebit(Mono<CreditDto> creditDto, String id){
     ResponseListDto responseDto=new ResponseListDto();
     List<CreditDto> lista=new ArrayList<>();


       return this.creditService.updateCredit(creditDto, id).flatMap(update->{
         responseDto.setStatus(HttpStatus.CREATED.toString());
         if(update.getCategory() == 1){
           responseDto.setMsg("Se asocio tarjeta de debito a cuenta principal : " + update.getNumberCuent());
         }else{
           responseDto.setMsg("Se asocio tarjeta de debito a cuenta : " + update.getNumberCuent());
         }

         lista.add(update);
         responseDto.setListCreditDto(lista);

         return Mono.just(responseDto);
       });

   }

   @PostMapping("/search/balance/mainAccount")
   public Mono<ResponseDto> getBalanceMainAccount(@RequestBody Mono<CreditDto> creditDto){
    ResponseDto responseDto=new ResponseDto();
      return creditDto.flatMap(credit->{
          return this.creditService.getAccountMain(credit.getIdClient(),credit.getNumberCard(),credit.getCategory()).flatMap(b->{
            responseDto.setStatus(HttpStatus.CREATED.toString());
            responseDto.setMsg("ok");
            responseDto.setCreditDto(b);
            return Mono.just(responseDto);
          });
      });
   }


}
