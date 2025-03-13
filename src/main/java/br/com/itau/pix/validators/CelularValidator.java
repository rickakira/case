package br.com.itau.pix.validators;

import br.com.itau.pix.validators.interfaces.Celular;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CelularValidator implements ConstraintValidator<Celular, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = PhoneNumberUtil.getInstance().parse(value, "BR");

            return util.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }
}