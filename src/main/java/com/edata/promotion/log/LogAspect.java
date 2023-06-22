package com.edata.promotion.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 请求日志统一处理
 * @author yugt 2021/10/20
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(public * com.edata.promotion.controller.*.*(..))")
    public void getControllerPointCut(){

    }

    @Around("getControllerPointCut()")
    public Object processControllerLog(ProceedingJoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestURI = request.getRequestURI();
        Map paramMap = request.getParameterMap();
        // log.info("requestURI :{}, param: {}", requestURI, paramMap);
        Object object = null;
        try{
            object = joinPoint.proceed();
        }catch (Throwable e){
            log.error("",e);
        }
        return object;
    }

}
