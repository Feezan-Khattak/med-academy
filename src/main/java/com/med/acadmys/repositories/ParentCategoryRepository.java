package com.med.acadmys.repositories;

import com.med.acadmys.models.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentCategoryRepository extends JpaRepository<ParentCategory, Long> {
    Optional<ParentCategory> findByName(String name);
}
