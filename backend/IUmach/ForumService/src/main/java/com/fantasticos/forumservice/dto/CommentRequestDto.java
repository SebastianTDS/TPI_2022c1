package com.fantasticos.forumservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentRequestDto {

    @NotBlank
    @NotEmpty
    private String content;
    @NotNull(message = "El post al que intentas comentar no existe o fue borrado recientemente")
    private Long id_post;
}
