package cn.pjmike.xuebei.web.controller;

import cn.pjmike.xuebei.web.exception.NullException;
import cn.pjmike.xuebei.web.exception.UserException;
import cn.pjmike.xuebei.utils.ResponseResult;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.SendFailedException;
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
        result.setMsg("参数校验失败");
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
    public ResponseResult<Object> EncodingExceptionHandler(UnsupportedEncodingException e) {

        return new ResponseResult<Object>(1,"系统编码错误");
    }
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult<Object> IOExceptionHandler(IOException e) {
        return new ResponseResult<Object>(1,"读取文件错误");
    }

    @ExceptionHandler(UserException.class)
    public ResponseResult<Object> UserExceptionHandler(UserException e) {
        return new ResponseResult<Object>(1,e.getMessage());
    }

    /**
     * 对邮箱地址进行校验
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(SendFailedException.class)
    public ResponseResult<Object> SendFailedExceptionHandler(SendFailedException ex) {
        ResponseResult<Object> result = new ResponseResult<Object>();
        result.setCode(1);
        result.setMsg("该邮箱地址不存在");
        return result;
    }
}
