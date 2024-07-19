package com.med.acadmys.mapper;

import com.med.acadmys.dtos.QuestionDto;
import com.med.acadmys.models.Question;
import com.med.acadmys.utils.Util;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuestionMapper {
    private final Util util;
    private final ModelMapper modelMapper;

    public Question mapNewQuestion(QuestionDto questionDto) {
       questionDto.setQuestionCode(util.generateQuestionCode());
        return modelMapper.map(questionDto, Question.class);
    }

    public Question mapUpdatedQuestion(QuestionDto questionDto, Question question) {
        questionDto.setId(question.getId());
        questionDto.setQuestionCode(question.getQuestionCode());
        return modelMapper.map(questionDto, Question.class);
    }
}
