package chrisbrn.iqs_api.models

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@JvmOverloads
public fun ok(token : String = "Success"): ResponseEntity<String> {
    return ResponseEntity(token, HttpStatus.OK)
}

@JvmOverloads
public fun badRequest(errorMessage: String = "Bad Request"): ResponseEntity<String> {
    return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
}