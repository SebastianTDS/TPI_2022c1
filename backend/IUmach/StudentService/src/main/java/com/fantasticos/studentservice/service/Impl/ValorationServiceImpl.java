package com.fantasticos.studentservice.service.Impl;

import com.fantasticos.studentservice.dto.DtoValorationCreatedRequest;
import com.fantasticos.studentservice.dto.DtoValorationNotification;
import com.fantasticos.studentservice.dto.DtoValorationRequest;
import com.fantasticos.studentservice.mapper.ValorationNotificationMapper;
import com.fantasticos.studentservice.model.Student;
import com.fantasticos.studentservice.model.ValorationNotification;
import com.fantasticos.studentservice.repository.StudentRepository;
import com.fantasticos.studentservice.repository.ValorationRepository;
import com.fantasticos.studentservice.service.ValorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValorationServiceImpl implements ValorationService {

    private final ValorationRepository valorationRepository;
    private final StudentRepository studentRepository;
    private final ValorationNotificationMapper valorationNotificationMapper;

    @Override
    public List<DtoValorationNotification> getValorationNotification(String userName) {
        List<ValorationNotification> valorationNotification = valorationRepository.findAllByidValuationOut(userName);
        List<DtoValorationNotification> dtoValorationNotifications = new ArrayList<>();
        valorationNotification.forEach(valoration ->
                dtoValorationNotifications.add(
                        valorationNotificationMapper.valorationNotificationToDto(valoration)));

        return dtoValorationNotifications;
    }

    @Override
    public void valoration(DtoValorationRequest valorationRequest) {
        Long valoracion = 0l;
        Optional<Student> student = studentRepository.findById(valorationRequest.getValuationIn());
        if (valorationRequest.isResponsable()) {
            valoracion++;
            student.get().setResponsable(student.get().getResponsable() + 1);
        }
        if (valorationRequest.isColaborador()) {
            valoracion++;
            student.get().setColaborador(student.get().getColaborador() + 1);
        }
        if (valorationRequest.isProActivo()) {
            valoracion++;
            student.get().setProActivo(student.get().getProActivo() + 1);
        }
        if (valorationRequest.isOrganizado()) {
            valoracion++;
            student.get().setOrganizado(student.get().getOrganizado() + 1);
        }
        if (valorationRequest.isConocedor()) {
            valoracion++;
            student.get().setConocedor(student.get().getConocedor() + 1);
        }
        if (valorationRequest.isDetallista()) {
            valoracion++;
            student.get().setDetallista(student.get().getDetallista() + 1);
        }
        student.get().setValoration(student.get().getValoration() + valoracion * 20);
        studentRepository.save(student.get());
        valorationRepository.deleteById(valorationRequest.getIdValorationNotification());
    }

    @Override
    public void create(DtoValorationCreatedRequest dtoValorationCreatedRequest) {
        List<ValorationNotification> valorationNotifications = new ArrayList<>();
        for (String idQualifier : dtoValorationCreatedRequest.getIdStudent()) {
            for (String idQualified : dtoValorationCreatedRequest.getIdStudent()) {
                if (idQualifier.equals(idQualified))
                    continue;
                Optional<Student> student = studentRepository.findById(idQualified);
                ValorationNotification valorationNotification = new ValorationNotification();
                valorationNotification.setDate(new Date());
                valorationNotification.setValuationIn(student.get());
                valorationNotification.setIdValuationOut(idQualifier);
                valorationNotification.setNameGroup(dtoValorationCreatedRequest.getNameGroup());
                valorationNotifications.add(valorationNotification);
            }
        }
        valorationRepository.saveAll(valorationNotifications);
    }

    @Override
    public void valorationLike(String userId) {
        Optional<Student> student = studentRepository.findById(userId);
        student.get().setContribuciones(student.get().getContribuciones() + 1);
        student.get().setValoration(student.get().getValoration() + 1);
        studentRepository.save(student.get());
    }
}
