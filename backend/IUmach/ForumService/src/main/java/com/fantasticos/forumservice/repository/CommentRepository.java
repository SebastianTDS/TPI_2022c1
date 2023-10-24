package com.fantasticos.forumservice.repository;

import com.fantasticos.forumservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
