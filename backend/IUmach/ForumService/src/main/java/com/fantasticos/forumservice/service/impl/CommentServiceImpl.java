package com.fantasticos.forumservice.service.impl;

import com.fantasticos.forumservice.dto.CommentRequestDto;
import com.fantasticos.forumservice.dto.DtoUser;
import com.fantasticos.forumservice.dto.StudentRequestDto;
import com.fantasticos.forumservice.exception.PostDoesNotExistOrWasDeletedException;
import com.fantasticos.forumservice.exception.UserTryingToCreatePostDoesNotExistOrWasDeletedException;
import com.fantasticos.forumservice.mapper.CommentMapper;
import com.fantasticos.forumservice.model.Comment;
import com.fantasticos.forumservice.model.Post;
import com.fantasticos.forumservice.repository.CommentRepository;
import com.fantasticos.forumservice.repository.PostRepository;
import com.fantasticos.forumservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.fantasticos.forumservice.util.StudentMethods.getMyId;
import static com.fantasticos.forumservice.util.StudentMethods.getMyUser;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    @Override
    public void createComment(CommentRequestDto commentRequestDto, String token) {
        Comment comment =  commentMapper.RequestDtoToComment(commentRequestDto);
        Optional<StudentRequestDto> postCreator =  getMyUser(token);
        if(postCreator.isEmpty()) throw new UserTryingToCreatePostDoesNotExistOrWasDeletedException("The user trying to create the post does not exist or was deleted");
        comment.setId_user(postCreator.get().getId());
        comment.setUser_name(postCreator.get().getFirstName()+' '+postCreator.get().getLastName());
        commentRepository.save(comment);
        Optional<Post> post = postRepository.findById(commentRequestDto.getId_post());
        if(post.isEmpty()) throw new PostDoesNotExistOrWasDeletedException("The post in which you want to leave a comment does not exist or was deleted");
        post.get().getComments().add(comment);
        postRepository.save(post.get());
    }
}
