package com.example.QuizApplication.Entity;

import com.example.QuizApplication.Enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="quizzes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String question;

    private List<String> options;

    private int rightAnswer;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private Status status;

}
