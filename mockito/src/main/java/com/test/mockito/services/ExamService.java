package com.test.mockito.services;

import com.test.mockito.models.Exam;

import java.util.List;

public interface ExamService {

    Exam findExamByName(String name);
    List<Exam> findAll();
    Exam save(Exam exam);

}
