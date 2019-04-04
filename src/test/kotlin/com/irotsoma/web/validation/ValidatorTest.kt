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
import javax.validation.Validator


class PasswordConstraintValidatorTest {
    private var validator: Validator? = null
    init{
        val factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    @Test
    fun testInvalid() {
        val userRegistration = TestFormObject()
        userRegistration.email="test@irotsoma.com"
        userRegistration.confirmEmail="test222@irotsoma.com"
        userRegistration.password="password"
        userRegistration.confirmPassword="test"

        val constraintViolations = validator!!.validate(userRegistration)
        assertEquals(constraintViolations.size, 4)
    }

    @Test
    fun testValid() {
        val userRegistration = TestFormObject()
        userRegistration.email="test@irotsoma.com"
        userRegistration.confirmEmail="test@irotsoma.com"
        userRegistration.password="xJ3!dij50"
        userRegistration.confirmPassword="xJ3!dij50"

        val constraintViolations = validator!!.validate(userRegistration)
        assertEquals(constraintViolations.size, 0)
    }

    //TODO: Tests for parameters

}