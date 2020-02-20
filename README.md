# Interview Questions Website






## ENVIRONMENT / SETUP 

```
SIGNER = [secretword]
DATABASE_URL = postgres://[DBusername]:[DBpassword]@localhost:[port]/[databaseName]  
``` 

Env Var             | Example                                           | Description                                               | More Info
:-------------      | :-------------                                    | :-------------                                            | :-------------
`SIGNER`            | `(("f)VFUR-uY_:7yH<b6GNUvw^t8RM#C3!`              | A secure password / passphrase, used to sign JWTs         | https://github.com/auth0/java-jwt
`DATABASE_URL`      | `postgres://root:1234@localhost:5432/postgres`    | Allows for independent locales (i.e. local & staging)     |

`docker run -p [port:port] -e POSTGRES_USER=[username] -e POSTGRES_PASSWORD=[password] -d [databaseName]`   
    
e.g. `docker run -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=1234 -d postgres` 

## ENDPOINTS
