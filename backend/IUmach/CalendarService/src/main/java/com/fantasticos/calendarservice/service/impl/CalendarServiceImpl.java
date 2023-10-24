package com.fantasticos.calendarservice.service.impl;

import com.fantasticos.calendarservice.dto.*;
import com.fantasticos.calendarservice.exception.EventExistException;
import com.fantasticos.calendarservice.exception.EventNotFoundException;
import com.fantasticos.calendarservice.model.CalendarEntity;
import com.fantasticos.calendarservice.repository.CalendarRepository;
import com.fantasticos.calendarservice.service.CalendarService;
import com.fantasticos.calendarservice.util.Tipo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fantasticos.calendarservice.util.Apis.*;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository repository;
    private final EmailService emailService;

    @Override
    public void createEvent(CreateEventDto event, String bearerToken) {
        if (exist(event))
            throw new EventExistException("The event already exists");
        else {
            CalendarEntity eventToCreate = createEventDtoToEvent(event);
            Optional<GroupResponseDto> group = getGroupById(bearerToken, event.getIdGroup());
            Optional<DtoStudent> studentId = getUser(bearerToken);
            Optional<StudentResponseDto> student = getUserById(bearerToken,studentId.get().getUserName());
            sendNotification(bearerToken, group.get(), student.get());

            repository.save(eventToCreate);
        }
    }

    private boolean exist(CreateEventDto event) {
        return repository.findByNameAndDescription(
                event.getName(), event.getDescription()).isPresent();
    }

    private CalendarEntity createEventDtoToEvent(CreateEventDto createEventDto) {
        CalendarEntity event = new CalendarEntity();
        event.setName(createEventDto.getName());
        event.setDescription(createEventDto.getDescription());
        event.setEventDate(createEventDto.getEventDate());
        event.setIdGroup(createEventDto.getIdGroup());
        event.setSend(false);
        return event;
    }

    @Override
    public void updateEvent(EventUpdateDto event, String bearerToken) {
        Optional<CalendarEntity> wantedEvent = repository.findById(event.getId());
        if (wantedEvent.isEmpty())
            throw new EventNotFoundException("The event you want to update does not exist");
        if (!sameValues(wantedEvent.get(), event)) {
            if (existAnotherGroupWithThoseValues(event)) {
                throw new EventExistException("The event was not updated because another one already has those identification values");
            }
            CalendarEntity eventToUpdate = merge(wantedEvent.get(), event);
            Optional<GroupResponseDto> group = getGroupById(bearerToken, event.getIdGroup());
            Optional<DtoStudent> studentId = getUser(bearerToken);
            Optional<StudentResponseDto> student = getUserById(bearerToken,studentId.get().getUserName());
            sendNotification(bearerToken, group.get(), student.get());
            repository.save(eventToUpdate);
        }
    }

    private boolean sameValues(CalendarEntity wantedEvent, EventUpdateDto event) {
        return wantedEvent.getName().equals(event.getName()) &&
                wantedEvent.getDescription().equals(event.getDescription());
    }

    private boolean existAnotherGroupWithThoseValues(EventUpdateDto event) {
        return repository.findByIdNotAndNameAndDescription(event.getId(),
                event.getName(), event.getDescription()).isPresent();
    }

    private CalendarEntity merge(CalendarEntity event, EventUpdateDto eventUpdateDto) {
        event.setName(eventUpdateDto.getName());
        event.setDescription(eventUpdateDto.getDescription());
        event.setEventDate(event.getEventDate());
        event.setIdGroup(eventUpdateDto.getIdGroup());
        return event;
    }

    private void sendNotification(String bearerToken, GroupResponseDto group, StudentResponseDto student) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setIdGroup(group.getId());
        notificationDto.setTipoNotification(Tipo.RECORDATORIO);
        notificationDto.setNameGroup(group.getName());
        notificationDto.setNameNotificante(student.getFirstName()
                + " " + student.getLastName());
        for (StudentResponseDto s : group.getStudents()) {
            notificationDto.setIdStudent(s.getId());
            notificationDto.setIdStudentNotification(s.getId());

            createNotification(bearerToken, notificationDto);
        }
    }

    @Override
    public void deleteEvent(Long id) {
        Optional<CalendarEntity> event = repository.findById(id);
        if (event.isEmpty())
            throw new EventNotFoundException("The event you want to delete does not exist");
        repository.deleteById(id);
    }

    @Override
    public List<EventDto> findAll(Long idGroup) {
        List<CalendarEntity> eventList = repository.findAllByIdGroupOrderByEventDate(idGroup);
        if (eventList.isEmpty())
            throw new EventNotFoundException("There were no events found");
        List<EventDto> list = new ArrayList<>();
        for (CalendarEntity event : eventList) {
            list.add(mapCalendarEntityToEventDto(event));
        }
        return list;
    }

    @Override
    public void sendEventsForReminder(String bearerToken) {

        List<CalendarEntity> eventList = repository.findAllEventsForReminder();

        for (CalendarEntity event : eventList) {
            Optional<GroupResponseDto> group = getGroupById(bearerToken, event.getIdGroup());
            if (!event.getSend()) {
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd LLLL yyyy");
                String stringDateFormat = dateFormat.format(event.getEventDate());
                for (StudentResponseDto student : group.get().getStudents()) {
                    emailService.send(student.getEmail(),
                            buildEmail(group.get().getName(), stringDateFormat, event.getName()), event.getName());
                }
            }
            event.setSend(true);
        }

    }

    @Override
    public void deleteEventsForTimeExpired() {
        repository.deleteEventsForTimeExpired();
    }

    private EventDto mapCalendarEntityToEventDto(CalendarEntity event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setDescription(event.getDescription());
        eventDto.setEventDate(event.getEventDate());
        eventDto.setIdGroup(event.getIdGroup());
        return eventDto;
    }

    private String buildEmail(String nameGroup, String eventDate, String nameEvent) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                "  <title>Email Confirmation</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "  <style type=\"text/css\">\n" +
                "  @media screen {\n" +
                "    @font-face {\n" +
                "      font-family: 'Source Sans Pro';\n" +
                "      font-style: normal;\n" +
                "      font-weight: 400;\n" +
                "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://github.com/sahibjotsaggu/San-Francisco-Pro-Fonts/blob/master/SF-Pro-Display-Regular.otf) format('otf');\n" +
                "    }\n" +
                "\n" +
                "    @font-face {\n" +
                "      font-family: 'Source Sans Pro';\n" +
                "      font-style: normal;\n" +
                "      font-weight: 700;\n" +
                "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://github.com/sahibjotsaggu/San-Francisco-Pro-Fonts/blob/master/SF-Pro-Rounded-Bold.otf) format('otf');\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  body,\n" +
                "  table,\n" +
                "  td,\n" +
                "  a {\n" +
                "    -ms-text-size-adjust: 100%; /* 1 */\n" +
                "    -webkit-text-size-adjust: 100%; /* 2 */\n" +
                "  }\n" +
                "\n" +
                "  table,\n" +
                "  td {\n" +
                "    mso-table-rspace: 0pt;\n" +
                "    mso-table-lspace: 0pt;\n" +
                "  }\n" +
                "\n" +
                "  img {\n" +
                "    -ms-interpolation-mode: bicubic;\n" +
                "  }\n" +
                "\n" +
                "  a[x-apple-data-detectors] {\n" +
                "    font-family: inherit !important;\n" +
                "    font-size: inherit !important;\n" +
                "    font-weight: inherit !important;\n" +
                "    line-height: inherit !important;\n" +
                "    color: inherit !important;\n" +
                "    text-decoration: none !important;\n" +
                "  }\n" +
                "\n" +
                "  .preheader[style*=\"margin: 16px 0;\"] {\n" +
                "    margin: 0 !important;\n" +
                "  }\n" +
                "\n" +
                "  body {\n" +
                "    width: 100% !important;\n" +
                "    height: 100% !important;\n" +
                "    padding: 0 !important;\n" +
                "    margin: 0 !important;\n" +
                "  }\n" +
                "  table {\n" +
                "    border-collapse: collapse !important;\n" +
                "  }\n" +
                "\n" +
                "  a {\n" +
                "    background-color: #1a82e2;\n" +
                "  }\n" +
                "\n" +
                "  img {\n" +
                "    height: auto;\n" +
                "    line-height: 100%;\n" +
                "    text-decoration: none;\n" +
                "    border: 0;\n" +
                "    outline: none;\n" +
                "  }\n" +
                "\n" +
                "a{\n" +
                "color: white !important ;\n" +
                "text-decoration: none !important ; \n" +
                "font-size: larger !important; \n" +
                "padding:10px 30px !important; \n" +
                "display: block !important;\n" +
                "border-radius: 6px;\n" +
                "}\n" +
                "  </style>\n" +
                "\n" +
                "</head>\n" +
                "<body style=\"background-color: #e9ecef;\">\n" +
                "\n" +
                "  <!-- start preheader -->\n" +
                "  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\n" +
                "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n" +
                "  </div>\n" +
                "  <!-- end preheader -->\n" +
                "\n" +
                "  <!-- start body -->\n" +
                "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "\n" +
                "    <!-- start logo -->\n" +
                "    <tr>\n" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "          <tr>\n" +
                "            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n" +
                "                <img src=\"https://i.imgur.com/YiJBcQn.png\" alt=\"Logo\" border=\"0\" style=\"display: block; width: 500px; max-width: 400px; min-width: 48px;\">\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n" +
                "              <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Recordatorio del evento " + nameEvent + "</h1>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <!-- end hero -->\n" +
                "\n" +
                "    <!-- start copy block -->\n" +
                "    <tr>\n" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "\n" +
                "          <!-- start copy -->\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                "              <p style=\"margin: 0;\">Le recordamos que su grupo (" + nameGroup + ") tiene programado un evento para el día " + eventDate + "</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end copy -->      \n" +
                "\n" +
                "          <!-- start copy -->\n" +
                "          <tr>\n" +
                "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\n" +
                "              <p style=\"margin: 0;\">Saludos,<br> El equipo de Iumach.</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end copy -->\n" +
                "\n" +
                "        </table>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <!-- end copy block -->\n" +
                "\n" +
                "    <!-- start footer -->\n" +
                "    <tr>\n" +
                "      <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 24px;\">\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "\n" +
                "          <!-- start unsubscribe -->\n" +
                "          <tr>\n" +
                "            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
                "              <p style=\"margin: 0;\">Florencio Varela 1903, B1754 San Justo, Provincia de Buenos Aires</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <!-- end unsubscribe -->\n" +
                "\n" +
                "        </table>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <!-- end footer -->\n" +
                "\n" +
                "  </table>\n" +
                "  <!-- end body -->\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }
}
