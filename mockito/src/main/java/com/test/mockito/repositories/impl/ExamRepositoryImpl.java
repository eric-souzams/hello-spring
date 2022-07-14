package com.test.mockito.repositories.impl;

import com.test.mockito.models.Exam;
import com.test.mockito.repositories.ExamRepository;

import java.util.List;
import java.util.Optional;

public class ExamRepositoryImpl implements ExamRepository {

    public List<Exam> findAll() {
        return List.of(new Exam(1L, "Math"), new Exam(2L, "Science"), new Exam(3L, "History"));
    }

    public Optional<Exam> findByName(String name) {
        return Optional.of(new Exam(1L, "Math"));
    }

    public Optional<Exam> findByNameWithQuestions(String name) {
        return Optional.empty();
    }

    @Override
    public Exam save(Exam exam) {
        return null;
    }
}
