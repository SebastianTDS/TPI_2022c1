package com.fantasticos.studentservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValorationNotification {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String idValuationOut;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "valuation_in_id")
    private Student ValuationIn;
    private String nameGroup;
    private Date date=new Date();
}
