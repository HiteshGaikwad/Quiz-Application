package com.example.QuizApplication.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequestDto {

    private String question;

    private List<String> options;

    private int rightAnswer;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
