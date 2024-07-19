package com.med.acadmys.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.med.acadmys.models.ChildCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParentCategoryDto extends BaseEntityDto {
    private String name;
    private Set<ChildCategory> childCategory = new HashSet<>();
}
