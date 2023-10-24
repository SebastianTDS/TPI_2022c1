package com.fantasticos.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DtoValorationCreatedRequest {
    private String nameGroup;
    private List<String>  idStudent;
}
