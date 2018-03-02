package cn.pjmike.xuebei.utils;

import java.util.UUID;

/**
 * 验证码生成器
 *
 * @author pjmike
 * @create 2018-01-29 23:26
 **/
public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
