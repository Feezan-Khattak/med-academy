package com.med.acadmys.controllers;

import com.med.acadmys.dtos.Response;
import com.med.acadmys.models.Question;
import com.med.acadmys.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "*")
@RequestMapping("/api/v1/question")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveQuestion(
            @RequestParam String category,
            @RequestBody Question question
    ) {
        Response response = questionService.saveQuestion(category, question);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<Response> updateQuestion(
            @RequestParam String category,
            @RequestBody Question question
    ) {
        Response response = questionService.updateQuestion(category, question);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getQuestionById(@PathVariable Long id) {
        Response response = questionService.getQuestionById(id);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/code/{questionCode}")
    public ResponseEntity<Response> getQuestionByQuestionCode(@PathVariable String questionCode) {
        Response response = questionService.getQuestionByQuestionCode(questionCode);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllQuestions(
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        Response response = questionService.getAllQuestions(pageNumber, pageSize);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteQuestion(@PathVariable Long id) {
        Response response = questionService.deleteQuestion(id);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/by-child-category/{childCategory}")
    public ResponseEntity<Response> getQuestionsByChildCategory(
            @PathVariable String childCategory,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Response response = questionService.getQuestionsByCategory(childCategory, pageable);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
