package chrisbrn.iqs_api.services

import chrisbrn.iqs_api.models.Credentials
import chrisbrn.iqs_api.models.User
import chrisbrn.iqs_api.models.UserRole
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

fun isPasswordValid(supplied: Credentials, storedUser: User): Boolean {
    val encoder = BCryptPasswordEncoder(12)
    return encoder.matches(supplied.password, storedUser.password)
}

fun isEmailAddressValid(email: String?): Boolean {
    return EmailValidator.getInstance(false).isValid(email)
}

fun isRoleValid(roleToCheck: String): Boolean {
    for (role in UserRole.values()) {
        if (role.name == roleToCheck) {
            return true
        }
    }
    return false
}

fun canAddUser(roleToCheck: String): Boolean {
    return roleToCheck == UserRole.ADMIN.name || roleToCheck == UserRole.INIT.name
}