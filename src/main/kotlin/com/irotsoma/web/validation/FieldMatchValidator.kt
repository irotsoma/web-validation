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

import org.apache.commons.beanutils.BeanUtils
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * Constraint Validator for the Field match annotation
 *
 * @property firstFieldName Holds the name of the first field to be compared
 * @property secondFieldName Holds the name of the first field to be compared
 * @property message Holds the error message to use if validation fails
 *
 * @author Justin Zak
 */
class FieldMatchValidator : ConstraintValidator<FieldMatch, Any> {

    private var firstFieldName: String? = null
    private var secondFieldName: String? = null
    private var message: String? = null

    /**
     * Initializes the validator pulling values from the FieldMatch annotation parameters
     *
     * @param constraintAnnotation the instance of the annotation in context
     */
    override fun initialize(constraintAnnotation: FieldMatch) {
        firstFieldName = constraintAnnotation.first
        secondFieldName = constraintAnnotation.second
        message = constraintAnnotation.message
    }

    /**
     * Builds the validator and runs the validate function. Errors are added to the context.
     *
     * @param value value to be validated
     * @param context context in which the constraint is evaluated
     * @return true if all validations passed otherwise false
     */
    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        var valid = true
        try {
            val firstObj = BeanUtils.getProperty(value, firstFieldName)
            val secondObj = BeanUtils.getProperty(value, secondFieldName)

            valid = firstObj == null && secondObj == null || firstObj != null && firstObj == secondObj
        } catch (ignore: Exception) {
            // ignore
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(firstFieldName)
                .addConstraintViolation()
                .disableDefaultConstraintViolation()
        }

        return valid
    }
}
