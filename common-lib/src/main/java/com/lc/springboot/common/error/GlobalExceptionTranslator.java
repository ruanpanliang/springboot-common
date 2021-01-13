package com.lc.springboot.common.error;

import com.lc.springboot.common.api.BaseResponse;
import com.lc.springboot.common.api.ResultCode;
import com.lc.springboot.common.auth.PermissionDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * restController 全局处理异常类
 *
 * @author liangchao
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionTranslator {

//  static final Ilog log = SlogFactory.getlog(GlobalExceptionTranslator.class);

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public BaseResponse handleError(MissingServletRequestParameterException e) {
    log.warn("Missing Request Parameter", e);
    String message = String.format("Missing Request Parameter: %s", e.getParameterName());
    return BaseResponse.builder().code(ResultCode.PARAM_MISS).message(message).build();
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public BaseResponse handleError(MethodArgumentTypeMismatchException e) {
    log.warn("Method Argument Type Mismatch", e);
    String message = String.format("Method Argument Type Mismatch: %s", e.getName());
    return BaseResponse.builder().code(ResultCode.PARAM_TYPE_ERROR).message(message).build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public BaseResponse handleError(MethodArgumentNotValidException e) {
    log.warn("Method Argument Not Valid", e);
    BindingResult result = e.getBindingResult();
    FieldError error = result.getFieldError();
    String message = String.format("%s", error.getDefaultMessage());
    // String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
    return BaseResponse.builder().code(ResultCode.PARAM_VALID_ERROR).message(message).build();
  }

  /**
   * 非法参数
   *
   * @param e 异常类
   * @return 响应类基本信息
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public BaseResponse handleError(IllegalArgumentException e) {
    log.warn("IllegalArgument", e);
    String message = String.format("%s:%s", "", e.getLocalizedMessage());
    return BaseResponse.builder().code(ResultCode.PARAM_VALID_ERROR).message(message).build();
  }

  @ExceptionHandler(BindException.class)
  public BaseResponse handleError(BindException e) {
    log.warn("Bind Exception", e);
    FieldError error = e.getFieldError();
    String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
    return BaseResponse.builder().code(ResultCode.PARAM_BIND_ERROR).message(message).build();
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public BaseResponse handleError(ConstraintViolationException e) {
    log.warn("Constraint Violation", e);
    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    ConstraintViolation<?> violation = violations.iterator().next();
    String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
    String message = String.format("%s:%s", path, violation.getMessage());
    return BaseResponse.builder().code(ResultCode.PARAM_VALID_ERROR).message(message).build();
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public BaseResponse handleError(NoHandlerFoundException e) {
    log.error("404 Not Found", e);
    return BaseResponse.builder().code(ResultCode.NOT_FOUND).message(e.getMessage()).build();
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public BaseResponse handleError(HttpMessageNotReadableException e) {
    log.error("Message Not Readable", e);
    String message = e.getMessage();
    if (StringUtils.isNotEmpty(message) && message.indexOf(":") > 0) {
      message = message.substring(0, message.indexOf(":"));
    }
    return BaseResponse.builder().code(ResultCode.MSG_NOT_READABLE).message(message).build();
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public BaseResponse handleError(HttpRequestMethodNotSupportedException e) {
    log.error("Request Method Not Supported", e);
    return BaseResponse.builder()
        .code(ResultCode.METHOD_NOT_SUPPORTED)
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public BaseResponse handleError(HttpMediaTypeNotSupportedException e) {
    log.error("Media Type Not Supported", e);
    return BaseResponse.builder()
        .code(ResultCode.MEDIA_TYPE_NOT_SUPPORTED)
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(ServiceException.class)
  public BaseResponse handleError(ServiceException e) {
    log.error("Service Exception", e);
    return BaseResponse.builder().code(e.getResultCode()).message(e.getMessage()).build();
  }

  @ExceptionHandler(PermissionDeniedException.class)
  public BaseResponse handleError(PermissionDeniedException e) {
    log.error("Permission Denied", e);
    return BaseResponse.builder().code(e.getResultCode()).message(e.getMessage()).build();
  }

  /**
   * 全局异常处理方法
   *
   * @param e 异常类
   * @return 异常封装结果信息
   */
  @ExceptionHandler(Throwable.class)
  public BaseResponse handleError(Throwable e) {
    log.error("Internal Server Error", e);
    return BaseResponse.builder()
        .code(ResultCode.INTERNAL_SERVER_ERROR)
        .message(e.getMessage())
        .build();
  }
}
