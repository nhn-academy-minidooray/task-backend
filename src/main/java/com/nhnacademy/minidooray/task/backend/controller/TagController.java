package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.dto.TagDTO;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.ProjectIdOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.TagNameOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.TagRegisterRequest;
import com.nhnacademy.minidooray.task.backend.service.TagService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<TagDTO>> getTags(ProjectIdOnlyRequest request){
        List<TagDTO> tagList = tagService.findAllByProjectId(request);

        return tagList.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(tagList);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createTag(@RequestBody TagRegisterRequest request) {
        boolean isProcessed = tagService.createTag(request);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/{tagId}/modify")
    public ResponseEntity<Void> modifyTag(@PathVariable("tagId") Long id,
                                          @RequestBody TagNameOnlyRequest request){
        boolean isProcessed = tagService.modifyTag(id, request);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/{tagId}/delete")
    public ResponseEntity<Void> deleteTag(@PathVariable("tagId") Long tagId) {
        boolean isProcessed = tagService.deleteTag(tagId);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
