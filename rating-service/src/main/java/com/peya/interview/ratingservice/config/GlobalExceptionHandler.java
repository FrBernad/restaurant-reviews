package com.peya.interview.ratingservice.config;

import com.peya.interview.ratingservice.model.dto.response.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      final Exception ex,
      final Object body,
      final HttpHeaders headers,
      final HttpStatusCode statusCode,
      final WebRequest request) {
    log.error(ex.getMessage(), ex);
    return super.handleExceptionInternal(ex, body, headers, statusCode, request);
  }

  @Override
  protected ResponseEntity<Object> createResponseEntity(
      final Object body,
      final HttpHeaders headers,
      final HttpStatusCode statusCode,
      final WebRequest request) {
    final var path = ((ServletWebRequest) request).getRequest().getRequestURI();
    final var status = statusCode.value();

    final var title =
        HttpStatus.resolve(status) != null ? HttpStatus.resolve(status).getReasonPhrase() : "Error";

    final var message = extractMessage(body, status, title);

    final var apiError = new ApiError(Instant.now(), status, title, message, path);

    return new ResponseEntity<>(apiError, headers, statusCode);
  }

  private String extractMessage(final Object body, final int status, final String fallbackTitle) {
    if (body instanceof org.springframework.http.ProblemDetail pd) {
      if (pd.getDetail() != null && !pd.getDetail().isBlank()) return pd.getDetail();
      if (pd.getTitle() != null && !pd.getTitle().isBlank()) return pd.getTitle();
      return fallbackTitle;
    }
    if (body instanceof String s && !s.isBlank()) return s;

    if (status >= 500) return "An unexpected error occurred";
    return fallbackTitle;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGeneric(final Exception ex, final WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
