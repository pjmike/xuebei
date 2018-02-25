package cn.pjmike.xuebei.web.controller;

import cn.pjmike.xuebei.web.exception.NullException;
import cn.pjmike.xuebei.web.exception.UserException;
import cn.pjmike.xuebei.utils.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常处理类
 *
 * @author pjmike
 * @create 2018-01-29 22:50
 **/
@RestControllerAdvice
public class OwnerExceptionController {
    private ResponseResult<Object> result;
    @ExceptionHandler(NullException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseResult<Object> nullExceptionHandler(NullException ex) {
        result = new ResponseResult<Object>();
        result.setCode(1);
        result.setMsg(ex.getMessage());
        return result;
    }

    /**
     * 参数校验错误的异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult<Object> handlerValidationException(MethodArgumentNotValidException ex) {
        result = new ResponseResult<Object>();
        result.setCode(1);
        result.setMsg("校验失败");
        result.setData(getErrors(ex.getBindingResult()));
        return result;
    }

    /**
     * 将属性校验失败后的信息放入map，返回给前端
     *
     * @param result
     * @return
     */
    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<String, String>();
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors) {
            map.put(error.getField(), error.getDefaultMessage());
        }
        return map;
    }
    @ExceptionHandler(UnsupportedEncodingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> EncodingExceptionHandler(Exception e) {
        Map<String, String> map = new HashMap<String, String>(16);
        map.put("status", "1");
        map.put("message", "系统编码错误");
        return map;
    }
    @ExceptionHandler(IOException.class)
    public Map<String, String> IOExceptionHandler(Exception e) {
        Map<String, String> map = new HashMap<String, String>(16);
        map.put("status", "1");
        map.put("message", "编码错误");
        return map;
    }

    @ExceptionHandler(UserException.class)
    public Map<String, String> UserExceptionHandler(UserException e) {
        Map<String, String> map = new HashMap<String, String>(16);
        map.put("status", "1");
        map.put("message", e.getMessage());
        return map;
    }
}
