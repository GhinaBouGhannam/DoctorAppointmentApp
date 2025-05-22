package com.example.springboot.validation;

    import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = TimeValidator.class)
    @Documented
    public @interface ValidTime {
        String message() default "Invalid time format";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }



