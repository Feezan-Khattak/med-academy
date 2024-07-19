package com.med.acadmys.repositories;

import com.med.acadmys.models.Comment;
import com.med.acadmys.models.Question;
import com.med.acadmys.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByUser(User user, Pageable pageable);
    Page<Comment> findByQuestion(Question question, Pageable pageable);

}
