package ru.geekbrains.validation;


import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static java.util.Arrays.asList;


public class PasswordConstraintValidator
        implements ConstraintValidator<PasswordConstraint, String> {


    private static final List<Rule> RULES = asList(new LengthRule(8, 80),
            new UppercaseCharacterRule(1),
            new DigitCharacterRule(1),
            new SpecialCharacterRule(1),
            new NumericalSequenceRule(3, false),
            new AlphabeticalSequenceRule(3, false),
            new QwertySequenceRule(3, false),
            new WhitespaceRule());


    @Override
    public void initialize(PasswordConstraint annotation) {

    }


    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(RULES);
        PasswordData data = new PasswordData(password);
        RuleResult result = validator.validate(data);

        if (result.isValid())
            return true;

//	List<String> messages = validator.getMessages(result);
//	String template = String.join(",", messages);

//	context.disableDefaultConstraintViolation();
//	context
//			.buildConstraintViolationWithTemplate(template)
//			.addConstraintViolation();

        return false;
    }

}