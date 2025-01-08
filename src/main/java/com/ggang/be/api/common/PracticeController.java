package com.ggang.be.api.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.ggang.be.api.common.ResponseError.*;

@RestController
public class PracticeController {

    @GetMapping("/ok")
    public ResponseEntity<ApiResponse<Object>> ok() {
        return ResponseBuilder.ok(null);
    }

    @GetMapping("/created")
    public ResponseEntity<ApiResponse<Object>> created() {
        return ResponseBuilder.created("1");
    }

    @GetMapping("/custom-error")
    public ResponseEntity<ApiResponse<Void>> error() {
        return ResponseBuilder.error(INVALID_TOKEN);
    }

}