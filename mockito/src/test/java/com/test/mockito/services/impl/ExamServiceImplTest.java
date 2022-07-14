package com.test.mockito.services.impl;

import com.test.mockito.models.Exam;
import com.test.mockito.repositories.ExamRepository;
import com.test.mockito.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

    @Mock
    private ExamRepository examRepository;
    @Mock
    private QuestionRepository questionRepository;
    @InjectMocks
    private ExamServiceImpl examService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testFindExamByName() {
        when(examRepository.findByName(anyString())).thenReturn(Optional.of(new Exam(1L, "Math")));

        Exam result = examService.findExamByName(anyString());

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Math", result.getName());
    }

    @Test
    void testNotFindExamByName() {
        when(examRepository.findByName(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> examService.findExamByName(anyString()));
        assertEquals("Exam not found", exception.getMessage());
    }

    @Test
    void testFindAllExams() {
        when(examRepository.findAll()).thenReturn(List.of(new Exam(1L, "Math"), new Exam(2L, "Science")));

        List<Exam> result = examService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }
    @Test
    void testFindAllExamsReturnEmpty() {
        when(examRepository.findAll()).thenReturn(List.of());

        List<Exam> result = examService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindExamByNameWithQuestions() {
        when(examRepository.findByName(anyString())).thenReturn(Optional.of(new Exam(1L, "Math")));
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(List.of("Question1", "Question2"));

        Exam result = examService.findExamByName(anyString());

        assertNotNull(result);
        assertFalse(result.getQuestions().isEmpty());
        assertEquals(2, result.getQuestions().size());
    }

    @Test
    void testFindExamByNameVerify() {
        when(examRepository.findByName(anyString())).thenReturn(Optional.of(new Exam(1L, "Math")));
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(List.of("Question1", "Question2"));

        Exam result = examService.findExamByName(anyString());

        assertNotNull(result);
        verify(examRepository).findByName(anyString());
        verify(examRepository, times(1)).findByName(anyString());
        verify(questionRepository).findQuestionByExamId(anyLong());
    }

    @Test
    void testSaveExam() {
        Exam exam = new Exam(1L, "Math");
        exam.setQuestions(List.of("Question1", "Question2"));

        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        Exam result = examService.save(exam);

        assertNotNull(result);
        assertEquals(1, result.getId());

        verify(examRepository).save(any(Exam.class));
        verify(questionRepository).save(anyList());
    }

    @Test
    void testThrowExceptionWhenTryFindExamByNameWithQuestions() {
        when(examRepository.findByName(anyString())).thenReturn(Optional.of(new Exam(1L, "Math")));
        when(questionRepository.findQuestionByExamId(anyLong())).thenThrow(IllegalArgumentException.class);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> examService.findExamByName(anyString()));

        assertEquals(IllegalArgumentException.class, exception.getClass());
        verify(examRepository).findByName(anyString());
        verify(questionRepository).findQuestionByExamId(anyLong());
    }

    @Test
    void testArgumentMatchers() {
        when(examRepository.findByName(anyString())).thenReturn(Optional.of(new Exam(1L, "Math")));
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(List.of("Question1", "Question2"));

        examService.findExamByName(anyString());

        verify(examRepository).findByName(anyString());
        verify(questionRepository).findQuestionByExamId(argThat(arg -> arg != null && arg >= 1L));
    }

    @Test
    void testDoThrow() {
        Exam exam = new Exam(1L, "Math");
        exam.setQuestions(List.of("Question1", "Question2"));

        doThrow(IllegalArgumentException.class).when(questionRepository).save(anyList());

        assertThrows(IllegalArgumentException.class, () -> examService.save(exam));
    }

    @Test
    void testOrders() {
        when(examRepository.findByName(anyString())).thenReturn(Optional.of(new Exam(1L, "Math")));

        examService.findExamByName("Math");

        InOrder inOrder = inOrder(examRepository, questionRepository);
        inOrder.verify(examRepository).findByName("Math");
        inOrder.verify(questionRepository).findQuestionByExamId(1L);
    }

    @Test
    void testNumberOfInvocation() {
        when(examRepository.findByName(anyString())).thenReturn(Optional.of(new Exam(1L, "Math")));

        examService.findExamByName("Math");

        verify(questionRepository).findQuestionByExamId(anyLong());
        verify(questionRepository, times(1)).findQuestionByExamId(anyLong()); //x vezes
        verify(questionRepository, atLeast(1)).findQuestionByExamId(anyLong()); //pelo menos x vezes
        verify(questionRepository, atLeastOnce()).findQuestionByExamId(anyLong());
        verify(questionRepository, atMost(1)).findQuestionByExamId(anyLong()); //no máximo x vezes
        verify(questionRepository, atMostOnce()).findQuestionByExamId(anyLong());
    }

    @Test
    void testNumberOfInvocation2() {
        when(examRepository.findAll()).thenReturn(List.of(new Exam(1L, "Math"), new Exam(2L, "Science")));

        examService.findAll();

        verify(questionRepository, never()).findQuestionByExamId(anyLong());
        verifyNoInteractions(questionRepository);

        verify(examRepository).findAll();
        verify(examRepository, times(1)).findAll();
        verify(examRepository, atLeast(1)).findAll();
        verify(examRepository, atLeastOnce()).findAll();
        verify(examRepository, atMost(1)).findAll();
        verify(examRepository, atMostOnce()).findAll();
    }
}