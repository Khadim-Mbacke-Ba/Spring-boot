package sn.ucad.restou.entity;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NumeroCarteValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNumeroCarte {

    String message() default "Numéro de carte invalide";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}