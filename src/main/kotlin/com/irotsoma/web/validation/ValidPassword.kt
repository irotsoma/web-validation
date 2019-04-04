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

@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [PasswordConstraintValidator::class])
annotation class ValidPassword(
    val message: String = "Invalid Password",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)