package com.med.acadmys.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "comment")
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseEntity {
    @Lob
    private String comment;

    @Column(name = "like_counts")
    private int likeCounts;

    @Column(name = "dislike_counts")
    private int dislikeCounts;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
