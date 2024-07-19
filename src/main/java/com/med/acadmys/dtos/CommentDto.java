package com.med.acadmys.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto extends BaseEntityDto {
    private String comment;
    private int likeCounts;
    private int dislikeCounts;
    private UserDto user;
    private QuestionDto question;

    @Data
    static class UserDto {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
    }

    @Data
    static class QuestionDto {
        private Long id;
        private String questionCode;
        private String title;
        private String description;
    }
}
