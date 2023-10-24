package com.fantasticos.forumservice.model;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PostLikes {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String id_user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostLikes postLikes = (PostLikes) o;
        return Objects.equals(id_user, postLikes.id_user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_user);
    }
}