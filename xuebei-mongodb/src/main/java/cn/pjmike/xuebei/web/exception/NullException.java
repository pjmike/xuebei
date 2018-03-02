package cn.pjmike.xuebei.web.exception;

/**
 * 空异常
 *
 * @author pjmike
 * @create 2018-01-31 14:25
 **/
public class NullException extends RuntimeException{
    public NullException() {
    }

    public NullException(String message) {
        super(message);
    }

    public NullException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullException(Throwable cause) {
        super(cause);
    }

    public NullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
