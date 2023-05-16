package com.example.QuizApplication.Convertors;

import com.example.QuizApplication.Entity.QuizEntity;
import com.example.QuizApplication.RequestDto.QuizRequestDto;
import com.example.QuizApplication.ResponseDto.QuizResponseDto;

import java.io.IOException;

public class ConvertToEntity {

    public static QuizResponseDto convertQuizEntityToQuizResponseDto(QuizEntity quizEntity){

        QuizResponseDto quizResponseDto= QuizResponseDto.builder().question(quizEntity.getQuestion()).options(quizEntity.getOptions())
                .build();

        return quizResponseDto;
    }

    public static QuizEntity convertQuizRequestDtoToQuizEntity(QuizRequestDto quizRequestDto) throws IOException {

        QuizEntity quiz= QuizEntity.builder().question(quizRequestDto.getQuestion())
                .startDate(quizRequestDto.getStartDate()).endDate(quizRequestDto.getEndDate())
                .rightAnswer(quizRequestDto.getRightAnswer()).options(quizRequestDto.getOptions())
                .build();


        //quiz.setOptions(optionsList);
        return quiz;
    }
}


