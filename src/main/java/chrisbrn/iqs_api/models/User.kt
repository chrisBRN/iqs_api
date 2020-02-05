package chrisbrn.iqs_api.models

data class User(
        var username: String? = null,
        var password: String? = null,
        var role: String? = null,
        var email: String? = null
)