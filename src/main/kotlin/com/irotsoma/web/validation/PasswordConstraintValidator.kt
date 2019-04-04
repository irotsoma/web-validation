/*
 *  Copyright (C) 2019  Irotsoma, LLC
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

/* Based on Java code at https://memorynotfound.com/custom-password-constraint-validator-annotation/ */

/*
 * Created by irotsoma on 4/3/2019.
 */

package com.irotsoma.web.validation

import org.passay.*
import java.util.*
import java.util.stream.Collectors
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordConstraintValidator : ConstraintValidator<ValidPassword, String> {
    override fun isValid(password: String, context: ConstraintValidatorContext): Boolean {
        val validator = PasswordValidator(Arrays.asList(
            // at least 8 characters
            LengthRule(8, 30),
            // at least one upper-case character
            CharacterRule(EnglishCharacterData.UpperCase, 1),
            // at least one lower-case character
            CharacterRule(EnglishCharacterData.LowerCase, 1),
            // at least one digit character
            CharacterRule(EnglishCharacterData.Digit, 1),
            // at least one symbol (special character)
            CharacterRule(EnglishCharacterData.Special, 1),
            // no whitespace
            WhitespaceRule()
        ))
        val result = validator.validate(PasswordData(password))
        if (result.isValid) {
            return true
        }
        val messages = validator.getMessages(result)
        val messageTemplate = messages.stream()
            .collect(Collectors.joining(","))
        context.buildConstraintViolationWithTemplate(messageTemplate)
            .addConstraintViolation()
            .disableDefaultConstraintViolation()
        return false
    }

}
