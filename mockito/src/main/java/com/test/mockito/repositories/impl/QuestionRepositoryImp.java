package com.test.mockito.repositories.impl;

import com.test.mockito.repositories.QuestionRepository;

import java.util.List;

public class QuestionRepositoryImp implements QuestionRepository {
    @Override
    public List<String> findQuestionByExamId(Long id) {
        return List.of("Question1", "Question2");
    }

    @Override
    public void save(List<String> questions) {

    }
}
