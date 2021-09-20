package io.github.cloudgyb.springsecurity02.config;


import java.util.List;

import io.github.cloudgyb.springsecurity02.common.po.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author cloudgyb
 * 2021/9/5 20:10
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 认证时认证失败抛出AuthenticationException实例
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResult handleAuthenticationException(AuthenticationException e){
        logger.error(e.getLocalizedMessage());
        return ApiResult.error(HttpStatus.UNAUTHORIZED.value(),"用户名或密码错误！");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResult handleAccessDeniedException(AccessDeniedException e){
        logger.error(e.getLocalizedMessage());
        return ApiResult.error(HttpStatus.FORBIDDEN.value(),e.getLocalizedMessage());
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResult handleUsernameNotFoundException(HttpRequestMethodNotSupportedException e){
        logger.error(e.getLocalizedMessage());
        return ApiResult.error(HttpStatus.METHOD_NOT_ALLOWED.value(),e.getMethod()+"不允许");
    }

    //**********客户端请求错误处理开始**************//
    //1. 接口必须是RequestBody但客户端未按照Content-Type:application/json的方式进行参数上传
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleException(HttpMessageNotReadableException e){
        logger.error(e.getLocalizedMessage());
        return ApiResult.error(HttpStatus.BAD_REQUEST.value(),"客户端请求错误！");
    }

    //1. 客户端未设置正确的Content-Type
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleException(HttpMediaTypeNotSupportedException e){
        logger.error(e.getLocalizedMessage());
        return ApiResult.error(HttpStatus.BAD_REQUEST.value(),"客户端请求错误！");
    }

    //2. 接口对应的Controller方法参数校验未通过
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleException(MethodArgumentNotValidException e){
        logger.error(e.getLocalizedMessage());
        final BindingResult bindingResult = e.getBindingResult();
        final List<ObjectError> allErrors = bindingResult.getAllErrors();
        final String message = allErrors.get(0).getDefaultMessage();
        return ApiResult.error(HttpStatus.BAD_REQUEST.value(),
                String.format("客户端请求错误，%s！", message));
    }

    //3. 接口对应的Controller方法的参数上标注了@RequestParam，但客户端未传参
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleException(MissingServletRequestParameterException e){
        logger.error(e.getLocalizedMessage());
        final String parameterName = e.getParameterName();
        return ApiResult.error(HttpStatus.BAD_REQUEST.value(),
                String.format("客户端请求错误，丢失‘%s’！", parameterName));
    }
    //*********客户端请求错误处理结束**************//

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult handleException(Exception e){
        logger.error(e.getLocalizedMessage());
        e.printStackTrace();
        return ApiResult.error(HttpStatus.METHOD_NOT_ALLOWED.value(),"服务器内部错误！");
    }



}
