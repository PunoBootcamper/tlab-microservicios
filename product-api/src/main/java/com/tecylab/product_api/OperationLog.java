package com.tecylab.product_api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {
    String description() default "";
    OperationType type() default OperationType.READ;

    enum OperationType {
        CREATE,
        READ,
        UPDATE,
        DELETE,
        OTHER,
        ERROR
    }
}
