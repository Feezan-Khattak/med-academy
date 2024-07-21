package com.med.acadmys.repositories;

import com.med.acadmys.models.ChildCategory;
import com.med.acadmys.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByQuestionCode(String questionCode);
    Page<Question> findByChildCategory(ChildCategory childCategory, Pageable pageable);

    Page<Question> findByFlag(boolean flag, Pageable pageable);
}
