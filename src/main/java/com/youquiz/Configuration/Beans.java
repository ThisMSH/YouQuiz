package com.youquiz.Configuration;

import com.youquiz.DTO.AnswerValidationDTO;
import com.youquiz.DTO.QuestionDTO;
import com.youquiz.Entities.Answer;
import com.youquiz.Entities.AnswerValidation;
import com.youquiz.Entities.Question;
import com.youquiz.DTO.SubjectDTO;
import com.youquiz.Entities.Subject;
import com.youquiz.Repositories.AnswerRepository;
import com.youquiz.Repositories.QuestionRepository;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public Beans(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Long, Answer> answerConverter = new AbstractConverter<Long, Answer>() {
            @Override
            protected Answer convert(Long id) {
                return answerRepository.findById(id).orElse(null);
            }
        };

        Converter<Long, Question> questionConverter = new AbstractConverter<Long, Question>() {
            @Override
            protected Question convert(Long id) {
                return questionRepository.findById(id).orElse(null);
            }
        };

        modelMapper.addMappings(new PropertyMap<QuestionDTO, Question>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<SubjectDTO, Subject>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<AnswerValidationDTO, AnswerValidation>() {
            @Override
            protected void configure() {
                using(answerConverter).map(source.getAnswerId(), destination.getAnswer());
                using(questionConverter).map(source.getQuestionId(), destination.getQuestion());
            }
        });

        return modelMapper;
    }
}
