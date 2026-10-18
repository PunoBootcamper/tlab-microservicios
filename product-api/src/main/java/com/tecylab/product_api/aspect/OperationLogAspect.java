package com.tecylab.product_api.aspect;

import com.tecylab.product_api.OperationLog;
import com.tecylab.product_api.model.OperationLogEntity;
import com.tecylab.product_api.repository.OperationLogRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OperationLogAspect {
    private final OperationLogRepository operationLogRepository;

    public OperationLogAspect(OperationLogRepository operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    @Around("@annotation(operationLog)")
    public Object createOperationLog(ProceedingJoinPoint joinPoint, OperationLog operationLog) throws Throwable {
        Object result = joinPoint.proceed();

        String description = resolveDescription(operationLog.description(), joinPoint, result);

        OperationLogEntity operationLogEntity = new OperationLogEntity();
        operationLogEntity.setOperation(operationLog.type().name());
        operationLogEntity.setDescription(description);
        operationLogEntity.setTimestamp(java.time.LocalDateTime.now());

        operationLogRepository.save(operationLogEntity);

        return result;
    }

    private String resolveDescription(String description, ProceedingJoinPoint joinPoint, Object result) {
        if(description == null || description.isEmpty()) {
            return joinPoint.getSignature().getName();
        }

        if(description.contains("#id")) {
            try {
                var method = result.getClass().getMethod("id");
                Object idValue = method.invoke(result);
                if(idValue != null) {
                    description = description.replace("#id", idValue.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return description;
    }
}