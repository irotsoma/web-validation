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

/*
 * Created by irotsoma on 4/3/2019.
 */
package com.irotsoma.web.validation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.validation.Validation

/**
 * Tests for PasswordConstraintValidator
 *
 * @author Justin Zak
 */
class PasswordConstraintValidatorTest {
    /**
     * Tests the password format using default password format options, email match, and password match
     *
     * Expect 4 validation errors
     *      Password format errors for password field
     *      Password format errors for confirmPassword field
     *      Email must match
     *      Passwords must match
     */
    @Test
    fun testDefaultInvalid() {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator
        val userRegistration = BasicTestFormObject()
        userRegistration.email="test@irotsoma.com"
        userRegistration.confirmEmail="test222@irotsoma.com"
        userRegistration.password="password"
        userRegistration.confirmPassword="test"

        val constraintViolations = validator!!.validate(userRegistration)
        assertEquals(constraintViolations.size, 4)
    }

    /**
     * Tests the password format using default password format options, email match, and password match
     *
     * Expect no validation errors
     */
    @Test
    fun testDefaultValid() {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator
        val userRegistration = BasicTestFormObject()
        userRegistration.email="test@irotsoma.com"
        userRegistration.confirmEmail="test@irotsoma.com"
        userRegistration.password="xJ3!dij50"
        userRegistration.confirmPassword="xJ3!dij50"

        val constraintViolations = validator!!.validate(userRegistration)
        assertEquals(constraintViolations.size, 0)
    }
    /**
     * Tests the password format using custom format options and password match
     *
     * Expect 3 validation errors
     *      Password format errors for password field
     *      Password format errors for confirmPassword field
     *      Passwords must match
     *
     * Also parses password format error messages to check that the appropriate separator was used and the proper number
     * of messages returned for that field.
     */
    @Test
    fun testPasswordValidationInvalid() {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator
        val userRegistration = PasswordTestFormObject()
        userRegistration.password = "nogood"
        userRegistration.confirmPassword = "reallybad"
        val constraintViolations = validator!!.validate(userRegistration)
        assertEquals(constraintViolations.size, 3)
        constraintViolations.forEach { error ->
            var separator = error.constraintDescriptor.attributes["messageSeparator"]
            if (separator?.toString().isNullOrEmpty()){
                separator = ","
            }
            val messages = error.message.split(separator.toString())
            if (!messages.contains("The password fields must match")){
                assertEquals(messages.size, 4)
            }
        }
    }
    //TODO: Add more tests for PasswordTestFormObject
}