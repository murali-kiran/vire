package com.vire.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class PerformanceLogger {

    @Pointcut("within(com.vire.repository.*)")
    public void repositoryClassMethods() {};

    @Pointcut("within(com.vire.service.*)")
    public void serviceClassMethods() {};

    @Around("repositoryClassMethods() || serviceClassMethods()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        Throwable throwable = null;
        Object retval = null;

        try {
            retval = pjp.proceed();
        }catch (Throwable e){
            throwable = e;
        }

        long end = System.nanoTime();
        String methodName = pjp.getSignature().getName();
        String classname = pjp.getSignature().getDeclaringType().getName();
        var args = pjp.getArgs();
        StringBuffer stringBuffer = new StringBuffer("");

        if(args != null && args.length > 0){
            Arrays.stream(args).forEach(i -> {
                if(i!=null)
                    stringBuffer.append(i.toString()+"##");
                else
                    stringBuffer.append("null##");
            });
        }

        if(throwable !=null){
            log.info("Execution of " + classname+ "." + methodName + ": args " +
                    stringBuffer.toString() + ": throwed exception "+ throwable.getMessage());
            throw throwable;
        }

        log.info("Execution of " + classname+ "." + methodName + ": args " +
                stringBuffer.toString() + ": took " +
                TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");

        return retval;
    }
}
