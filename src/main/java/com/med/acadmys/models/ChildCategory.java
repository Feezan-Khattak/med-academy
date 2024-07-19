package com.med.acadmys.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "child_category")
@EqualsAndHashCode(callSuper = true)
public class ChildCategory extends BaseEntity {
    private String name;

//    @ManyToOne
//    @JoinColumn(name = "parent_category_id")
//    private ParentCategory parentCategory;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "child_category_id")
//    private Set<Question> questions = new HashSet<>();
}
