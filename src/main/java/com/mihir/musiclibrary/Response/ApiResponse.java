package com.mihir.musiclibrary.Response;

import java.util.List;

public class ApiResponse<T> {
    private int status;
    private T data;
    private String message;
    private List<ErrorDetails> error;

    public ApiResponse(int status, T data, String message, List<ErrorDetails> error) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorDetails> getError() {
        return error;
    }

    public void setError(List<ErrorDetails> error) {
        this.error = error;
    }
}