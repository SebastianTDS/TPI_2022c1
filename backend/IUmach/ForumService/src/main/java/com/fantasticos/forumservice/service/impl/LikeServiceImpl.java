package com.fantasticos.forumservice.service.impl;

import com.fantasticos.forumservice.dto.DtoUser;
import com.fantasticos.forumservice.dto.LikeRequestDto;
import com.fantasticos.forumservice.exception.CommentDoesNotExistOrWasDeletedException;
import com.fantasticos.forumservice.exception.IdFieldForPostOrCommentIsEmptyException;
import com.fantasticos.forumservice.exception.PostDoesNotExistOrWasDeletedException;
import com.fantasticos.forumservice.exception.StudentDoestNotExistsOrWasEliminatedException;
import com.fantasticos.forumservice.model.Comment;
import com.fantasticos.forumservice.model.CommentLikes;
import com.fantasticos.forumservice.model.Post;
import com.fantasticos.forumservice.model.PostLikes;
import com.fantasticos.forumservice.repository.CommentLikesRepository;
import com.fantasticos.forumservice.repository.CommentRepository;
import com.fantasticos.forumservice.repository.PostLikesRepository;
import com.fantasticos.forumservice.repository.PostRepository;
import com.fantasticos.forumservice.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.fantasticos.forumservice.util.StudentMethods.getMyId;
@Service
@RequiredArgsConstructor
@Transactional
public class LikeServiceImpl implements LikeService {

    private final PostLikesRepository postLikesRepository;
    private final CommentLikesRepository commentLikesRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void createLike(LikeRequestDto likeRequestDto, String bearerToken) {
        if((likeRequestDto.getId_comment()==null && likeRequestDto.getId_post()==null) || (likeRequestDto.getId_comment()!=null && likeRequestDto.getId_post()!=null)) throw new IdFieldForPostOrCommentIsEmptyException("The 'id' of the post or comment is needed");
        Optional<DtoUser> user = getMyId(bearerToken);
        if(user.isEmpty()) throw new StudentDoestNotExistsOrWasEliminatedException("The user trying to 'like' does not exist or was deleted.");
        if(likeRequestDto.getId_post()!=null) {
            Optional<Post> post = postRepository.findById(likeRequestDto.getId_post());
            if(post.isEmpty()) throw new PostDoesNotExistOrWasDeletedException("The post to be liked does not exist or was deleted.");
            PostLikes postLike = new PostLikes();
            postLike.setId_user(user.get().getUserName());
            postLikesRepository.save(postLike);
            post.get().getLikes().add(postLike);
            postRepository.save(post.get());
        }else{
            Optional<Comment> comment = commentRepository.findById(likeRequestDto.getId_comment());
            if(comment.isEmpty()) throw new CommentDoesNotExistOrWasDeletedException("The comment to be liked does not exist or was deleted.");
            CommentLikes commentLikes = new CommentLikes();
            commentLikes.setId_user(user.get().getUserName());
            commentLikesRepository.save(commentLikes);
            comment.get().getLikes().add(commentLikes);
            commentRepository.save(comment.get());
        }
    }


}
