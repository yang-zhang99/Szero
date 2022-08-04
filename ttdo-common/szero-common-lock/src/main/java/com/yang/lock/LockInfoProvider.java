package com.yang.lock;



import com.yang.lock.annotation.Lock;
import com.yang.lock.annotation.LockKey;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class LockInfoProvider {

    private ParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    private ExpressionParser parser = new SpelExpressionParser();

    public LockInfo getLockInfo(JoinPoint joinPoint, Lock lock) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        List<String> keyList = this.getKeyList(joinPoint, lock);
        String lockName = this.getLockName(lock.name(), signature, keyList);
        long waitTime = this.getLockWaitTime(lock);
        long leaseTime = this.getLockLeaseTime(lock);
        TimeUnit timeUnit = this.getLockTimeUnit(lock);
        return new LockInfo(lockName, waitTime, leaseTime, timeUnit);
    }

    private List<String> getKeyList(JoinPoint joinPoint, Lock lock) {
        Method method = this.getMethod(joinPoint);
        List<String> definitionKeys = this.getSpelDefinitionKey(lock.keys(), method, joinPoint.getArgs());
        List<String> keyList = new ArrayList(definitionKeys);
        List<String> parameterKeys = this.getParameterKey(method.getParameters(), joinPoint.getArgs());
        keyList.addAll(parameterKeys);
        return keyList;
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), method.getParameterTypes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return method;
    }

    // spel 表达式运算
    private List<String> getSpelDefinitionKey(String[] definitionKeys, Method method, Object[] parameterValues) {
        List<String> definitionKeyList = new ArrayList();

        for (int i = 0; i < definitionKeys.length; i++) {
            if (definitionKeys[i] != null && !definitionKeys[i].isEmpty()) {
                EvaluationContext context = new MethodBasedEvaluationContext((Object) null, method, parameterValues, this.nameDiscoverer);
                Object value = this.parser.parseExpression(definitionKeys[i]).getValue(context);
                if (value != null) {
                    definitionKeyList.add(value.toString());
                }
            }
        }
        return definitionKeyList;
    }

    private List<String> getParameterKey(Parameter[] parameters, Object[] parameterValues) {
        List<String> parameterKey = new ArrayList();
        for (int i = 0; i < parameters.length; ++i) {
            if (parameters[i].getAnnotation(LockKey.class) != null) {
                LockKey keyAnnotation = parameters[i].getAnnotation(LockKey.class);
                if (keyAnnotation.value().isEmpty()) {
                    parameterKey.add(parameterValues[i].toString());
                } else {
                    StandardEvaluationContext context = new StandardEvaluationContext(parameterValues[i]);
                    Object value = this.parser.parseExpression(keyAnnotation.value()).getValue(context);
                    if (value != null) {
                        parameterKey.add(value.toString());
                    }
                }
            }
        }

        return parameterKey;
    }

    private String getLockName(String annotationName, MethodSignature signature, List<String> keyList) {
        return annotationName.isEmpty() ? String.format("%s:%s.%s.%s", "lock", signature.getDeclaringTypeName(), signature.getMethod().getName(), StringUtils.collectionToDelimitedString(keyList, "", "-", "")) : annotationName;
    }

    private long getLockWaitTime(Lock lock) {
        return lock.waitTime();
    }

    private long getLockLeaseTime(Lock lock) {
        return lock.leaseTime();
    }

    private TimeUnit getLockTimeUnit(Lock lock) {
        return lock.timeunit();
    }

}
