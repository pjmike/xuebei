package cn.pjmike.xuebei.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author pjmike
 * @create 2018-01-30 16:01
 **/
public class JwtToken {
    /**
     * 加密所需盐
     */
    private static final String SECRET = "secret";
    /**
     * 令牌过期时间5分钟
     */
    private static final Long TTLMILLS = 1000*60*5L;
    /**
     * 创建Token
     *
     * @param email
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String createToken(String email) throws UnsupportedEncodingException {
        Map<String, Object> header = new HashMap<String, Object>(16);
        long nowMillis = System.currentTimeMillis();
        header.put("type", "jwt");
        header.put("alg", "HS256");
        Date date = new Date(TTLMILLS+nowMillis);
        return JWT.create()
                .withHeader(header)
                .withClaim("email", email)
                //设置过期时间
                .withExpiresAt(date)
                .withNotBefore(new Date(nowMillis))
                .sign(Algorithm.HMAC256(SECRET));
    }
    public static String createTokenWithTime(String email,long TTLMils) throws UnsupportedEncodingException {
        //当前系统时间
        long nowMillis = System.currentTimeMillis();
        Date date = new Date(TTLMils);
        //利用hashmap设置JWT的头信息
        Map<String, Object> head = new HashMap<String, Object>(16);
        head.put("type", "jwt");
        head.put("alg", "HS256");
        return JWT.create()
                .withHeader(head)
                .withClaim("email", email)
                .withExpiresAt(date)
                .withNotBefore(new Date(nowMillis))
                .sign(Algorithm.HMAC256(SECRET));
    }
    /**
     * 验证token
     *
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException {
        //用加密方式
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaims();
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    public static boolean verifyTokenTime(String token) {
        DecodedJWT jwt = JWT.decode(token);
        Date date = jwt.getExpiresAt();
        Date now = jwt.getNotBefore();

//        System.out.println(date.getTime());
        //如果过期了，返回true
        if (date.getTime() > now.getTime()) {
            return false;
        }
        return true;
    }
}
