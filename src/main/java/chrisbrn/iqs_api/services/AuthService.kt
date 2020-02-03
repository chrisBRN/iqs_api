package chrisbrn.iqs_api.services

import chrisbrn.iqs_api.models.Credentials
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import java.util.*

private const val anHour = 1000L * 60L * 60L
private const val signer: String = "secret"

public fun generateJWT(): String? {

    // https://github.com/auth0/java-jwt
    val expiry = Date(anHour + System.currentTimeMillis())
    val algorithm: Algorithm = Algorithm.HMAC256(signer)

    return try {

        JWT.create()
                .withIssuer("ChrisBRN")
                .withExpiresAt(expiry)
                .sign(algorithm)

    } catch (exception: JWTCreationException) {
        System.err.println("JWTCreationException")
        null
    }
}

public fun validateJWT(token: String, audience: String): Boolean {

    // https://github.com/auth0/java-jwt
    val algorithm: Algorithm = Algorithm.HMAC256(signer)

    return try {

        val verifier = JWT
                .require(algorithm)
                .withIssuer("ChrisBRN")
                .withAudience(audience)
                .build()
                .verify(token)
        true
    } catch (exception: JWTVerificationException) {
        System.err.println("JWTVerificationFailed")
        false
    }
}

public fun validateCredentials(credentials: Credentials): Boolean {
    return  isUsernameValid(credentials.username) &&
            isPasswordValid(credentials.password)
}

private fun isUsernameValid(username: String): Boolean {
    return username == "username"
}

private fun isPasswordValid(password: String): Boolean {
    return password == "password"
}