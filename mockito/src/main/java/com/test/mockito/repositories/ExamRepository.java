package com.test.mockito.repositories;

import com.test.mockito.models.Exam;

import java.util.List;
import java.util.Optional;

public interface ExamRepository {

    List<Exam> findAll();
    Optional<Exam> findByName(String name);
    Optional<Exam> findByNameWithQuestions(String name);
    Exam save(Exam exam);

}
