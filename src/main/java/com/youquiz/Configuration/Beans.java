package com.youquiz.Configuration;

import com.youquiz.DTO.AnswerValidationDTO;
import com.youquiz.DTO.MediaDTO;
import com.youquiz.DTO.QuestionDTO;
import com.youquiz.Entities.*;
import com.youquiz.DTO.SubjectDTO;
import com.youquiz.Repositories.AnswerRepository;
import com.youquiz.Repositories.QuestionRepository;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
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
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(QuestionDTO.class, Question.class)
            .addMappings(mapper -> {
                mapper.map(
                    QuestionDTO::getLevelId,
                    (dest, id) -> dest.getLevel().setId((Long) id)
                );
                mapper.map(
                    QuestionDTO::getSubjectId,
                    (dest, id) -> dest.getSubject().setId((Long) id)
                );
            });

        modelMapper.typeMap(SubjectDTO.class, Subject.class)
            .addMappings(mapper -> {
                mapper.map(
                    SubjectDTO::getParentId,
                    (dest, id) -> dest.getParent().setId((Long) id)
                );
            });

        modelMapper.typeMap(AnswerValidationDTO.class, AnswerValidation.class)
            .addMappings(mapper -> {
                mapper.map(
                    AnswerValidationDTO::getAnswerId,
                    (dest, id) -> dest.getAnswer().setId((Long) id)
                );
                mapper.map(
                    AnswerValidationDTO::getQuestionId,
                    (dest, id) -> dest.getQuestion().setId((Long) id)
                );
            });

        modelMapper.typeMap(MediaDTO.class, Media.class)
            .addMappings(mapper -> {
                mapper.map(
                    MediaDTO::getQuestionId,
                    (dest, id) -> dest.getQuestion().setId((Long) id)
                );
            });

        return modelMapper;
    }
}
