package com.nhnacademy.minidooray.task.backend.service;

import com.nhnacademy.minidooray.task.backend.domain.dto.comment.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentRequest;
import com.nhnacademy.minidooray.task.backend.entity.Comment;
import com.nhnacademy.minidooray.task.backend.entity.Task;
import com.nhnacademy.minidooray.task.backend.repository.CommentRepository;
import com.nhnacademy.minidooray.task.backend.repository.TaskRepository;
import com.nhnacademy.minidooray.task.backend.service.interfaces.CommentService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findCommentListByTask(Long taskId) {
        return commentRepository.findAllByTask_Id(taskId);
    }

    @Transactional
    @Override
    public boolean createComment(CommentRequest commentRequest) {
        Optional<Task> task = taskRepository.getTaskById(commentRequest.getTaskId());

        if(task.isPresent()){
            Comment comment = Comment.builder()
                    .owner(commentRequest.getOwner())
                    .content(commentRequest.getContent())
                    .task(task.get())
                    .build();

            commentRepository.save(comment);

            return true;
        }

        log.error("createComment() : Not found task by taskId {}", commentRequest.getTaskId());

        return false;
    }

    @Transactional
    @Override
    public boolean modifyComment(Long commentId, CommentModifyRequest commentModifyRequest) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isPresent()) {
            Comment modified = comment.get();
            modified.setOwner(commentModifyRequest.getOwner());
            modified.setContent(commentModifyRequest.getContent());
            commentRepository.save(modified);

            return true;
        }

        log.error("modifyComment() : Not found comment by commentId {}", commentId);

        return false;
    }

    @Transactional
    @Override
    public boolean deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);

            return true;
        }

        log.error("deleteComment() : Not exist comment by commentId {}", commentId);

        return false;
    }
}
