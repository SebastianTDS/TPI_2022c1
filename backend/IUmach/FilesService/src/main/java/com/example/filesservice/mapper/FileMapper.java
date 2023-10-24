package com.example.filesservice.mapper;

import com.example.filesservice.dto.FileResponseDto;
import com.example.filesservice.model.File;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileMapper INSTANCE= Mappers.getMapper(FileMapper.class);

    FileResponseDto fileToFileResponseDto(File file);
}
