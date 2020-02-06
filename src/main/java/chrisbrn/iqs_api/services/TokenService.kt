package chrisbrn.iqs_api.services

import chrisbrn.iqs_api.config.AppConfig
import chrisbrn.iqs_api.helpers.Logs
import chrisbrn.iqs_api.models.Credentials
import chrisbrn.iqs_api.models.User
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*

private val algorithm: Algorithm = Algorithm.HMAC256(AppConfig.getSigner())
private val verifier: JWTVerifier = JWT.require(algorithm).withIssuer("ChrisBRN").build()
private const val anHour = 1000L * 60L * 60L

fun generateJWT(user: User): String? {
    return try {
        JWT.create()
                .withIssuer("ChrisBRN")
                .withExpiresAt(Date(anHour + System.currentTimeMillis()))
                .withClaim("role", user.role)
                .withClaim("username", user.username)
                .withClaim("password", user.password)
                .sign(algorithm)
    } catch (exception: JWTCreationException) {
        Logs.tokenGenerationError()
        null
    }
}

fun isValidToken(token: String): Boolean {

    try {
        val decoded = verifier.verify(token)

    } catch (exception: JWTVerificationException) {
        Logs.tokenVerificationError()
        return false
    }
    return true
}

fun decodeToken(token: String): Credentials? {

    try {
        val decoded = verifier.verify(token)

        val tokenUsername: String = decoded.getClaim("username").asString()
        val tokenPassword: String = decoded.getClaim("password").asString()
        val tokenRole: String = decoded.getClaim("role").asString()

        return Credentials(tokenUsername, tokenPassword, tokenRole)

    } catch (exception: JWTVerificationException) {
        Logs.tokenVerificationError()
        return null;
    }
}