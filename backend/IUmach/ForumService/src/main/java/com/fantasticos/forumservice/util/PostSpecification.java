package com.fantasticos.forumservice.util;

import com.fantasticos.forumservice.dto.FilterDto;
import com.fantasticos.forumservice.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Component
public class PostSpecification {

    public  Specification<Post> specificationForGroup(FilterDto params, Long idGroup) {
        return (root,query,criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("idGroup"),idGroup));
            return getPredicate(params, root, criteriaBuilder, predicates);
        };
    }

    public  Specification<Post> specificationForGeneral(FilterDto params) {
        return (root,query,criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isNull(root.get("idGroup")));
            return getPredicate(params, root, criteriaBuilder, predicates);
        };
    }

    private Predicate getPredicate(FilterDto params, Root<Post> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if(params!=null){
            if(params.getIdUniversity()!=null){
                predicates.add(criteriaBuilder.equal(root.get("idUniversity"),params.getIdUniversity()));
            }if(params.getIdDepartment()!=null){
                predicates.add(criteriaBuilder.equal(root.get("idDepartment"),params.getIdDepartment()));
            }if(params.getIdCareer()!=null){
                predicates.add(criteriaBuilder.equal(root.get("idCareer"),params.getIdCareer()));
            }if(params.getIdSubject()!=null){
                predicates.add(criteriaBuilder.equal(root.get("idSubject"),params.getIdSubject()));
            }if(params.getName()!=null){
                predicates.add(criteriaBuilder.like(root.get("name"),"%"+params.getName()+"%"));
            }if(params.getIdTag()!=null){
                predicates.add(criteriaBuilder.equal(root.get("idTag"),params.getIdTag()));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


}
