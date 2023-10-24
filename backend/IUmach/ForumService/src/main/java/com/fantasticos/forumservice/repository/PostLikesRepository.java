package com.fantasticos.forumservice.repository;

import com.fantasticos.forumservice.model.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes,Long> {
}
