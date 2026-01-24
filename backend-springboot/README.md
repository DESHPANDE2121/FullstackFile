# HR Backend (Spring Boot + MongoDB + JWT)

This backend uses **Spring Data MongoDB** (not Node/Mongoose). The MongoDB connection string is configured in:

`src/main/resources/application.properties`

Default DB URL (as requested):

`mongodb://localhost:27017/curd_database`

## Run (Terminal)
1) Start MongoDB (local)
   - Make sure MongoDB service is running and listening on `localhost:27017`.

2) Update JWT secret (IMPORTANT)
   - In `application.properties`, set `app.jwt.secret` to a 32+ character value, **or** set env var:
     - Windows (PowerShell): `$env:JWT_SECRET="YOUR_SECRET_32+_CHARS"`
     - Linux/Mac: `export JWT_SECRET="YOUR_SECRET_32+_CHARS"`

3) Run the backend
   - If you have Maven installed:
     - `mvn spring-boot:run`
   - Or, open the project in IntelliJ/Eclipse and run `HrBackendApplication`.

Server: http://localhost:8080
