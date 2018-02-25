package cn.pjmike.xuebei.utils;

import org.springframework.util.DigestUtils;

/**
 * md5工具类
 *
 * @author pjmike
 * @create 2017-11-29 21:03
 **/
public class MD5Util {
    private static String salt = "pjmike";
    public static String getMD5(String password) {
        String str = password + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(str.getBytes());
        return md5;
    }
}
