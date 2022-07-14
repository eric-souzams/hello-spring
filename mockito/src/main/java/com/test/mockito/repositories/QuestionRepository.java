package com.test.mockito.repositories;

import java.util.List;

public interface QuestionRepository {

    List<String> findQuestionByExamId(Long id);
    void save(List<String> questions);

}
