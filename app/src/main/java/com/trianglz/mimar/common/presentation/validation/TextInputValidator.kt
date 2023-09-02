package com.trianglz.mimar.common.presentation.validation

import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.validation.RegexPatterns
import com.trianglz.core.presentation.validation.TextInputValidator
import com.trianglz.mimar.R
import com.trianglz.core.R as CoreR

class NumberValidator(
    isOptional: Boolean = false,
    validation: ((String) -> StringWrapper?)? = null
) : TextInputValidator() {
    init {
        isValid = lambda@{
            return@lambda if (validation != null) {
                validation(it)
            } else {
                when {
                    it.isEmpty() -> if (isOptional) null else cantBeEmpty()
                    !RegexPatterns.DigitsOnly.matches(it) ->
                        StringWrapper {
                            getString(
                                CoreR.string.should_be_digits_only,
                                getString(CoreR.string.field)
                            )
                        }
                    else -> null
                }
            }
        }
    }
}


class PhoneNumberValidator(
    isOptional: Boolean = false,
    validation: ((String) -> StringWrapper?)? = null
) : TextInputValidator() {
    init {
        isValid = lambda@{
            return@lambda if (validation != null) {
                validation(it)
            } else {
                when {
                    it.isEmpty() -> if (isOptional) null else cantBeEmpty()
                    !RegexPatterns.DigitsOnly.matches(it) ->
                        StringWrapper {
                            getString(
                                CoreR.string.should_be_digits_only,
                                getString(CoreR.string.phone_number)
                            )
                        }
                    !NineOrMore.matches(it) -> tooShort(9)
                    else -> null
                }
            }
        }
    }
}


class NewPasswordValidator(validation: ((String) -> StringWrapper)? = null) :
    TextInputValidator() {
    init {
        isValid = validator@{
            return@validator if (validation != null) {
                validation(it)
            } else {
                when {
                    it.isEmpty() -> cantBeEmpty()
                    !RegexPatterns.EightOrMore.matches(it) ->  StringWrapper {
                        getString(
                            R.string.password_too_short_less_than,
                            8
                        )
                    }
                    !RegexPatterns.EightOrMoreOneUpperLowerNumberSpecialCharacter.matches(it) ->
                        StringWrapper {
                            getString(
                                CoreR.string.password_include_number_upper_lower_case_spacial_char,
                                getString(R.string.password)
                            )
                        }
                    else -> null
                }
            }
        }
    }
}

class OldPasswordValidator(validation: ((String) -> StringWrapper)? = null) : TextInputValidator() {
    init {
        isValid = lambda@{
            return@lambda if (validation != null) {
                validation(it)
            } else {
                when {
                    it.isEmpty() -> StringWrapper(R.string.password_can_not_empty)
                    else -> null
                }
            }
        }
    }
}
