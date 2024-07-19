package com.med.acadmys.services;

import com.med.acadmys.dtos.QuestionDto;
import com.med.acadmys.dtos.Response;
import com.med.acadmys.models.ChildCategory;
import com.med.acadmys.models.Question;
import com.med.acadmys.repositories.ChildCategoryRepository;
import com.med.acadmys.repositories.QuestionRepository;
import com.med.acadmys.utils.ResponseUtil;
import com.med.acadmys.utils.Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    private final ResponseUtil responseUtil;
    private final ChildCategoryRepository childCategoryRepository;
    private final Util util;

    public Response saveQuestion(String category, Question question) {
        Optional<ChildCategory> childCategory = childCategoryRepository.findByName(category);
        if (childCategory.isPresent()) {
            try {
                question.setQuestionCode(util.generateQuestionCode());
                question.setFlag(false);
                question.setChildCategory(childCategory.get());
                Question savedQuestion = questionRepository.save(question);
                Type type = new TypeToken<QuestionDto>() {
                }.getType();
                QuestionDto response = modelMapper.map(savedQuestion, type);
                return responseUtil.generateSuccessResponse(response);
            } catch (Exception e) {
                return responseUtil.generateFailureResponse(e.getLocalizedMessage());
            }
        } else {
            return responseUtil.generateFailureResponse("No Category found, please provide the valid category");
        }
    }

    public Response updateQuestion(String category, Question question) {
        Optional<ChildCategory> childCategory = childCategoryRepository.findByName(category);
        if (childCategory.isPresent()) {
            Optional<Question> questionByCode = questionRepository.findById(question.getId());
            if (questionByCode.isPresent()) {
                try {
                    question.setQuestionCode(questionByCode.get().getQuestionCode());
                    question.setFlag(questionByCode.get().isFlag());
                    question.setChildCategory(childCategory.get());
                    Question savedQuestion = questionRepository.save(question);
                    Type type = new TypeToken<QuestionDto>() {
                    }.getType();
                    QuestionDto response = modelMapper.map(savedQuestion, type);
                    return responseUtil.generateSuccessResponse(response);
                } catch (Exception e) {
                    return responseUtil.generateFailureResponse(e.getLocalizedMessage());
                }
            } else {
                return responseUtil.generateFailureResponse("Please add question before updating it.");
            }
        } else {
            return responseUtil.generateFailureResponse("No Category found, please provide the valid category");
        }
    }

    public Response deleteQuestion(long id) {
        try {
            questionRepository.deleteById(id);
            return responseUtil.generateSuccessResponse("Question Deleted successfully from the system");
        } catch (Exception er) {
            return responseUtil.generateFailureResponse(er.getLocalizedMessage());
        }
    }

    public Response getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            Type type = new TypeToken<QuestionDto>() {
            }.getType();
            QuestionDto response = modelMapper.map(question.get(), type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No Question found");
        }
    }

    public Response getQuestionByQuestionCode(String questionCode) {
        Optional<Question> question = questionRepository.findByQuestionCode(questionCode);
        if (question.isPresent()) {
            Type type = new TypeToken<QuestionDto>() {
            }.getType();
            QuestionDto response = modelMapper.map(question.get(), type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No question found with the provided question code: " + questionCode);
        }
    }

    public Response getAllQuestions(int pageNumber, int pageSize) {
        try {
            PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
            Page<Question> pagedQuestions = questionRepository.findAll(pageRequest);

            List<QuestionDto> questions = pagedQuestions.getContent().stream()
                    .map(question -> modelMapper.map(question, QuestionDto.class))
                    .toList();

            if (!questions.isEmpty()) {
                return responseUtil.generateSuccessResponse(questions);
            } else {
                return responseUtil.generateFailureResponse("No Question In The Database");
            }
        } catch (Exception e) {
            return responseUtil.generateFailureResponse(e.getLocalizedMessage());
        }
    }

    public Response getQuestionsByCategory(String category, Pageable pageable) {
        Optional<ChildCategory> childCategory = childCategoryRepository.findByName(category);
        if (childCategory.isPresent()) {
            Page<Question> questionsPage = questionRepository.findByChildCategory(childCategory.get(), pageable);
            List<QuestionDto> questions = questionsPage.getContent().stream()
                    .map(question -> modelMapper.map(question, QuestionDto.class))
                    .toList();
            if (!questions.isEmpty()) {
                return responseUtil.generateSuccessResponse(questions);
            } else {
                return responseUtil.generateFailureResponse("No Question In The Database");
            }
        } else {
            return responseUtil.generateFailureResponse("No Category found, please provide the valid category");
        }
    }
}
