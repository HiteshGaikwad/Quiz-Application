package com.example.QuizApplication.ResponseDto;

import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponseDto {

    private String question;

    private String options;

}
