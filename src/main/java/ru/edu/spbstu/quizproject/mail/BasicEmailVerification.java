package ru.edu.spbstu.quizproject.mail;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BasicEmailVerification implements EmailVerification {
    @Override
    public Boolean checkEmail(String email) {
        if(email.length()<4||email.length()>128)
        {
            return false;
        }
        final String EMAIL_PATTERN =
                "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        +"([^-][A-Za-z0-9-]*)?(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{1,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher m = pattern.matcher(email);
        return m.matches();
    }
}
