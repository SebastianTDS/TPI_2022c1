package com.fantasticos.forumservice.repository;

import com.fantasticos.forumservice.dto.FilterDto;
import com.fantasticos.forumservice.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post,Long>, JpaSpecificationExecutor<Post> {


    Optional<Page<Post>> findAllByIdGroupIsNull(Pageable page);

    Optional<Page<Post>> findAllByIdGroup(Pageable page, Long IdGroup);


}


