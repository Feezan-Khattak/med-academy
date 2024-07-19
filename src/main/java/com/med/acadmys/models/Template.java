package com.med.acadmys.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "template")
public class Template extends BaseEntity {
    @Column(columnDefinition = "TEXT", nullable = false)
    private String template;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, unique = true)
    private String templateKey;
}
