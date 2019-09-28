package ru.javawebinar.topjava.util.exception;

public enum ErrorType {
    APP_ERROR("error.appError"),
    DATA_NOT_FOUND("error.dataNotFound"),
    DATA_ERROR("error.dataError"),
    VALIDATION_ERROR("error.validationError"),
    WRONG_REQUEST("error.wrongRequest");

    private final String errorCode;

    ErrorType(String errCode) {
        this.errorCode = errCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
