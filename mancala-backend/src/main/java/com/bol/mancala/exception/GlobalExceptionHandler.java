package com.bol.mancala.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.bol.mancala.exception.RestResponse.error;

/**
 * Handles exceptions thrown by controllers and returns appropriate error responses to the client
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public RestResponse<Void> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error(ErrorMessage.CONTROLLER_NO_HANDLER_FOUND.getMessage(), e);
        return error(e, ErrorMessage.CONTROLLER_NO_HANDLER_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<Void> handleMissingRequestParameterException(MissingServletRequestParameterException e) {
        log.error(ErrorMessage.MISSING_REQUEST_PARAMETER.getMessage(), e);
        return error(e, ErrorMessage.MISSING_REQUEST_PARAMETER);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<Void> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(ErrorMessage.ARGUMENT_TYPE_MISMATCH.getMessage(), e);
        return error(e, ErrorMessage.ARGUMENT_TYPE_MISMATCH);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(ErrorMessage.VALIDATION_FAILED.getMessage(), e);
        return error(e, ErrorMessage.VALIDATION_FAILED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(ErrorMessage.VALIDATION_FAILED.getMessage(), e);
        return error(e, ErrorMessage.VALIDATION_FAILED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error(ErrorMessage.CONTROLLER_HTTP_REQUEST_METHOD_NOT_SUPPORTED.getMessage(), e);
        return error(e, ErrorMessage.CONTROLLER_HTTP_REQUEST_METHOD_NOT_SUPPORTED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<Void> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.error(ErrorMessage.CONTROLLER_HTTP_MESSAGE_NOT_READABLE.getMessage(), e);
        return error(e, ErrorMessage.CONTROLLER_HTTP_MESSAGE_NOT_READABLE);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<Void> handleOtherException(Exception e) {
        log.error(ErrorMessage.DEFAULT.getMessage(), e);
        return error(e, ErrorMessage.DEFAULT);
    }

}
