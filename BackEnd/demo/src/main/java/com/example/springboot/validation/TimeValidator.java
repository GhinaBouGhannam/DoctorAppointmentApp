package com.example.springboot.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeValidator implements ConstraintValidator<ValidTime, String> {
        @Override
        public void initialize(ValidTime constraintAnnotation) {}

        @Override
        public boolean isValid(String time, ConstraintValidatorContext context) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                LocalTime.parse(time, formatter);
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }

        }}

