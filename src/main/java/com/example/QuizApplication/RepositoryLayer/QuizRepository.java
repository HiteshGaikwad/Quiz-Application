package com.example.QuizApplication.RepositoryLayer;

import com.example.QuizApplication.Entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizRepository extends JpaRepository<QuizEntity,Integer> {

}
