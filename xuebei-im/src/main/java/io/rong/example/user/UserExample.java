package io.rong.example.user;

import io.rong.RongCloud;
import io.rong.methods.user.User;
import io.rong.models.*;
import io.rong.models.response.*;
import io.rong.models.user.UserModel;

/**
 * Demo class
 *
 * @author hc
 * @date 2017/12/30
 */
public class UserExample {
    private static String appKey = "25wehl3u2sjuw";

    private static String appSecret = "xDobXIDLyI6y1";
    /**
     * 自定义api地址
     * */
    private static final String api = "http://api.cn.ronghub.com";

    public static void main(String[] args) throws Exception {

        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        //自定义 api 地址方式
        // RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret,api);
        User userRongCloud = rongCloud.user;

        /**
         * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/user/user.html#register
         *
         * 注册用户，生成用户在融云的唯一身份标识 Token
         */
        UserModel user = new UserModel()
                .setId("userxxd1")
                .setName("username")
                .setPortrait("http://www.rongcloud.cn/images/logo.png");
        TokenResult result = userRongCloud.register(user);
        System.out.println("getToken:  " + result.toString());

        /**
         *
         * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/user/user.html#refresh
         *
         * 刷新用户信息方法
         */
        Result refreshResult = userRongCloud.update(user);
        System.out.println("refresh:  " + refreshResult.toString());

    }
}
