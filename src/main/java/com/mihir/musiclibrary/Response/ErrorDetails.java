package com.mihir.musiclibrary.Response;

import lombok.Data;

@Data
public class ErrorDetails {
    private String fieldName; // The field where the error occurred
    private String message;   // Validation error message

    public ErrorDetails(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
