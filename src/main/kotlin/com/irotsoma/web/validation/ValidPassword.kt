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

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * A validation annotation to validate passwords
 *
 * If no rules parameters are sent, the defaults below are not used. Instead the following rules are used:
 * Length 8-30 Chars
 * 1 Uppercase
 * 1 Lowercase
 * 1 Numeric
 * 1 Special
 * No Whitespaces
 *
 * @param message the error message
 * @param messageSeparator separator character when multiple error messages are returned
 * @param groups array of classes used for grouping annotation classes
 * @param payload a class that implements Payload to provide metadata
 * @param messagePropertiesLocation the file location of a message.properties file to use for translating error messages
 * @param minLength sets the minimum length of the password, default is 1
 * @param maxLength sets the maximum length of the password, default is Int.MAX_VALUE
 * @param upperCaseCount sets the minimum number of uppercase characters the password must contain
 * @param lowerCaseCount sets the minimum number of lowercase characters the password must contain
 * @param alphaCharsCount sets the minimum number of alphabetic characters the password must contain
 * @param numericCharsCount sets the minimum number of numeric characters the password must contain
 * @param specialCharsCount sets the minimum number of special characters the password must contain
 * @param regex sets a regex to validate the password against
 * @param whitespaceAllowed sets whether whitespaces are allowed in the password, default is false
 *
 * @author Justin Zak
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [PasswordConstraintValidator::class])
annotation class ValidPassword(
    val message: String = "Invalid Password",
    val messageSeparator: String = "\u001E",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val messagePropertiesLocation: String = "",
    val minLength: Int = 1,
    val maxLength: Int = Int.MAX_VALUE,
    val upperCaseCount: Int = 0,
    val lowerCaseCount: Int = 0,
    val alphaCharsCount: Int = 0,
    val numericCharsCount: Int = 0,
    val specialCharsCount: Int = 0,
    val regex: String = "",
    val whitespaceAllowed: Boolean = true


)
