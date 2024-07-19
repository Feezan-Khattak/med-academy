package com.med.acadmys.services;

import com.med.acadmys.dtos.CommentDto;
import com.med.acadmys.dtos.Response;
import com.med.acadmys.models.Comment;
import com.med.acadmys.models.Question;
import com.med.acadmys.models.User;
import com.med.acadmys.repositories.CommentRepository;
import com.med.acadmys.repositories.QuestionRepository;
import com.med.acadmys.repositories.UserRepository;
import com.med.acadmys.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ResponseUtil responseUtil;

    @Transactional
    public Response addComment(Long userId, Long questionId, Comment comment) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Question> question = questionRepository.findById(questionId);
        if (user.isPresent() && question.isPresent()) {
            comment.setUser(user.get());
            comment.setQuestion(question.get());
            comment.setLikeCounts(0);
            comment.setDislikeCounts(0);
            Comment savedComment = commentRepository.save(comment);
            Type type = new TypeToken<CommentDto>() {
            }.getType();
            CommentDto response = modelMapper.map(savedComment, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No user/question found with the provided id in the database");
        }
    }

    @Transactional
    public Response updateComment(Comment comment) {
        Optional<Comment> commentById = commentRepository.findById(comment.getId());
        if (commentById.isPresent()) {
            commentById.get().setComment(comment.getComment());
            Comment savedComment = commentRepository.save(commentById.get());
            Type type = new TypeToken<CommentDto>() {
            }.getType();
            CommentDto response = modelMapper.map(savedComment, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No comment found with the provided id in the database");
        }
    }

    @Transactional(readOnly = true)
    public Response getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            Type type = new TypeToken<CommentDto>() {
            }.getType();
            CommentDto response = modelMapper.map(comment, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No comment found with the provided id in the database");
        }
    }

    @Transactional(readOnly = true)
    public Response getCommentsByUserId(Long userId, Pageable pageable) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Page<Comment> commentPage = commentRepository.findByUser(user.get(), pageable);
            List<CommentDto> comments = commentPage.getContent().stream()
                    .map(comment -> modelMapper.map(comment, CommentDto.class))
                    .toList();

            if (!comments.isEmpty()) {
                return responseUtil.generateSuccessResponse(comments);
            } else {
                return responseUtil.generateFailureResponse("No comments found in The Database against the userId: " + userId);
            }
        } else {
            return responseUtil.generateFailureResponse("No user found with the provided id in the database");
        }
    }

    @Transactional(readOnly = true)
    public Response getCommentsByQuestionId(Long questionId, Pageable pageable) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent()) {
            Page<Comment> commentPage = commentRepository.findByQuestion(question.get(), pageable);
            List<CommentDto> comments = commentPage.getContent().stream()
                    .map(comment -> modelMapper.map(comment, CommentDto.class))
                    .toList();

            if (!comments.isEmpty()) {
                return responseUtil.generateSuccessResponse(comments);
            } else {
                return responseUtil.generateFailureResponse("No comments found in The Database against the questionId: " + questionId);
            }
        } else {
            return responseUtil.generateFailureResponse("No question found with the provided id in the database");
        }
    }

    @Transactional
    public Response commentLiked(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            int likeCounts = comment.get().getLikeCounts();
            comment.get().setLikeCounts(likeCounts + 1);
            Comment savedComment = commentRepository.save(comment.get());
            Type type = new TypeToken<CommentDto>() {
            }.getType();
            CommentDto response = modelMapper.map(savedComment, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No comment found with the provided id in the database");
        }
    }

    @Transactional
    public Response commentDisliked(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            int dislikeCounts = comment.get().getDislikeCounts();
            comment.get().setDislikeCounts(dislikeCounts + 1);
            Comment savedComment = commentRepository.save(comment.get());
            Type type = new TypeToken<CommentDto>() {
            }.getType();
            CommentDto response = modelMapper.map(savedComment, type);
            return responseUtil.generateSuccessResponse(response);
        } else {
            return responseUtil.generateFailureResponse("No comment found with the provided id in the database");
        }
    }
}
