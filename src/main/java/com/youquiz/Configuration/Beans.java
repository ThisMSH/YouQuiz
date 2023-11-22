package com.youquiz.Configuration;

import com.youquiz.DTO.AltDTO.*;
import com.youquiz.DTO.AnswerValidationDTO;
import com.youquiz.DTO.MediaDTO;
import com.youquiz.DTO.NoParentAltDTO.*;
import com.youquiz.DTO.QuestionDTO;
import com.youquiz.DTO.SubjectDTO;
import com.youquiz.Entities.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class Beans {
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

/*
        modelMapper.typeMap(Subject.class, SubjectNoParentDTO.class)
            .addMappings(mapper -> {
                mapper.map(Subject::getId, SubjectNoParentDTO::setId);
//                mapper.skip(SubjectNoParentDTO::setParentId);
//                mapper.skip(SubjectNoParentDTO::setChildrenIds);
//                mapper.skip(SubjectNoParentDTO::setQuestionIds);
//                mapper.map(
//                    src -> src.getParent() != null
//                        ? src.getParent().getId()
//                        : null,
//                    SubjectNoParentDTO::setParentId
//                );
//                mapper.map(
//                    src -> src.getChildren() != null
//                        ? src.getChildren().stream()
//                        .map(Subject::getId)
//                        .collect(Collectors.toList())
//                        : null,
//                    SubjectNoParentDTO::setChildrenIds
//                );
//                mapper.map(
//                    src -> src.getQuestions() != null
//                        ? src.getQuestions().stream()
//                        .map(Question::getId)
//                        .collect(Collectors.toList())
//                        : null,
//                    SubjectNoParentDTO::setQuestionIds
//                );
            });

        modelMapper.typeMap(Subject.class, SubjectAltDTO.class)
            .addMappings(mapper -> {
                mapper.map(Subject::getId, SubjectAltDTO::setId);
                mapper.map(
                    src -> src.getQuestions() != null
                    ? src.getQuestions().stream()
                        .map(question -> {
                            QuestionNoParentDTO q = new QuestionNoParentDTO();
                            q.setId(question.getId());
                            q.setQuestion(question.getQuestion());
                            q.setDescription(question.getDescription());
                            q.setAnswersCount(question.getAnswersCount());
                            q.setCorrectAnswersCount(question.getCorrectAnswersCount());
                            q.setType(question.getType());
                            q.setCreatedAt(question.getCreatedAt());
                            q.setUpdatedAt(question.getUpdatedAt());
                            q.setLevelId(question.getLevel().getId());
                            q.setSubjectId(question.getSubject().getId());
                            q.setMediaIds(question.getMedias().stream().map(Media::getId).collect(Collectors.toList()));
                            q.setAnswerValidationIds(question.getAnswerValidations().stream().map(AnswerValidation::getId).collect(Collectors.toList()));

                            return q;
                        }).collect(Collectors.toList())
                    : null,
                    SubjectAltDTO::setQuestions
                );
//                mapper.map(
//                    src -> src.getQuestions() != null
//                        ? src.getQuestions().stream()
//                        .map(question -> modelMapper.map(question, QuestionNoParentDTO.class))
//                        .collect(Collectors.toList())
//                        : null,
//                    SubjectAltDTO::setQuestions
//                );
//                mapper.map(
//                    src -> src.getParent() != null
//                        ? modelMapper.map(src.getParent(), SubjectNoParentDTO.class)
//                        : null,
//                    SubjectAltDTO::setParent
//                );
//                mapper.map(
//                    src -> src.getChildren() != null
//                        ? src.getChildren().stream()
//                        .map(child -> modelMapper.map(child, SubjectNoParentDTO.class))
//                        .collect(Collectors.toList())
//                        : null,
//                    SubjectAltDTO::setChildren
//                );
            });

        modelMapper.typeMap(Media.class, MediaNoParentDTO.class)
            .addMappings(mapper -> {
                mapper.map(Media::getId, MediaNoParentDTO::setId);
                mapper.skip(MediaNoParentDTO::setQuestionId);
//                mapper.map(
//                    src -> src.getQuestion() != null
//                        ? src.getQuestion().getId()
//                        : null,
//                    MediaNoParentDTO::setQuestionId
//                );
            });

        modelMapper.typeMap(Media.class, MediaAltDTO.class)
            .addMappings(mapper -> {
                mapper.map(Media::getId, MediaAltDTO::setId);
                mapper.map(
                    src -> src.getQuestion() != null
                        ? modelMapper.map(src.getQuestion(), QuestionNoParentDTO.class)
                        : null,
                    MediaAltDTO::setQuestion
                );
            });

        modelMapper.typeMap(Question.class, QuestionNoParentDTO.class)
            .addMappings(mapper -> {
                mapper.map(Question::getId, QuestionNoParentDTO::setId);
                mapper.skip(QuestionNoParentDTO::setLevelId);
                mapper.skip(QuestionNoParentDTO::setSubjectId);
                mapper.skip(QuestionNoParentDTO::setMediaIds);
                mapper.skip(QuestionNoParentDTO::setAnswerValidationIds);
//                mapper.map(
//                    src -> src.getLevel() != null
//                        ? src.getLevel().getId()
//                        : null,
//                    QuestionNoParentDTO::setLevelId
//                );
//                mapper.map(
//                    src -> src.getSubject() != null
//                        ? src.getSubject().getId()
//                        : null,
//                    QuestionNoParentDTO::setSubjectId
//                );
//                mapper.map(
//                    src -> src.getMedias() != null
//                        ? src.getMedias().stream()
//                        .map(Media::getId)
//                        .collect(Collectors.toList())
//                        : null,
//                    QuestionNoParentDTO::setMediaIds
//                );
//                mapper.map(
//                    src -> src.getAnswerValidations() != null
//                        ? src.getAnswerValidations().stream()
//                        .map(AnswerValidation::getId)
//                        .collect(Collectors.toList())
//                        : null,
//                    QuestionNoParentDTO::setAnswerValidationIds
//                );
            });

        modelMapper.typeMap(Question.class, QuestionAltDTO.class)
            .addMappings(mapper -> {
                mapper.map(Question::getId, QuestionAltDTO::setId);
                mapper.map(
                    src -> src.getLevel() != null
                        ? modelMapper.map(src.getLevel(), LevelNoParentDTO.class)
                        : null,
                    QuestionAltDTO::setLevel
                );
                mapper.map(
                    src -> src.getSubject() != null
                        ? modelMapper.map(src.getSubject(), SubjectNoParentDTO.class)
                        : null,
                    QuestionAltDTO::setSubject
                );
                mapper.map(
                    src -> src.getMedias() != null
                        ? src.getMedias().stream()
                        .map(media -> modelMapper.map(media, MediaNoParentDTO.class))
                        .collect(Collectors.toList())
                        : null,
                    QuestionAltDTO::setMedias
                );
                mapper.map(
                    src -> src.getAnswerValidations() != null
                        ? src.getMedias().stream()
                        .map(av -> modelMapper.map(av, AnswerValidationNoParentDTO.class))
                        .collect(Collectors.toList())
                        : null,
                    QuestionAltDTO::setAnswerValidations
                );
            });

        modelMapper.typeMap(Level.class, LevelNoParentDTO.class)
            .addMappings(mapper -> {
                mapper.map(Level::getId, LevelNoParentDTO::setId);
                mapper.skip(LevelNoParentDTO::setQuestionIds);
//                mapper.map(
//                    src -> src.getQuestions() != null
//                        ? src.getQuestions().stream()
//                        .map(Question::getId)
//                        .collect(Collectors.toList())
//                        : null,
//                    LevelNoParentDTO::setQuestionIds
//                );
            });

        modelMapper.typeMap(Level.class, LevelAltDTO.class)
            .addMappings(mapper -> {
                mapper.map(Level::getId, LevelAltDTO::setId);
                mapper.map(
                    src -> src.getQuestions() != null
                        ? src.getQuestions().stream()
                        .map(question -> modelMapper.map(question, QuestionNoParentDTO.class))
                        .collect(Collectors.toList())
                        : null,
                    LevelAltDTO::setQuestions
                );
            });

        modelMapper.typeMap(Answer.class, AnswerNoParentDTO.class)
            .addMappings(mapper -> {
                mapper.map(Answer::getId, AnswerNoParentDTO::setId);
                mapper.skip(AnswerNoParentDTO::setAnswerValidationIds);
//                mapper.map(
//                    src -> src.getAnswerValidations() != null
//                        ? src.getAnswerValidations().stream()
//                        .map(AnswerValidation::getId)
//                        .collect(Collectors.toList())
//                        : null,
//                    AnswerNoParentDTO::setAnswerValidationIds
//                );
            });

        modelMapper.typeMap(Answer.class, AnswerAltDTO.class)
            .addMappings(mapper -> {
                mapper.map(Answer::getId, AnswerAltDTO::setId);
                mapper.map(
                    src -> src.getAnswerValidations() != null
                        ? src.getAnswerValidations().stream()
                        .map(av -> modelMapper.map(av, AnswerValidationNoParentDTO.class))
                        .collect(Collectors.toList())
                        : null,
                    AnswerAltDTO::setAnswerValidations
                );
            });

        modelMapper.typeMap(AnswerValidation.class, AnswerValidationNoParentDTO.class)
            .addMappings(mapper -> {
                mapper.map(AnswerValidation::getId, AnswerValidationNoParentDTO::setId);
                mapper.skip(AnswerValidationNoParentDTO::setAnswerId);
                mapper.skip(AnswerValidationNoParentDTO::setQuestionId);
//                mapper.map(
//                    src -> src.getAnswer() != null
//                        ? src.getAnswer().getId()
//                        : null,
//                    AnswerValidationNoParentDTO::setAnswerId
//                );
//                mapper.map(
//                    src -> src.getQuestion() != null
//                        ? src.getQuestion().getId()
//                        : null,
//                    AnswerValidationNoParentDTO::setQuestionId
//                );
            });

        modelMapper.typeMap(AnswerValidation.class, AnswerValidationAltDTO.class)
            .addMappings(mapper -> {
                mapper.map(AnswerValidation::getId, AnswerValidationAltDTO::setId);
                mapper.map(
                    src -> src.getAnswer() != null
                        ? modelMapper.map(src.getAnswer(), AnswerNoParentDTO.class)
                        : null,
                    AnswerValidationAltDTO::setAnswer
                );
                mapper.map(
                    src -> src.getQuestion() != null
                        ? modelMapper.map(src.getQuestion(), QuestionNoParentDTO.class)
                        : null,
                    AnswerValidationAltDTO::setQuestion
                );
            });
*/

        return modelMapper;
    }
}
