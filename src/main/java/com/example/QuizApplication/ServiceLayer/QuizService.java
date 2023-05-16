package com.example.QuizApplication.ServiceLayer;

import com.example.QuizApplication.Convertors.ConvertToEntity;
import com.example.QuizApplication.Entity.QuizEntity;
import com.example.QuizApplication.Enums.Status;
import com.example.QuizApplication.RepositoryLayer.QuizRepository;
import com.example.QuizApplication.RequestDto.QuizRequestDto;
import com.example.QuizApplication.ResponseDto.QuizResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

//    @Autowired
//    RedisTemplate<Integer,QuizEntity> redisTemplate;
//
//    @Autowired
//    ObjectMapper objectMapper;


    //function to create a quiz
    public void createNewQuiz(QuizRequestDto quizRequestDto) throws IOException {

        QuizEntity quiz= ConvertToEntity.convertQuizRequestDtoToQuizEntity(quizRequestDto);

//        List<String> list= Arrays.asList(quiz.getOptions().toString());
//
//        quiz.setOptions(list);

        //Initially status of the quiz will be Inactive
        quiz.setStatus(Status.INACTIVE);

        //saving quiz to the repository
        quizRepository.save(quiz);

//        //Saving quiz to Cache
//        saveInCache(quiz);
    }



//    //Implementing saveInCache function to save the Quiz in cache memory
//    public void saveInCache(QuizEntity quiz){
//
//        Map map = objectMapper.convertValue(quiz,Map.class);
//
//        redisTemplate.opsForHash().putAll(quiz.getId(),map);
//        redisTemplate.expire(quiz.getId(), Duration.ofHours(12));
//    }


    //function to get Active quiz at the current date and time
    public List<QuizResponseDto> getActiveQuiz(){

        List<QuizEntity> list= quizRepository.findAll();

        LocalDateTime currentDate= LocalDateTime.now();

        List<QuizResponseDto> listOfActiveQuizzes=new ArrayList<>();
        for(QuizEntity quiz: list){
            if(currentDate.isAfter(quiz.getStartDate()) && currentDate.isBefore(quiz.getEndDate())){
                    listOfActiveQuizzes.add(ConvertToEntity.convertQuizEntityToQuizResponseDto(quiz));
            }
        }
        return listOfActiveQuizzes;
    }


    //function to get result of the quiz by Id
    public String getResultOfQuizById(int id) {

//        //first find it in the Cache memory
//        Map map= redisTemplate.opsForHash().entries(id);
//
//        QuizEntity quiz=null;

        //if not found inside the cache memory then check in the database
       // if(map==null) {
            //Retrieving quiz from the repository by the id
            QuizEntity quiz = quizRepository.findById(id).get();

//             //save in the cache memory
//            saveInCache(quiz);
//
//        } else{
//            //found inside the cache memory
//            quiz= objectMapper.convertValue(map, QuizEntity.class);
//        }

        LocalDateTime now= LocalDateTime.now();

        //comparing if the finished time of the quiz plus 5 minutes is equal to current time
        if(now.isAfter(quiz.getEndDate().plusMinutes(5))) {

            //if yes then user should get to see the result of the specified id of the Quiz
            int index = quiz.getRightAnswer();
            List<String> option = quiz.getOptions();
            String answer = option.get(index);
            return answer;
        }
        //if it's not been 5 minutes plus since the finished time of the quiz then user should not be able to see the result
        return "Answer for this quiz is not available yet, wait for some time";
    }



    //function to get list of all quizzes
    public List<QuizResponseDto> getListOfAllQuizzes(){

        //Retrieving the list of the all existing quizzes for the repository
        List<QuizEntity> quizEntityList= quizRepository.findAll();

        List<QuizResponseDto> listOfQuizzes= new ArrayList<>();

        for(QuizEntity quiz: quizEntityList){
            QuizResponseDto quizDto= ConvertToEntity.convertQuizEntityToQuizResponseDto(quiz);
            listOfQuizzes.add(quizDto);
        }
        return listOfQuizzes;
    }
}
