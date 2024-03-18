package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateConstraint, LocalDate> {

  @Override
  public void initialize(ReleaseDateConstraint birthdayDate) {
  }

  @Override
  public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
    return date.isAfter(LocalDate.of(1895, 12, 28));
  }
}
