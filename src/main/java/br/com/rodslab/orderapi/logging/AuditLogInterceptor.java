package br.com.rodslab.orderapi.logging;

import java.lang.annotation.Annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class AuditLogInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogInterceptor.class);
	private static final String POINTCUT = "@annotation(br.com.rodslab.orderapi.logging.AuditLog)";

	@AfterReturning(POINTCUT)
	public void success(final JoinPoint joinPoint) {
		logPointcut(AfterReturning.class, joinPoint);
		getAuditLogger(joinPoint).success();
	}

	@AfterThrowing(pointcut = POINTCUT, throwing = "t")
	public void failure(final JoinPoint joinPoint, final Throwable t) {
		logPointcut(AfterThrowing.class, joinPoint);

	}

	private static void logPointcut(final Class<? extends Annotation> annotationClass, final JoinPoint joinPoint) {
		if (LOGGER.isTraceEnabled()) {
			final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
			LOGGER.trace("@{} -> {}#{}", annotationClass.getSimpleName(), methodSignature.getDeclaringTypeName(), methodSignature.getName());
		}
	}

	private static AuditLogger getAuditLogger(final JoinPoint joinPoint) {
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		final AuditLog annotation = methodSignature.getMethod().getAnnotation(AuditLog.class);

		return AuditLogger.getInstance(annotation.value());
	}



}
