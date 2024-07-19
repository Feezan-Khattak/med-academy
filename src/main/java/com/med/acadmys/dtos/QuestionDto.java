package com.med.acadmys.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.med.acadmys.models.ChildCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionDto extends BaseEntityDto {
    private String questionCode;
    private String title;
    private String description;
    private AnswerDto answer;
    private ChildCategoryDto childCategory;
    private List<CommentDto> comments = new ArrayList<>();
}
