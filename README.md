# web-validation

A JVM library written in Kotlin that adds some custom bean anotations for validating input using a bean validator like hybernate validator.

The current implementation introduces a @ValidPassword annotation to be used on bean fields and a @FieldMatch annotation for validating that 2 fields match each other.

## @ValidPassword
The following parameters are available to customize password validation:

* `message` overrides the default error message
* `messageSeparator` separator character when multiple error messages are returned
* `groups` array of classes used for grouping annotation classes
* `payload` a class that implements Payload to provide metadata
* `messagePropertiesLocation` the file location of a message.properties file to use for translating error messages. See the included [messages.properties](https://github.com/irotsoma/web-validation/blob/master/src/main/resources/messages.properties) for an example.
* `minLength` sets the minimum length of the password, default is 1
* `maxLength` sets the maximum length of the password, default is Int.MAX_VALUE
* `upperCaseCount` sets the minimum number of uppercase characters the password must contain
* `lowerCaseCount` sets the minimum number of lowercase characters the password must contain
* `alphaCharsCount` sets the minimum number of alphabetic characters the password must contain
* `numericCharsCount` sets the minimum number of numeric characters the password must contain
* `specialCharsCount` sets the minimum number of special characters the password must contain
* `regex` sets a regex to validate the password against
* `whitespaceAllowed` sets whether whitespaces are allowed in the password, default is false

## @FieldMatch
Use the parameters `first` and `second` to name the fields to be compared.

Use `message` to specify an error message other than the default.

In order to allow for multiple instances of `FieldMatch`, use the `.List` property and send an array of instances of `FieldMatch` as in the example below.

Example bean in Kotlin:
```kotlin
@FieldMatch.List([
    FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
    FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
])
class NewUserForm {
    @NotEmpty
    var username: String? = null
    @NotEmpty
    @ValidPassword
    var password: String? = null
    @NotEmpty
    @ValidPassword
    var passwordConfirm: String? = null
    @Email
    var email: String? = null
    @Email
    var emailConfirm: String? = null
    var roles: Array<Option> = emptyArray()
}
```
