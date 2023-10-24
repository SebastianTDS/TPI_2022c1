import com.fantasticos.studentservice.dto.DtoStudent;
import com.fantasticos.studentservice.dto.DtoUser;
import com.fantasticos.studentservice.dto.group.GroupCreateDto;
import com.fantasticos.studentservice.dto.group.GroupDto;
import com.fantasticos.studentservice.dto.group.GroupToAlgorithmDto;
import com.fantasticos.studentservice.dto.group.GroupUpdateDto;
import com.fantasticos.studentservice.exception.GroupExistException;
import com.fantasticos.studentservice.exception.GroupNotFoundException;
import com.fantasticos.studentservice.mapper.GroupMapper;
import com.fantasticos.studentservice.model.GroupEntity;
import com.fantasticos.studentservice.model.Student;
import com.fantasticos.studentservice.repository.GroupRepository;
import com.fantasticos.studentservice.repository.StudentRepository;
import com.fantasticos.studentservice.service.Impl.GroupServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class GroupUTest {

    @Mock
    private GroupRepository repository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private GroupMapper mapper;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity responseEntity;


    @InjectMocks
    private GroupServiceImpl service;

    private Student student1;
    private DtoStudent dtoStudent;
    private Student student2;
    private Set<Student> students;

    private GroupEntity group1;
    private GroupEntity group2;
    private GroupEntity group3;
    private GroupEntity groupUpdate;
    private GroupDto groupDto;
    private GroupDto groupDto2;
    private List<GroupEntity> groupEntityList;
    private List<GroupEntity> emptyList;
    private List<GroupDto> groupDtoList;
    private GroupCreateDto groupCreateDto;
    private GroupUpdateDto groupUpdateDto;

    private DtoUser dtoUser;

    @BeforeEach
    public void setUp() {

        student1 = new Student();
        student1.setId("id1");

        dtoStudent = new DtoStudent();
        dtoStudent.setId("id1");

        student2 = new Student();
        student2.setId("id2");

        students = new HashSet<>();
        students.add(student1);

        group1 = new GroupEntity();
        group1.setId(1L);
        group1.setName("Los 4 fantasticos");
        group1.setDescription("grupo de 4 integrantes");
        group1.setCareer("Tec.Desarrollo Web");
        group1.setSubject("Web 1");
        group1.setClosed(true);
        group1.setStudents(students);

        group2 = new GroupEntity();
        group2.setId(2L);
        group2.setName("TELE Y TRABAJO");
        group2.setDescription("Practicas de laboratorio 2");
        group2.setCareer("Tec.Desarrollo Web");
        group2.setSubject("Web 1");
        group2.setClosed(false);

        group3 = new GroupEntity();
        group3.setId(3L);
        group3.setName("RASPANDO APROBADOS");
        group3.setDescription("Practicas de laboratorio 3");
        group3.setCareer("Tec.Desarrollo Web");
        group3.setSubject("Web 1");
        group3.setClosed(true);

        groupDto = new GroupDto();
        groupDto.setId(group1.getId());
        groupDto.setName(group1.getName());
        groupDto.setDescription(group1.getDescription());
        groupDto.setCareer(group1.getCareer());
        groupDto.setSubject(group1.getSubject());
        groupDto.setClosed(group1.getClosed());

        groupDto2 = new GroupDto();
        groupDto2.setId(group3.getId());
        groupDto2.setName(group3.getName());
        groupDto2.setDescription(group3.getDescription());
        groupDto2.setCareer(group3.getCareer());
        groupDto2.setSubject(group3.getSubject());
        groupDto2.setClosed(group3.getClosed());

        groupUpdate = new GroupEntity();
        groupUpdate.setId(3L);
        groupUpdate.setName("RASPANDO APROBADOS");
        groupUpdate.setDescription("cambiando la info");
        groupUpdate.setCareer("Ing.informatica");
        groupUpdate.setSubject("Web 1");
        groupUpdate.setClosed(true);

        groupCreateDto = new GroupCreateDto();
        groupCreateDto.setName(group1.getName());
        groupCreateDto.setDescription(group1.getDescription());
        groupCreateDto.setCareer(group1.getCareer());
        groupCreateDto.setSubject(group1.getSubject());

        groupUpdateDto = new GroupUpdateDto();
        groupUpdateDto.setName(group3.getName());
        groupUpdateDto.setDescription(group3.getDescription());
        groupUpdateDto.setCareer(group3.getCareer());
        groupUpdateDto.setSubject(group3.getSubject());

        groupEntityList = new ArrayList<>();
        groupEntityList.add(group1);
        groupEntityList.add(group3);

        groupDtoList = new ArrayList<>();
        groupDtoList.add(groupDto);
        groupDtoList.add(groupDto2);

        emptyList = new ArrayList<>();

        dtoUser = new DtoUser();
        dtoUser.setUserName("id1");
    }

    @Test
    void itShouldBringTheGroupById() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findById(group1.getId())).thenReturn(Optional.of(group1));
        when(mapper.groupToGroupDto(group1)).thenReturn(groupDto);
        GroupDto actual = service.findById(group1.getId());

        log.info("Result: " + actual);

        assertThatActualIsEqualToExpected(actual, group1);
    }

    @Test
    void willThrowWhenTheGroupDoesNotExist() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findById(group1.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(group1.getId()))
                .isInstanceOf(GroupNotFoundException.class)
                .hasMessageContaining("The group does not exist");
    }

    @Test
    void itShouldBringAllGroups() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findAll()).thenReturn(groupEntityList);
        when(mapper.groupListToGroupListDto(groupEntityList)).thenReturn(groupDtoList);
        List<GroupDto> actualList = service.findAll();

        log.info("Result: " + actualList);

        assertThatActualIsEqualToExpected(actualList.get(0), groupEntityList.get(0));
        assertThatActualIsEqualToExpected(actualList.get(1), groupEntityList.get(1));
        assertThat(actualList.size()).isEqualTo(groupEntityList.size());
    }

    @Test
    void willThrowWhenNoGroupsAreFound() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findAll()).thenReturn(emptyList);

        assertThatThrownBy(() -> service.findAll()).isInstanceOf(GroupNotFoundException.class)
                .hasMessageContaining("There were no groups found");
    }

//    @Test
//    void itShouldCreateAGroupSuccessfully() {
//        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
//
//        when(mapper.groupCreateDtoToGroupEntity(groupCreateDto)).thenReturn(group1);
//        when(studentRepository.findById(student1.getId())).thenReturn(Optional.of(student1));
//
//        GroupToAlgorithmDto ga = new GroupToAlgorithmDto();
//        ga.setIdGrupo(group1.getId());
//        ga.setIdAdmin(student1.getId());
//
//        RequestEntity<GroupToAlgorithmDto> request = RequestEntity.post(URI.create("http://localhost:8761/algorithm/group/cargar"))
//                .header("X-USER-INNER",student1.getId())
//                .body(ga);
//
//        when(restTemplate.exchange(request,GroupToAlgorithmDto.class)).thenReturn(responseEntity);
//
//        service.save(groupCreateDto, dtoUser.getUserName()));
//
//        ArgumentCaptor<GroupEntity> groupArgumentCaptor = ArgumentCaptor.forClass(GroupEntity.class);
//
//        verify(repository, times(1)).save(groupArgumentCaptor.capture());
//        GroupEntity capturedGroup = groupArgumentCaptor.getValue();
//
//        log.info("Result: " + groupCreateDto);
//
//        assertThat(capturedGroup.getName()).isEqualTo(groupCreateDto.getName());
//        assertThat(capturedGroup.getDescription()).isEqualTo(groupCreateDto.getDescription());
//        assertThat(capturedGroup.getCareer()).isEqualTo(groupCreateDto.getCareer());
//    }

//    @Test
//    void willThrowWhenTheGroupAlreadyExist() {
//        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
//
//        when(repository.findByNameAndDescriptionAndCareerAndSubject(
//                group1.getName(),
//                group1.getDescription(),
//                group1.getCareer(),
//                group1.getSubject())).thenReturn(Optional.of(group1));
//
//        assertThatThrownBy(() -> service.save(groupCreateDto, dtoUser.getUserName()))
//                .isInstanceOf(GroupExistException.class)
//                .hasMessageContaining("The group already exist");
//    }

    @Test
    void itShouldUpdateAGroupWithTheSameDataAsAnExistingGroupSuccessfully() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findById(group3.getId())).thenReturn(Optional.of(group3));

        service.update(group3.getId(), groupUpdateDto);

        verify(repository, times(1)).findById(group3.getId());

        log.info("Result: " + group3);

        assertThatActualIsEqualToExpected(groupUpdateDto, group3);

    }

    @Test
    void itShouldUpdateAGroupWithDifferentDataAsAnExistingGroupSuccessfully() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        groupUpdateDto.setCareer(groupUpdate.getCareer());
        groupUpdateDto.setDescription(groupUpdate.getDescription());

        when(repository.findById(group3.getId())).thenReturn(Optional.of(group3));
        when(repository.findByIdNotAndNameAndDescriptionAndCareerAndSubject(group3.getId(),
                groupUpdateDto.getName(),
                groupUpdateDto.getDescription(),
                groupUpdateDto.getCareer(),
                groupUpdateDto.getSubject()))
                .thenReturn(Optional.empty());

        when(mapper.merge(group3,groupUpdateDto)).thenReturn(groupUpdate);

        service.update(group3.getId(), groupUpdateDto);

        ArgumentCaptor<GroupEntity> groupArgumentCaptor = ArgumentCaptor.forClass(GroupEntity.class);

        verify(repository, times(1)).findById(group3.getId());
        verify(repository, times(1))
                .findByIdNotAndNameAndDescriptionAndCareerAndSubject(group3.getId(),
                        groupUpdateDto.getName(),
                        groupUpdateDto.getDescription(),
                        groupUpdateDto.getCareer(),
                        groupUpdateDto.getSubject());

        verify(repository, times(1)).save(groupArgumentCaptor.capture());
        GroupEntity capturedGroup = groupArgumentCaptor.getValue();
        log.info("Result: " + capturedGroup);

        assertThatActualIsEqualToExpected(groupUpdateDto, capturedGroup);
        assertThat(capturedGroup.getCareer()).isNotEqualTo("Tec.Desarrollo Web");
        assertThat(capturedGroup.getDescription()).isNotEqualTo("Practicas de laboratorio 3");
    }

    @Test
    void willThrowIfThereIsAGroupWithEqualValidationData() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        groupUpdateDto.setName(group1.getName());
        groupUpdateDto.setDescription(group1.getDescription());
        groupUpdateDto.setCareer(group1.getCareer());
        groupUpdateDto.setSubject(group1.getSubject());

        when(repository.findById(group3.getId())).thenReturn(Optional.of(group3));
        when(repository.findByIdNotAndNameAndDescriptionAndCareerAndSubject(group3.getId(),
                groupUpdateDto.getName(),
                groupUpdateDto.getDescription(),
                groupUpdateDto.getCareer(),
                groupUpdateDto.getSubject()))
                .thenReturn(Optional.of(group1));

        assertThatThrownBy(() -> service.update(group3.getId(), groupUpdateDto))
                .isInstanceOf(GroupExistException.class)
                .hasMessageContaining("The group was not updated because another one already has those identification values");
    }

   /* @Test
    void itShouldDeleteAGroupSuccessfully() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findById(group1.getId())).thenReturn(Optional.of(group1));

        service.delete(group1.getId());

        verify(repository, times(1)).deleteById(group1.getId());
    }

    @Test
    void willThrowWhenTheGroupWantToDeleteDoesNotExist() {
        log.info("Running test: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        when(repository.findById(group1.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(group1.getId()))
                .isInstanceOf(GroupNotFoundException.class)
                .hasMessageContaining("The group you want to delete does not exist");
    }*/

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
