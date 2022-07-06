package com.nttdata.proyecto01.creditservice.util.convert;

import com.nttdata.proyecto01.creditservice.domain.model.CreditDto;
import com.nttdata.proyecto01.creditservice.infraestructure.data.document.Credit;
import org.springframework.beans.BeanUtils;

public class Convert {
    public static CreditDto entityToDto(Credit credit){
        CreditDto creditDTO=new CreditDto();
        BeanUtils.copyProperties(credit,creditDTO);
        return creditDTO;
    }
    public static Credit DtoToEntity(CreditDto creditDTO){
        Credit credit=new Credit();
        BeanUtils.copyProperties(creditDTO,credit);
        return credit;
    }
}
