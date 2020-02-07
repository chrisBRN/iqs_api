package chrisbrn.iqs_api.models

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun okLogin(token: String): ResponseEntity<String> {
    return ResponseEntity(token, HttpStatus.OK)
}

@JvmOverloads
fun ok(message: String = "Success"): ResponseEntity<String> {
    return ResponseEntity(message, HttpStatus.OK)
}

@JvmOverloads
fun forbidden(message: String = "Access Denied"): ResponseEntity<String> {
    return ResponseEntity(message, HttpStatus.FORBIDDEN)
}

@JvmOverloads
fun badRequest(errorMessage: String = "Bad Request"): ResponseEntity<String> {
    return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
}

@JvmOverloads
fun serverError(errorMessage: String = "There Was An Error Please Try Again"): ResponseEntity<String> {
    return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
}
