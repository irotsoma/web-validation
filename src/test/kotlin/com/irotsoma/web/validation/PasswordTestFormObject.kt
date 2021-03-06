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

import javax.validation.constraints.NotEmpty

/**
 * A class representing a web form object with password field and confirmation field.
 * Password validation uses several non default options for testing.
 *
 * @author Justin Zak
 */
@FieldMatch.List([
    FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
])
class PasswordTestFormObject {
    @NotEmpty
    @ValidPassword(messageSeparator = "\r", alphaCharsCount = 5, numericCharsCount = 2, specialCharsCount = 2, upperCaseCount = 2, minLength = 10, whitespaceAllowed = false)
    var password: String? = null

    @NotEmpty
    @ValidPassword(messageSeparator = "\r", alphaCharsCount = 5, numericCharsCount = 2, specialCharsCount = 2, upperCaseCount = 2, minLength = 10, whitespaceAllowed = false)
    var confirmPassword: String? = null
}