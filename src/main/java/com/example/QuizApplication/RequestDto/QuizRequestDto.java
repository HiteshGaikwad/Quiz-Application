package com.example.QuizApplication.RequestDto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

//@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class QuizRequestDto {

    private String question;

    private String options;

    private int rightAnswer;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
