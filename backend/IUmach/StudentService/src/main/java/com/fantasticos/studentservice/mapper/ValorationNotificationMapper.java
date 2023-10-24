package com.fantasticos.studentservice.mapper;

import com.fantasticos.studentservice.dto.DtoValorationNotification;
import com.fantasticos.studentservice.model.ValorationNotification;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ValorationNotificationMapper {
    ValorationNotificationMapper INSTANCE= Mappers.getMapper(ValorationNotificationMapper.class);
    DtoValorationNotification valorationNotificationToDto(ValorationNotification valorationNotification);

    @InheritInverseConfiguration
    ValorationNotification dtoToValorationNotificationo(DtoValorationNotification dtoValorationNotification);
}
