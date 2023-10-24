package com.fantasticos.forumservice.service.impl;

import com.fantasticos.forumservice.dto.*;
import com.fantasticos.forumservice.exception.GroupIdInvalidOrDoesNotExistException;
import com.fantasticos.forumservice.exception.StudentDoestNotExistsOrWasEliminatedException;
import com.fantasticos.forumservice.exception.UserTryingToCreatePostDoesNotExistOrWasDeletedException;
import com.fantasticos.forumservice.mapper.PostMapper;
import com.fantasticos.forumservice.model.Post;
import com.fantasticos.forumservice.repository.PostRepository;
import com.fantasticos.forumservice.service.PostService;
import com.fantasticos.forumservice.util.PostSpecification;
import com.fantasticos.forumservice.util.Tipo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static com.fantasticos.forumservice.util.GroupMethods.getGroupById;
import static com.fantasticos.forumservice.util.GroupMethods.getGroupById2;
import static com.fantasticos.forumservice.util.StudentMethods.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostSpecification postSpecification;
    private final PostMapper postMapper;

    @Override
    public List<PostResponseDto> findAllGroupPost(String token, Long idGroup, Pageable page,FilterDto params) {
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        Optional<GroupResponseDto> groupFindById = getGroupById(token,idGroup);
        if(groupFindById.isEmpty())throw new GroupIdInvalidOrDoesNotExistException("The group id is invalid or does not exist");
        if (params != null) {
            Optional<Page<Post>> postResult = Optional.of(postRepository.findAll(postSpecification.specificationForGroup(params, idGroup), page));
            return getPostDtos(token, postResponseDtos, postResult);
        }else{
                Optional<Page<Post>> postResult = postRepository.findAllByIdGroup(page, idGroup);
                return getPostDtos(token, postResponseDtos, postResult);
            }
    }
    @Override
    public List<PostResponseDto> findAllGeneralPost(String token,FilterDto params, Pageable page) {
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        if (params.getIdUniversity()!=null || params.getIdDepartment()!=null || params.getIdCareer()!=null || params.getIdSubject()!=null || params.getIdTag()!=null || params.getName()!=null) {
            Optional<Page<Post>> postResult = Optional.of(postRepository.findAll(postSpecification.specificationForGeneral(params), page));
            return getPostDtos(token, postResponseDtos, postResult);
        } else {
            Optional<Page<Post>> postResult = postRepository.findAllByIdGroupIsNull(page);
            return getPostDtos(token, postResponseDtos, postResult);
        }
    }

    @Override
    public Post createPost(PostRequestDto postDto, String token) {
        Post post = postMapper.requestDtoToPost(postDto);
        Optional<DtoUser> postCreator =  getMyId(token);
        if(postCreator.isEmpty()) throw new UserTryingToCreatePostDoesNotExistOrWasDeletedException("The user trying to create the post does not exist or was deleted");
        post.setIdUser(postCreator.get().getUserName());
        if(postDto.getIdGroup()!=null){

            sendNotification(token,getMyUser(token).get(),getGroupById2(token,postDto.getIdGroup()).get());

        }
        return postRepository.save(post);
    }

    private List<PostResponseDto> getPostDtos(String token, List<PostResponseDto> postResponseDtos, Optional<Page<Post>> postResult) {
            postResult.get().stream().map(Post::getIdUser).collect(Collectors.toSet()).forEach(id -> {
                Optional<StudentRequestDto> student = getUserById(token, id);
                if (student.isEmpty()) throw new StudentDoestNotExistsOrWasEliminatedException("The student does not exist or was eliminated");
                postResult.get().forEach(post -> {
                            if(Objects.equals(post.getIdUser(), student.get().getId())){
                                PostResponseDto postResponseDto = postMapper.postToDto(post);
                                postResponseDto.setIdUser(student.get().getId());
                                postResponseDto.setUserName(student.get().getFirstName() + ' ' + student.get().getLastName());
                                postResponseDtos.add(postResponseDto);
                            }
                        }
                );
            });
        return postResponseDtos;
        }

    private void sendNotification(String bearerToken, StudentRequestDto student,groupDto group) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setIdGroup(group.getId());
        notificationDto.setTipoNotification(Tipo.MENSAJE);
        notificationDto.setNameGroup(group.getName());
        notificationDto.setNameNotificante(student.getFirstName()
                + " " + student.getLastName());
        for (StudentRequestDto s : group.getStudents()) {
            notificationDto.setIdStudent(s.getId());
            notificationDto.setIdStudentNotification(s.getId());

            createNotification(bearerToken, notificationDto);
        }
    }


    public static void createNotification(String bearerToken, NotificationDto notificationDto) {

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<NotificationDto> request = RequestEntity.post(URI.create("http://localhost:8761/notification/create-notification"))
                .header("Authorization", bearerToken)
                .body(notificationDto);
        restTemplate.exchange(request, NotificationDto.class);
    }


}
