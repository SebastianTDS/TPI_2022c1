package com.example.notificationservice.service.imple;

import com.example.notificationservice.dto.DtoUser;
import com.example.notificationservice.dto.GroupResponseDto;
import com.example.notificationservice.dto.NotificationRequestDto;
import com.example.notificationservice.dto.StudentRequestDto;
import com.example.notificationservice.exeptions.GroupIdInvalidOrDoesNotExistException;
import com.example.notificationservice.exeptions.StudentDoestNotExistsOrWasEliminatedException;
import com.example.notificationservice.mapper.NotificationMapper;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.service.NotificationService;
import com.example.notificationservice.util.GroupMethods;
import com.example.notificationservice.util.StudentMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceNotificationImple implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public List<Notification> getNotification(String token) {
        Optional<DtoUser> dtoUser = StudentMethods.getMyId(token);
        if (dtoUser.isEmpty())
            throw new StudentDoestNotExistsOrWasEliminatedException("The student does not exist or was eliminated");
        return notificationRepository.findAllByIdStudentNotification(dtoUser.get().getUserName());
    }

    @Override
    public Notification createNotification(NotificationRequestDto requestDto, String token) {
        Optional<DtoUser> dtoUser1 = StudentMethods.getMyId(token);
        Optional<StudentRequestDto> dtoUser2 = StudentMethods.getUserById(token, requestDto.getIdStudent());
        Optional<GroupResponseDto> dtoGroup = GroupMethods.getGroupById(token, requestDto.getIdGroup());
        if (dtoUser1.isEmpty() || dtoUser2.isEmpty())
            throw new StudentDoestNotExistsOrWasEliminatedException("The student does not exist or was eliminated");
        if (dtoGroup.isEmpty())
            throw new GroupIdInvalidOrDoesNotExistException("The group id is invalid or does not exist");
        Notification notification = notificationMapper.requestDtoToNotification(requestDto);
        notification.setDateAt(new Date());
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long idNotification, String token) {
        Optional<DtoUser> dtoUser = StudentMethods.getMyId(token);
        notificationRepository.deleteById(idNotification);
    }
}
