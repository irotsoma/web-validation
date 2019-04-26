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
import java.io.File
import java.util.*
import java.util.stream.Collectors
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * Constraint Validator for the ValidPassword annotation.
 *
 * If rules parameters are not sent, the following rules are used:
 * LengthRule(8, 30),
 * CharacterRule(EnglishCharacterData.UpperCase, 1),
 * CharacterRule(EnglishCharacterData.LowerCase, 1),
 * CharacterRule(EnglishCharacterData.Digit, 1),
 * CharacterRule(EnglishCharacterData.Special, 1),
 * WhitespaceRule()
 *
 * If no message resolver is sent, the messages.properties is used from the library.
 *
 * @property messageResolver A MessageResolver used to translate the default messages from passay into something more readable.
 * @property rules A list of Rules to be used. Blank if no parameters were sent in the annotation.
 * @property messageSeparator The separator string to be used between each message for a given validation error
 * @property messageProperties A Properties object containing the currently valid message.properties. Getter is public, but setter is private.
 *
 * @author Justin Zak
 */
class PasswordConstraintValidator : ConstraintValidator<ValidPassword, String> {
    private var messageResolver: MessageResolver? = null
    private val rules: ArrayList<Rule> = arrayListOf()
    private var messageSeparator = ","

    var messageProperties: Properties? = null
        private set

    /**
     * Initializes the validator setting up the message resolver and any rules sent in the annotation parameters
     *
     * @param constraintAnnotation the instance of the annotation in context
     */
    override fun initialize(constraintAnnotation: ValidPassword) {
        //get a message resolver from either the path sent as a parameter or use the one in the resources folder
        val resourceFile = File(this.javaClass.classLoader.getResource("messages.properties").file)
        if (constraintAnnotation.messageSeparator.isNotEmpty()){
            messageSeparator = constraintAnnotation.messageSeparator
        }
        val messageProperties =
            if (constraintAnnotation.messagePropertiesLocation.isNotBlank()){
                val incomingFile = File(constraintAnnotation.messagePropertiesLocation)
                if (incomingFile.exists())
                    incomingFile
                else
                    resourceFile
            } else {
                resourceFile
            }
        if (messageProperties.exists()) {
            this.messageProperties = Properties()
            this.messageProperties!!.load(messageProperties.inputStream())
            messageResolver = PropertiesMessageResolver(this.messageProperties)
        }
        // get all settings for selected rules
        if (constraintAnnotation.minLength > 1 || constraintAnnotation.maxLength < Int.MAX_VALUE) {
            rules.add(LengthRule(constraintAnnotation.minLength, constraintAnnotation.maxLength))
        }
        if (constraintAnnotation.upperCaseCount > 0){
            rules.add(CharacterRule(EnglishCharacterData.UpperCase,constraintAnnotation.upperCaseCount))
        }
        if (constraintAnnotation.lowerCaseCount > 0){
            rules.add(CharacterRule(EnglishCharacterData.LowerCase,constraintAnnotation.lowerCaseCount))
        }
        if (constraintAnnotation.numericCharsCount > 0){
            rules.add(CharacterRule(EnglishCharacterData.Digit,constraintAnnotation.numericCharsCount))
        }
        if (constraintAnnotation.alphaCharsCount > 0){
            rules.add(CharacterRule(EnglishCharacterData.Alphabetical,constraintAnnotation.alphaCharsCount))
        }
        if (constraintAnnotation.specialCharsCount > 0){
            rules.add(CharacterRule(EnglishCharacterData.Special,constraintAnnotation.specialCharsCount))
        }
        if (constraintAnnotation.regex.isNotBlank()){
            rules.add(AllowedRegexRule(constraintAnnotation.regex))
        }
        if (!constraintAnnotation.whitespaceAllowed){
            rules.add(WhitespaceRule())
        }
        super.initialize(constraintAnnotation)
    }

    /**
     * Builds the validator and runs the validate function. Errors are added to the context.
     *
     * @param password value to be validated
     * @param context context in which the constraint is evaluated
     * @return true if all validations passed otherwise false
     */
    override fun isValid(password: String, context: ConstraintValidatorContext): Boolean {

        val validator =
            if (rules.size == 0) {
                PasswordValidator(
                    messageResolver, listOf(
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
                    )
                )
            } else {
                PasswordValidator(messageResolver, rules)
            }
        val result = validator.validate(PasswordData(password))
        if (result.isValid) {
            return true
        }
        val messages = validator.getMessages(result)
        val messageTemplate = messages.stream()
            .collect(Collectors.joining(messageSeparator))
        context.buildConstraintViolationWithTemplate(messageTemplate)
            .addConstraintViolation()
            .disableDefaultConstraintViolation()
        return false
    }

}
