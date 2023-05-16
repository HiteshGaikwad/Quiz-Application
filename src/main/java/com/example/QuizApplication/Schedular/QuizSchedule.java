package com.example.QuizApplication.Schedular;

import com.example.QuizApplication.Entity.QuizEntity;
import com.example.QuizApplication.Enums.Status;
import com.example.QuizApplication.RepositoryLayer.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class QuizSchedule {

    @Autowired
    QuizRepository quizRepository;

    //The status field is being updated automatically by the application based on the start and end time of each quiz by using Cron job.

    @Scheduled(cron = "0 * * * * *")
    public void updateStatus(){

        //Retrieving the list of all Quizzes form the Repository
        List<QuizEntity> listOfQuiz= quizRepository.findAll();

        //Getting the Current Date and Time
        LocalDateTime nowDate= LocalDateTime.now();
        //LocalTime nowTime= LocalTime.now();

        //Iterating over the List of Quizzes to compare with current date-time and define date-time of individual Quiz
        for(QuizEntity quiz: listOfQuiz){

            //if current date-time is before defined date-time of the Quiz then status of the Quiz should be Inactive.
            if(nowDate.isBefore(quiz.getStartDate())){
                quiz.setStatus(Status.INACTIVE);
            }
            //if current date-time is after the defined date-time of the quiz then status should be Finished.
            else if(nowDate.isAfter(quiz.getEndDate())){
                quiz.setStatus(Status.FINISHED);
            }
            //if the current date-time is lie between defined date-time of the Quiz then status  should be Active.
            else{
                quiz.setStatus(Status.ACTIVE);
            }

            //saving back to the repository after updating the status.
            quizRepository.save(quiz);
        }
    }

}
