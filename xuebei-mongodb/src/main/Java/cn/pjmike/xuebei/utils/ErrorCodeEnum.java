package cn.pjmike.xuebei.utils;

/**
 * 响应错误提示枚举类
 *
 * @author pjmike
 * @create 2018-02-28 21:16
 */
public enum  ErrorCodeEnum {
    LENDER_NOT_CHANGE("请先移交群主");
    private String msg;

    ErrorCodeEnum() {
    }

    ErrorCodeEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
