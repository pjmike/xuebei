package cn.pjmike.xuebei.web.exception;

/**
 * @author pjmike
 * @create 2018-02-01 15:35
 **/
public class NoPermissionException extends RuntimeException{
    public NoPermissionException() {
    }

    public NoPermissionException(String message) {
        super(message);
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPermissionException(Throwable cause) {
        super(cause);
    }

    public NoPermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
