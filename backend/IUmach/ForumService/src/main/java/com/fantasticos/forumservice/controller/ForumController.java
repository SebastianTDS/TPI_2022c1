package com.fantasticos.forumservice.controller;
import com.fantasticos.forumservice.dto.*;
import com.fantasticos.forumservice.model.Post;
import com.fantasticos.forumservice.service.CommentService;
import com.fantasticos.forumservice.service.LikeService;
import com.fantasticos.forumservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor
public class ForumController {

	private final PostService postService;
	private final LikeService likeService;
	private final CommentService commentService;

	@GetMapping("/general")
	public ResponseEntity<List<PostResponseDto>> findAllGeneralPost(@RequestHeader("Authorization") String bearerToken,FilterDto params, Pageable page){
		return new ResponseEntity<>(postService.findAllGeneralPost(bearerToken,params, page), HttpStatus.OK);
	}

	@PostMapping("/general/create")
	public ResponseEntity<Post> createGeneralPost(@Valid @RequestBody PostRequestDto postDto, @RequestHeader("Authorization") String bearerToken){
		return new ResponseEntity<>(postService.createPost(postDto,bearerToken), HttpStatus.CREATED);
	}

	@GetMapping("/group/{idGroup}")
	public ResponseEntity<List<PostResponseDto>> findAllGroupPost(@RequestHeader("Authorization") String bearerToken, @PathVariable("idGroup") Long idGroup, Pageable page,FilterDto params){
		return new ResponseEntity<>(postService.findAllGroupPost(bearerToken,idGroup,page,params), HttpStatus.OK);
	}

	@PostMapping("/group/create")
		public ResponseEntity<Post> createGroupPost (@Valid @RequestBody PostRequestDto postDto, @RequestHeader("Authorization") String bearerToken){
			return new ResponseEntity<>(postService.createPost(postDto,bearerToken), HttpStatus.CREATED);
		}

	@PostMapping("/comment")
	@ResponseStatus(HttpStatus.OK)
		public void commentPost(@Valid @RequestBody CommentRequestDto commentRequestDto, @RequestHeader("Authorization") String bearerToken){
		commentService.createComment(commentRequestDto,bearerToken);
	}

	@PostMapping("/like")
	@ResponseStatus(HttpStatus.OK)
		public void like(@RequestBody LikeRequestDto likeRequestDto, @RequestHeader("Authorization") String bearerToken){
			likeService.createLike(likeRequestDto,bearerToken);
	}


}

