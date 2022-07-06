package com.nttdata.proyecto01.typeclientservice.utils.convert;

import com.nttdata.proyecto01.typeclientservice.domain.model.TypeDto;
import com.nttdata.proyecto01.typeclientservice.infraestructure.data.document.Type;
import org.springframework.beans.BeanUtils;

public class Convert {

  public static TypeDto entityToDto(Type typeClient) {
    TypeDto typeDto = new TypeDto();
    BeanUtils.copyProperties(typeClient, typeDto);
    return typeDto;
  }

  public static Type dtoToEntity(TypeDto clientDto) {
    Type typeClient = new Type();
    BeanUtils.copyProperties(clientDto, typeClient);
    return typeClient;
  }
}
