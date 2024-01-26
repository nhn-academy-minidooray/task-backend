package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.requestbody.TagNameOnlyRequest;
import com.nhnacademy.minidooray.task.backend.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/{projectId}/tag/register")
    public ResponseEntity<Void> createTag(@PathVariable("projectId") Long projectId,
                                          @RequestBody TagNameOnlyRequest request) {
        boolean isProcessed = tagService.createTag(projectId, request);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/project/tag/{tagId}")
    public ResponseEntity<Void> modifyTag(@PathVariable("tagId") Long tagId,
                                          @RequestBody TagNameOnlyRequest request){
        boolean isProcessed = tagService.modifyTag(tagId, request);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/project/tag/{tagId}/delete")
    public ResponseEntity<Void> deleteTag(@PathVariable("tagId") Long tagId) {
        boolean isProcessed = tagService.deleteTag(tagId);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
