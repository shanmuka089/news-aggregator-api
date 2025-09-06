package com.example.new_aggregator.config.annotations;

import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword
{
    String message() default "Password must be 8-15 characters long and contain at least one special character.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
