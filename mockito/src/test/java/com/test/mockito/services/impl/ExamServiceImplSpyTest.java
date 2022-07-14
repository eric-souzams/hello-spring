package com.test.mockito.services.impl;

import com.test.mockito.models.Exam;
import com.test.mockito.repositories.ExamRepository;
import com.test.mockito.repositories.QuestionRepository;
import com.test.mockito.repositories.impl.ExamRepositoryImpl;
import com.test.mockito.repositories.impl.QuestionRepositoryImp;
import com.test.mockito.services.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplSpyTest {

    @Spy
    private ExamRepositoryImpl examRepository;
    @Spy
    private QuestionRepositoryImp questionRepository;
    @InjectMocks
    private ExamServiceImpl examService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testWithSpy() {
        Exam result = examService.findExamByName(anyString());

        assertEquals(1L, result.getId());
        assertEquals("Math", result.getName());
        assertEquals(2, result.getQuestions().size());

        verify(examRepository).findByName(anyString());
        verify(questionRepository).findQuestionByExamId(anyLong());
    }

}