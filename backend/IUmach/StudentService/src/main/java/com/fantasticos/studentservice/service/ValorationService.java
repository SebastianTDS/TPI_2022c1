package com.fantasticos.studentservice.service;

import com.fantasticos.studentservice.dto.DtoValorationCreatedRequest;
import com.fantasticos.studentservice.dto.DtoValorationNotification;
import com.fantasticos.studentservice.dto.DtoValorationRequest;
import com.fantasticos.studentservice.model.ValorationNotification;

import java.util.List;


public interface ValorationService {

    List<DtoValorationNotification> getValorationNotification(String userName);

    void valoration(DtoValorationRequest valorationRequest);

    void create(DtoValorationCreatedRequest dtoValorationCreatedRequest);

    void valorationLike(String userId);
}
