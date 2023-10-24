package com.fantasticos.forumservice.repository;

import com.fantasticos.forumservice.model.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikesRepository extends JpaRepository<CommentLikes,Long> {
}
