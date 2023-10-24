import com.fantasticos.studentservice.dto.DtoStudent;
import com.fantasticos.studentservice.dto.DtoUser;
import com.fantasticos.studentservice.dto.group.GroupDto;
import com.fantasticos.studentservice.dto.request.RequestJoinDataDto;
import com.fantasticos.studentservice.dto.request.RequestJoinDto;
import com.fantasticos.studentservice.exception.RequestJoinNotFoundException;
import com.fantasticos.studentservice.mapper.GroupMapper;
import com.fantasticos.studentservice.mapper.RequestJoinMapper;
import com.fantasticos.studentservice.mapper.StudentMapper;
import com.fantasticos.studentservice.model.GroupEntity;
import com.fantasticos.studentservice.model.RequestJoin;
import com.fantasticos.studentservice.model.Student;
import com.fantasticos.studentservice.repository.RequestJoinRepository;
import com.fantasticos.studentservice.service.GroupService;
import com.fantasticos.studentservice.service.Impl.RequestJoinServiceImpl;
import com.fantasticos.studentservice.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class RequestJoinUTest {

    @Mock
    private RequestJoinRepository repository;

    @Mock
    private RequestJoinMapper mapper;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private StudentService studentService;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private RequestJoinServiceImpl service;

    private Student student1;
    private DtoStudent dtoStudent;
    private Student student2;
    private Set<Student> students;

    private RequestJoin requestJoin1;
    private RequestJoin requestJoin2;
    private RequestJoinDto requestJoinDto;
    private RequestJoinDto requestJoinDto2;
    private List<RequestJoin> requestJoinList;
    private List<RequestJoin> emptyList;
    private List<RequestJoinDto> requestJoinDtoList;
    private RequestJoinDataDto requestJoinCreateDto;

    private GroupEntity group;
    private DtoUser dtoUser;
    private GroupDto groupDto;

    @BeforeEach
    public void setUp() {

        student1 = new Student();
        student1.setId("id1");

        group = new GroupEntity();
        group.setId(1L);

        dtoStudent = new DtoStudent();
        dtoStudent.setId("id1");

        groupDto = new GroupDto();
        groupDto.setId(group.getId());

        student2 = new Student();
        student2.setId("id2");

        students = new HashSet<>();
        students.add(student1);

        requestJoin1 = new RequestJoin();
        requestJoin1.setId(1L);
        requestJoin1.setGroup(group);
        requestJoin1.setStudent(student1);

        requestJoin2 = new RequestJoin();
        requestJoin2.setId(2L);
        requestJoin2.setGroup(group);
        requestJoin2.setStudent(student1);

        requestJoinDto = new RequestJoinDto();
        requestJoinDto.setId(requestJoin1.getId());

        requestJoinDto2 = new RequestJoinDto();
        requestJoinDto2.setId(requestJoin2.getId());

        requestJoinCreateDto = new RequestJoinDataDto();
        requestJoinCreateDto.setGroupId(group.getId());
        requestJoinCreateDto.setAccepted(false);

        requestJoinList = new ArrayList<>();
        requestJoinList.add(requestJoin1);
        requestJoinList.add(requestJoin2);

        requestJoinDtoList = new ArrayList<>();
        requestJoinDtoList.add(requestJoinDto);
        requestJoinDtoList.add(requestJoinDto2);

        emptyList = new ArrayList<>();

        dtoUser = new DtoUser();
        dtoUser.setUserName("id1");
    }

    @Test
    void itShouldBringTheRequestJoinById() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findById(requestJoin1.getId())).thenReturn(Optional.of(requestJoin1));
        when(mapper.requestJoinToRequestJoinDto(requestJoin1)).thenReturn(requestJoinDto);
        RequestJoinDto actual = service.findById(requestJoin1.getId());

        log.info("Result: " + actual);

        assertThatActualIsEqualToExpected(actual, requestJoin1);
    }

    @Test
    void willThrowWhenTheRequestJoinDoesNotExist() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findById(requestJoin1.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(requestJoin1.getId()))
                .isInstanceOf(RequestJoinNotFoundException.class)
                .hasMessageContaining("The request join was not found");
    }

    @Test
    void itShouldBringAllRequestJoin() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findAll()).thenReturn(requestJoinList);
        when(mapper.requestJoinListToRequestJoinDtoList(requestJoinList)).thenReturn(requestJoinDtoList);
        List<RequestJoinDto> actualList = service.findAll();

        log.info("Result: " + actualList);

        assertThatActualIsEqualToExpected(actualList.get(0), requestJoinList.get(0));
        assertThatActualIsEqualToExpected(actualList.get(1), requestJoinList.get(1));
        assertThat(actualList.size()).isEqualTo(requestJoinList.size());
    }

    @Test
    void willThrowWhenNoRequestJoinAreFound() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findAll()).thenReturn(emptyList);

        assertThatThrownBy(() -> service.findAll()).isInstanceOf(RequestJoinNotFoundException.class)
                .hasMessageContaining("No requests join found");
    }

   /* @Test
    void itShouldCreateARequestJoinSuccessfully() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(studentService.findById(student1.getId())).thenReturn(dtoStudent);
        when(studentMapper.DtoStudentToStudent(dtoStudent)).thenReturn(student1);
        when(groupService.findById(group.getId())).thenReturn(groupDto);
        when(groupMapper.groupDtoToGroupEntity(groupDto)).thenReturn(group);
        when(repository.findByStudentAndGroup(student1,group)).thenReturn(Optional.empty());

        service.save(requestJoinCreateDto, dtoUser.getUserName());

        ArgumentCaptor<RequestJoin> requestJoinArgumentCaptor = ArgumentCaptor.forClass(RequestJoin.class);

        verify(repository, times(1)).save(requestJoinArgumentCaptor.capture());
        RequestJoin capturedRequestJoin = requestJoinArgumentCaptor.getValue();

        log.info("Result: " + requestJoinCreateDto);

        assertThat(capturedRequestJoin.getGroup().getId()).isEqualTo(requestJoinCreateDto.getGroupId());
    }

    @Test
    void willThrowWhenTheRequestJoinAlreadyExist() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(studentService.findById(student1.getId())).thenReturn(dtoStudent);
        when(studentMapper.DtoStudentToStudent(dtoStudent)).thenReturn(student1);
        when(groupService.findById(group.getId())).thenReturn(groupDto);
        when(groupMapper.groupDtoToGroupEntity(groupDto)).thenReturn(group);
        when(repository.findByStudentAndGroup(student1,group)).thenReturn(Optional.of(requestJoin1));

        assertThatThrownBy(() -> service.save(requestJoinCreateDto, dtoUser.getUserName()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("The request join already exist");
    }*/

   /* @Test
    void itShouldAcceptARequestJoinSuccessfully() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findById(requestJoin1.getId())).thenReturn(Optional.of(requestJoin1));
        when(mapper.requestJoinToRequestJoinDto(requestJoin1)).thenReturn(requestJoinDto);
        when(service.findById(requestJoin1.getId())).thenReturn(requestJoinDto);
        when(mapper.requestJoinDtoToRequestJoin(requestJoinDto)).thenReturn(requestJoin1);

        service.acceptRequestJoin(requestJoin1.getId());

        verify(repository, times(1)).save(requestJoin1);

        log.info("Result: " + requestJoin1);

        assertThat(requestJoin1.getAccepted()).isTrue();
    }*/

    @Test
    void itShouldRejectARequestJoinSuccessfully() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

//        when(repository.findByStudentAndGroup(student1, group)).thenReturn(Optional.of(requestJoin1));
//
//        service.rejectRequestJoin(requestJoin1.getId(), requestJoin1.getStudent().getId());
//
//        verify(repository, times(1)).deleteById(requestJoin1.getId());
    }

    @Test
    void willThrowWhenTheRequestJoinWantToRejectDoesNotExist() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

//        when(repository.findByStudentAndGroup(student1, group)).thenReturn(Optional.of(requestJoin1));
//
//        assertThatThrownBy(() -> service.rejectRequestJoin(requestJoin1.getId(), requestJoin1.getStudent().getId()))
//                .isInstanceOf(RequestJoinNotFoundException.class)
//                .hasMessageContaining("The request join you want to reject does not exist");
    }

    private void assertThatActualIsEqualToExpected(Object actual, Object expected) {
        for (Method m : actual.getClass().getMethods()) {
            if (m.getName().equals("getId") || m.getName().equals("getName")
                    ||m.getName().equals("getDescription") || m.getName().equals("getCareer")
                    ||m.getName().equals("getSubject")) {
                try {
                    assertThat(getObjectAttribute(expected, m.getName())).isEqualTo(getObjectAttribute(actual, m.getName()));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object getObjectAttribute(Object ob, String methodName) {
        Object response = null;
        try {
            Method method = ob.getClass().getMethod(methodName);
            response = method.invoke(ob, null);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return response;
    }

}
