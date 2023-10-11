package com.example.QuizApplication.ControllerLayer;

import com.example.QuizApplication.Entity.QuizEntity;
import com.example.QuizApplication.RequestDto.QuizRequestDto;
import com.example.QuizApplication.ResponseDto.QuizResponseDto;
import com.example.QuizApplication.ServiceLayer.QuizService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    private final Bucket bucket;

    @Autowired
    QuizService quizService;


    //Implemented the functionality for Rate liming (only 50 requests in one minute)
    public QuizController() {
        Bandwidth limit= Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        this.bucket= Bucket4j.builder().addLimit(limit).build();
    }


    //Endpoint to create a new Quiz
    @PostMapping("/create-quiz")
    public ResponseEntity<String> createNewQuiz(@RequestBody() QuizRequestDto quiz) throws IOException {

        quizService.createNewQuiz(quiz);

        return new ResponseEntity<>("New Quiz has been created successfully.", HttpStatus.CREATED);
    }

    @GetMapping("/active")
    public ResponseEntity<List<QuizResponseDto>> getActiveQuiz(){

        //if request are in specified limit
        if(bucket.tryConsume(1)) {
            List<QuizResponseDto> quizzes = quizService.getActiveQuiz();
            if (quizzes == null) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(quizzes, HttpStatus.FOUND);
        }
        //if requests are more than the specified limit
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    //Endpoint to get Quiz By ID
    @GetMapping("/id/result/{id}")
    public ResponseEntity<String> getResultOfQuizById(@PathVariable("id") int id){

        if(bucket.tryConsume(1)) {
            String answer = quizService.getResultOfQuizById(id);

            if(answer==null){
                return new ResponseEntity<>("Id not found, enter valid Id", HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>(answer, HttpStatus.FOUND);
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }



    //Endpoint to get all the Quizzes
    @GetMapping("/all")
    public ResponseEntity<List<QuizResponseDto>> getAllTheQuizzes() {

        if (bucket.tryConsume(1)) {
            List<QuizResponseDto> listOfQuizzes = quizService.getListOfAllQuizzes();
            return new ResponseEntity<>(listOfQuizzes, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }


}
