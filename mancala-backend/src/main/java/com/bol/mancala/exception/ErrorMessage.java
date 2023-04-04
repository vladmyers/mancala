package com.bol.mancala.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error messages for user
 */
@Getter
@AllArgsConstructor
public enum ErrorMessage {

    DEFAULT("Something went wrong..."),
    CONTROLLER_NO_HANDLER_FOUND("Controller handler was not found"),
    CONTROLLER_HTTP_REQUEST_METHOD_NOT_SUPPORTED("Controller http request method not supported"),
    CONTROLLER_HTTP_MESSAGE_NOT_READABLE("Controller HTTP message is not readable"),
    PAGE_NOT_FOUND("Page was not found"),
    VALIDATION_FAILED("Validation has failed"),
    MISSING_REQUEST_PARAMETER("Request parameter is missing"),
    ARGUMENT_TYPE_MISMATCH("Argument type mismatch");

    private final String message;

}
