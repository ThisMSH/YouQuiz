package com.youquiz.configuration;

import com.youquiz.dto.requestdto.*;
import com.youquiz.dto.responsedto.*;
import com.youquiz.entities.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Beans {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(Question.class, QuestionDTO.class)
            .addMappings(mapper -> {
                mapper.skip(QuestionDTO::setAnswerValidations);
                mapper.skip(QuestionDTO::setSubject);
                mapper.skip(QuestionDTO::setLevel);
                mapper.skip(QuestionDTO::setMedias);
                mapper.skip(QuestionDTO::setQuizQuestions);
            });

        modelMapper.typeMap(QuestionRequestDTO.class, Question.class)
            .addMappings(mapper -> {
                mapper.map(
                    QuestionRequestDTO::getLevelId,
                    (dest, id) -> dest.getLevel().setId((Long) id)
                );
                mapper.map(
                    QuestionRequestDTO::getSubjectId,
                    (dest, id) -> dest.getSubject().setId((Long) id)
                );
            });

        modelMapper.typeMap(Answer.class, AnswerDTO.class)
            .addMappings(mapper -> {
                mapper.skip(AnswerDTO::setAnswerValidations);
            });

        modelMapper.typeMap(Level.class, LevelDTO.class)
            .addMappings(mapper -> {
                mapper.skip(LevelDTO::setQuestions);
            });

        modelMapper.typeMap(Subject.class, SubjectDTO.class)
            .addMappings(mapper -> {
                mapper.skip(SubjectDTO::setQuestions);
                mapper.skip(SubjectDTO::setChildren);
            });

        modelMapper.typeMap(SubjectRequestDTO.class, Subject.class)
            .addMappings(mapper -> {
                mapper.map(
                    SubjectRequestDTO::getParentId,
                    (dest, id) -> dest.getParent().setId((Long) id)
                );
            });

        modelMapper.typeMap(AnswerValidationRequestDTO.class, AnswerValidation.class)
            .addMappings(mapper -> {
                mapper.map(
                    AnswerValidationRequestDTO::getAnswerId,
                    (dest, id) -> dest.getAnswer().setId((Long) id)
                );
                mapper.map(
                    AnswerValidationRequestDTO::getQuestionId,
                    (dest, id) -> dest.getQuestion().setId((Long) id)
                );
            });

        modelMapper.typeMap(MediaFileRequestDTO.class, Media.class)
            .addMappings(mapper -> {
                mapper.map(
                    MediaFileRequestDTO::getQuestionId,
                    (dest, id) -> dest.getQuestion().setId((Long) id)
                );
            });

        modelMapper.typeMap(QuizRequestDTO.class, Quiz.class)
            .addMappings(mapper -> {
                mapper.map(
                    QuizRequestDTO::getTrainerId,
                    (dest, id) -> dest.getTrainer().setId((Long) id)
                );
            });

        modelMapper.typeMap(QuizAssignmentRequestDTO.class, QuizAssignment.class)
            .addMappings(mapper -> {
                mapper.map(
                    QuizAssignmentRequestDTO::getStudentId,
                    (dest, id) -> dest.getStudent().setId((Long) id)
                );
                mapper.map(
                    QuizAssignmentRequestDTO::getQuizId,
                    (dest, id) -> dest.getQuiz().setId((Long) id)
                );
            });

        modelMapper.typeMap(QuizQuestionRequestDTO.class, QuizQuestion.class)
            .addMappings(mapper -> {
                mapper.map(
                    QuizQuestionRequestDTO::getQuestionId,
                    (dest, id) -> dest.getQuestion().setId((Long) id)
                );
                mapper.map(
                    QuizQuestionRequestDTO::getQuizId,
                    (dest, id) -> dest.getQuiz().setId((Long) id)
                );
            });

        modelMapper.typeMap(Quiz.class, QuizDTO.class)
            .addMappings(mapper -> {
                mapper.skip(QuizDTO::setQuizQuestions);
                mapper.skip(QuizDTO::setQuizAssignments);
                mapper.skip(QuizDTO::setTrainer);
                mapper.skip(QuizDTO::setSubjects);
            });

        modelMapper.typeMap(Student.class, StudentDTO.class)
            .addMappings(mapper -> {
                mapper.skip(StudentDTO::setQuizAssignments);
            });

        modelMapper.typeMap(Trainer.class, TrainerDTO.class)
            .addMappings(mapper -> {
                mapper.skip(TrainerDTO::setQuizzes);
            });

        return modelMapper;
    }
}
