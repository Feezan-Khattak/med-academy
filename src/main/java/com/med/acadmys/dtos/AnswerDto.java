package com.med.acadmys.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.med.acadmys.models.Option;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDto extends BaseEntityDto {
    private int correct;
    private List<OptionDto> options = new ArrayList<>();
}
