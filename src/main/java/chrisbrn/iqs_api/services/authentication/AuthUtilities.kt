package chrisbrn.iqs_api.services.authentication

import chrisbrn.iqs_api.models.Credentials
import chrisbrn.iqs_api.models.User
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.security.SecureRandom
import java.util.*


fun isPasswordValid(supplied: Credentials, storedUser: User): Boolean {
    val encoder = BCryptPasswordEncoder(12)
    return encoder.matches(supplied.password, storedUser.password)
}

fun isEmailAddressValid(email: String?): Boolean {
    return EmailValidator.getInstance(false).isValid(email)
}



fun isRoleValid(roleToCheck: String): Boolean {
    for (role in Role.values()) {
        if (role.name == roleToCheck) {
            return true
        }
    }
    return false
}

fun hasAddCandidatePrivileges(role: String): Boolean {
    return role == Role.ADMIN.name || role == Role.STAFF.name
}

fun hasAddAnyUserPrivileges(role: String): Boolean {
    return role == Role.ADMIN.name
}

fun generateRandomBase64Token(byteLength: Int): String? {
    // https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
    val secureRandom = SecureRandom()
    val token = ByteArray(byteLength)
    secureRandom.nextBytes(token)
    return Base64.getUrlEncoder().withoutPadding().encodeToString(token)
}