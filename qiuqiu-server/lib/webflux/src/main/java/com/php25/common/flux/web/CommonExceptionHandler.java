package com.php25.common.flux.web;

import com.php25.common.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;

/**
 * 统一异常处理
 *
 * @author penghuiping
 * @date 2019/7/18 09:41
 */
@SuppressWarnings("rawtypes")
@RestControllerAdvice
@ConditionalOnClass(HttpServletRequest.class)
public class CommonExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(value = {BindException.class, WebExchangeBindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<JSONResponse> handleBindException(BindException e) {
        log.error("请求访问参数错误!!", e);
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setCode(ApiErrorCode.input_params_error.getCode());
        FieldError fieldError = e.getFieldError();
        if (fieldError == null) {
            jsonResponse.setMessage(ApiErrorCode.input_params_error.getDesc());
        } else {
            jsonResponse.setMessage(fieldError.getField() + fieldError.getDefaultMessage());
        }
        return ResponseEntity.ok(jsonResponse);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, ConstraintDeclarationException.class, ServerWebInputException.class})
    public ResponseEntity<JSONResponse> handleInputParamsError(Exception e) {
        log.error("请求访问参数错误!!", e);
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setCode(ApiErrorCode.input_params_error.getCode());
        jsonResponse.setMessage(e.getMessage());
        return ResponseEntity.ok(jsonResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<JSONResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("请求http_request_method方式错误!!", e);
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setCode(ApiErrorCode.http_method_not_support.getCode());
        jsonResponse.setMessage(ApiErrorCode.http_method_not_support.getDesc());
        return ResponseEntity.ok(jsonResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<JSONResponse> handleBusinessException(BusinessException e) {
        log.error("出现业务错误!!", e);
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setCode(e.getCode());
        jsonResponse.setMessage(e.getMessage());
        return ResponseEntity.ok(jsonResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONResponse> handleException(Exception e) {
        log.error("服务器未知错误!!", e);
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setCode(ApiErrorCode.unknown_error.getCode());
        jsonResponse.setMessage(ApiErrorCode.unknown_error.getDesc());
        return ResponseEntity.ok(jsonResponse);
    }
}
