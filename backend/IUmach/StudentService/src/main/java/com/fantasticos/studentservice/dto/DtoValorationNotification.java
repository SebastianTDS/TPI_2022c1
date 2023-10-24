package com.fantasticos.studentservice.dto;

import com.fantasticos.studentservice.model.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DtoValorationNotification {
    private Long id;
    private String idValuationOut;
    private Student ValuationIn;
    private String nameGroup;
    private Date date;
}
