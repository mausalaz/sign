package com.bci.sign.handleError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bci.sign.response.ErrorResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    // 400

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),ex.getLocalizedMessage());
        final ApiError apiError = new ApiError(Arrays.asList(errorResponse));
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),ex.getLocalizedMessage());
        final ApiError apiError = new ApiError(Arrays.asList(errorResponse));
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),error);
        final ApiError apiError = new ApiError(Arrays.asList(errorResponse));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = ex.getRequestPartName() + " part is missing";
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),error);
        final ApiError apiError = new ApiError(Arrays.asList(errorResponse));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = ex.getParameterName() + " parameter is missing";
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),error);
        final ApiError apiError = new ApiError(Arrays.asList(errorResponse));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),error);
        final ApiError apiError = new ApiError(Arrays.asList(errorResponse));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 404

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),error);
        final ApiError apiError = new ApiError(Arrays.asList(errorResponse));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),  HttpStatus.NOT_FOUND);
    }

    // 405

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.METHOD_NOT_ALLOWED.value(),builder.toString());
        final ApiError apiError = new ApiError(Arrays.asList(errorResponse));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),builder.substring(0, builder.length() - 2));
        final ApiError apiError = new ApiError(Arrays.asList(errorResponse));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // 500

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getLocalizedMessage());
        final ApiError apiError = new ApiError(Arrays.asList(errorResponse));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
