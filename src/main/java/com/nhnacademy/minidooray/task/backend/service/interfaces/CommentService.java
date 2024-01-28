package com.nhnacademy.minidooray.task.backend.service.interfaces;

import com.nhnacademy.minidooray.task.backend.domain.dto.comment.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentRequest;
import java.util.List;

public interface CommentService {
    List<CommentDto> findCommentListByTask(Long taskId);

    boolean createComment(CommentRequest commentRequest);

    boolean modifyComment(Long commentId, CommentModifyRequest commentModifyRequest);

    boolean deleteComment(Long commentId);
}
