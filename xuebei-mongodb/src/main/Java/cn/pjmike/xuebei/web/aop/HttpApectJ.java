package cn.pjmike.xuebei.web.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 使用AOP统一处理请求日志
 *
 * @author pjmike
 * @create 2018-02-03 17:40
 **/
@Aspect
@Component
@EnableAspectJAutoProxy
public class HttpApectJ {
    private final static Logger logger = LoggerFactory.getLogger(HttpApectJ.class);

    @Pointcut("execution(public * cn.pjmike.xuebei.web.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint point) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //请求URL
        logger.info("url={}", request.getRequestURL());
        //请求方法
        logger.info("HTTP_Method={}", request.getMethod());
        //请求IP
        logger.info("IP={}", request.getRemoteAddr());
        //请求类的方法
        logger.info("Class_Method={}", point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        //请求类方法的参数
        logger.info("Args={}", Arrays.asList(point.getArgs()));
    }

    @AfterReturning(returning = "object", pointcut = "webLog()")
    public void doAfterReturn(Object object) {
        logger.info("response={}",object.toString());
    }
}
