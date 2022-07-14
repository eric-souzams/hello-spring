package com.test.mockito.services.impl;

import com.test.mockito.models.Exam;
import com.test.mockito.repositories.ExamRepository;
import com.test.mockito.repositories.QuestionRepository;
import com.test.mockito.services.ExamService;

import java.util.List;
import java.util.Optional;

public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;
    private QuestionRepository questionRepository;

    public ExamServiceImpl(ExamRepository examRepository, QuestionRepository questionRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public Exam findExamByName(String name) {
        Optional<Exam> foundExam = examRepository.findByName(name);
        if (foundExam.isEmpty()) {
            throw new RuntimeException("Exam not found");
        }

        List<String> questions = questionRepository.findQuestionByExamId(foundExam.get().getId());
        foundExam.get().setQuestions(questions);

        return foundExam.get();
    }

    @Override
    public List<Exam> findAll() {
        return examRepository.findAll();
    }

    @Override
    public Exam save(Exam exam) {
        if (!exam.getQuestions().isEmpty()) {
            questionRepository.save(exam.getQuestions());
        }

        return examRepository.save(exam);
    }
}
