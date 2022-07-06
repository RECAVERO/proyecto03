package com.nttdata.proyecto01.clientservice.utils;

import com.nttdata.proyecto01.clientservice.domain.models.ClientDto;
import com.nttdata.proyecto01.clientservice.infrastructure.data.documents.Client;
import org.springframework.beans.BeanUtils;

public class Convert {
  public static ClientDto entityToDto(Client client) {
    ClientDto clientDto = new ClientDto();
    BeanUtils.copyProperties(client, clientDto);
    return clientDto;
  }

  public static Client dtoToEntity(ClientDto clientDto) {
    Client client = new Client();
    BeanUtils.copyProperties(clientDto, client);
    return client;
  }
}
