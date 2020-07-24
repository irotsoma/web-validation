/*
 *  Copyright (C) 2020  Irotsoma, LLC
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
 * Created by irotsoma on 7/12/2020.
 */
package com.irotsoma.web.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * Validation annotation to validate that two fields match
 *
 * @param message the default error message to be returned if fields do not match and no message was supplied in the annotation
 * @param groups array of classes used for grouping annotation classes
 * @param payload a class that implements Payload to provide metadata
 * @param fields holds the field names of properties to be compared
 *
 * @author Justin Zak
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [OneNotBlankValidator::class])
@MustBeDocumented
annotation class OneNotBlank(
    val message: String = "One of the fields must not be blank.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val fields: Array<String>
) {

    /**
     * Allows for grouping multiple validation annotations into a single annotation since only one of each annotation is allowed
     * Note: items in the array should not have the @ sign
     *
     * Example: Add this annotation to the class to validate both password and email are the same as their confirm fields
     * * @OneNotBlank.List([
     * *    OneNotBlank(fields = ["firstNonBlank", "secondNonBlank", "thirdNonBlank"], message="At least one of these fields firstNonBlank, secondNonBlank, or thirdNonBlank must be filled."),
     * *    OneNotBlank(fields = ["fourthNonBlank", "fifthNonBlank"], message="At least one of these fields fourthNonBlank or fifthNonBlank must be filled."
     * * ])
     *
     * @param value A list of OneNotBlank instances to be validated
     */
    @Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    annotation class List(val value: Array<OneNotBlank>)
}

