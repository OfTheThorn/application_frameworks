package com.matthiasvdd.opdracht.validation;

import com.matthiasvdd.opdracht.models.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.passay.*;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<ValidPassword, Object> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        User u = (User) value;
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/messages/messages.properties"));
            props.load(new FileInputStream("src/main/resources/messages/messages_nl.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessageResolver resolver = new PropertiesMessageResolver(props);

        PasswordValidator passwordValidator = new PasswordValidator(resolver,
                new LengthRule(8, 30),
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),

                // no whitespace
                new WhitespaceRule(),

                // not same as username
                new UsernameRule()

        );
        PasswordData passwordData = new PasswordData(u.getPassword());
        passwordData.setUsername(u.getUsername());
        RuleResult ruleResult = passwordValidator.validate(passwordData);


        if (ruleResult.isValid()) {
            // Check if passwords match
            boolean isValid = u.getPassword().equals(u.getConfirmPassword());
            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("password").addConstraintViolation();
            }
            return isValid;
        } else {
            List<String> messages = passwordValidator.getMessages(ruleResult);
            String messageTemplate = String.join(",", messages);
            context.buildConstraintViolationWithTemplate(messageTemplate)
                    .addPropertyNode("password")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }

    }
}
