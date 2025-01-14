package com.ggang.be.api.comment.controller;

import com.ggang.be.api.comment.dto.ReadCommentRequest;
import com.ggang.be.api.comment.dto.ReadCommentResponse;
import com.ggang.be.api.comment.dto.WriteCommentRequest;
import com.ggang.be.api.comment.dto.WriteCommentResponse;
import com.ggang.be.api.common.ApiResponse;
import com.ggang.be.api.common.ResponseBuilder;
import com.ggang.be.api.facade.CommentFacade;
import com.ggang.be.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentFacade commentFacade;
    private final JwtService jwtService;

    @PostMapping("/comment")
    public ResponseEntity<ApiResponse<WriteCommentResponse>> writeComment(@RequestHeader("Authorization") final String token, @RequestBody final WriteCommentRequest dto){
        Long userId = jwtService.parseTokenAndGetUserId(token);
        return ResponseBuilder.created(commentFacade.writeComment(userId, dto));
    }

    @GetMapping("/comments")
    public ResponseEntity<ApiResponse<ReadCommentResponse>> getComments(@RequestHeader("Authorization") final String token, @RequestParam boolean isPublic, @RequestBody final ReadCommentRequest dto){
        jwtService.isValidToken(token);
        return ResponseBuilder.ok(commentFacade.readComment(isPublic, dto));
    }

}