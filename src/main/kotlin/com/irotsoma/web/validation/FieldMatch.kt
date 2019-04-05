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

/* Based on Java code at https://memorynotfound.com/field-matching-bean-validation-annotation-example/ */

/*
 * Created by irotsoma on 4/3/2019.
 */
package com.irotsoma.web.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * Validation annotation to validate that two fields match
 *
 * @param message the error message to be returned if fields do not match
 * @param groups array of classes used for grouping annotation classes
 * @param payload a class that implements Payload to provide metadata
 * @param first holds the field name for the first value for comparison
 * @param second holds the field name for the the second value for comparison
 *
 * @author Justin Zak
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [FieldMatchValidator::class])
@MustBeDocumented
annotation class FieldMatch(
    val message: String = "The fields must match",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val first: String,
    val second: String
) {

    /**
     * Allows for grouping multiple validation annotations into a single annotation since only one of each annotation is allowed
     * Note: items in the array should not have the @ sign
     *
     * Example: Add this annotation to the class to validate both password and email are the same as their confirm fields
     * * @FieldMatch.List([
     * *    FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
     * *    FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
     * * ])
     *
     * @param value A list of FieldMatch instances to be validated
     */
    @Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    annotation class List(val value: Array<FieldMatch>)
}

