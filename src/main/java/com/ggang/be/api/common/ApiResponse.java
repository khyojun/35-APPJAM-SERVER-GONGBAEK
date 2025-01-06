package com.ggang.be.api.common;

import lombok.Getter;

@Getter
public class  ApiResponse<T> {
    boolean success;
    int code;
    String message;
    T data;

    public static <T> ApiResponse<T> success(ResponseSuccess responseSuccess, T data) {
        return new ApiResponse<>(true, responseSuccess.getCode(), responseSuccess.getMessage(), data);
    }

    public static <T> ApiResponse <T> error(ResponseError responseError) {
        return new ApiResponse<>(false, responseError.getCode(), responseError.getMessage(), null);
    }

    public ApiResponse(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
//{
//        "success": false,
//        "code": 4000,
//        "message": "유효하지 않은 요청입니다.",
//        "data": null
//        }