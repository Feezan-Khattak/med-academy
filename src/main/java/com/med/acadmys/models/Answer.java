package com.med.acadmys.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "answer")
@EqualsAndHashCode(callSuper = true)
public class Answer extends BaseEntity {
    private int correct;

//    @OneToOne
//    @JoinColumn(name = "question_id")
//    private Question question;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "answer_id")
    private List<Option> options = new ArrayList<>();
}
