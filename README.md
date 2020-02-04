## Interview Questions Website - RestAPI Backend

#### Environment / Setup 

##### Local 
docker run 
    -p [port:port]  
    -e POSTGRES_USER=[username]  
    -e POSTGRES_PASSWORD=[password]  
    -d [databaseName]   
    
e.g. "docker run -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=1234 -d postgres"  

DATABASE_URL = postgres://[username]:[password]@localhost:[port]/[databaseName]