package org.onap.so.adapters.nssmf.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.onap.so.adapters.nssmf.annotation.NssmfLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(100)
@Component
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("execution(* org.onap.so.adapters.nssmf.service..*(..))")
    public void serviceLogger() {

    }

    @Around("serviceLogger()")
    public Object around(ProceedingJoinPoint joinPoint) {
        try{
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            Class<?> targetClass = method.getDeclaringClass();

            StringBuilder classAndMethod = new StringBuilder();
            NssmfLogger classAnnotation = targetClass.getAnnotation(NssmfLogger.class);
            NssmfLogger methodAnnotation = method.getAnnotation(NssmfLogger.class);

            if (classAnnotation == null && methodAnnotation == null) {
                return joinPoint.proceed();
            }

            if (classAnnotation != null) {
                if (classAnnotation.ignore()) {
                    return joinPoint.proceed();
                }
                classAndMethod.append(classAnnotation.value()).append("-");
            }

            String target = targetClass.getName() + "#" + method.getName();

            String params = NssmfAdapterUtil.marshal(joinPoint.getArgs());

            logger.info("{} {}, \nParams: {}", classAndMethod.toString(), target, params);

            long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long timeConsuming = System.currentTimeMillis() - start;

            logger.info("\n{}{} end, spend time: {}ms \nresult: {}",
                    classAndMethod.toString(), target, timeConsuming, NssmfAdapterUtil.marshal(result));
            return result;

        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
