package com.fantasticos.forumservice.dto;

import com.fantasticos.forumservice.model.Comment;
import com.fantasticos.forumservice.model.PostLikes;
import lombok.*;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostResponseDto {

    private String idUser;

    private Long id;

    private Long idGroup;

    private String content;

    private Date date;

    private String userName;

    private String name;

    Set<Comment> comments ;

    Set<PostLikes> likes ;

    private Long idUniversity;

    private Long idDepartment;

    private Long idCareer;

    private Long idSubject;

    private Long idTag;

    private String tagName;

}
