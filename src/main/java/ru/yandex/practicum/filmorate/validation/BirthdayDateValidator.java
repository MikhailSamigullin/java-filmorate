package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BirthdayDateValidator implements ConstraintValidator<BirthdayDateConstraint, LocalDate> {
  @Override
  public void initialize(BirthdayDateConstraint birthdayDate) {
  }

  @Override
  public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
    return date.isBefore(LocalDate.now());
  }
}
