package cn.pjmike.xuebei.web.exception;

import java.io.IOException;

/**
 * @author pjmike
 * @create 2018-01-31 13:47
 **/
public class EncodingException extends IOException {
    public EncodingException() {
    }

    public EncodingException(String message) {
        super(message);
    }

    public EncodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncodingException(Throwable cause) {
        super(cause);
    }
}
