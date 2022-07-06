package com.nttdata.proyecto01.movementservice._5Utils.convert;

import com.nttdata.proyecto01.movementservice._3Domain.model.MovementDto;
import com.nttdata.proyecto01.movementservice._4Infraestructure.data.document.Movement;
import org.springframework.beans.BeanUtils;

public class Convert {
    public static MovementDto entityToDto(Movement movement){
        MovementDto movementDTO=new MovementDto();
        BeanUtils.copyProperties(movement,movementDTO);
        return movementDTO;
    }
    public static Movement DtoToEntity(MovementDto movementDTO){
        Movement movement=new Movement();
        BeanUtils.copyProperties(movementDTO,movement);
        return movement;
    }
}
