package com.nhnacademy.minidooray.task.backend.repository;

import com.nhnacademy.minidooray.task.backend.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT id, content from Comment WHERE task.id = :taskId")
    List<List<Object>> commentListByTaskId(@Param("taskId") Long taskId);

    Comment getCommentById(Long commentId);

}