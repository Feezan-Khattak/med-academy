package com.med.acadmys.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "parent_category")
@EqualsAndHashCode(callSuper = true)
public class ParentCategory extends BaseEntity {
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_category_id")
    private Set<ChildCategory> childCategory = new HashSet<>();
}
