package com.optus.counterapi.repository;

import com.optus.counterapi.entity.SampleParagraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleParagraphRepository extends JpaRepository<SampleParagraph, Long> {
}
