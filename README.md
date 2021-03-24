# testBackEnd

## Serve application

Run this Application in development mode.

####Requirements : Java 11(1.11.0-openjdk-amd64) , Maven

###Custom company name :
Replace 'MyCompany' with desired company name.

`$ mvn spring-boot:run -Dspring-boot.run.arguments=--company.name=MyCompany`

###Default company name : 

`$ mvn spring-boot:run`

###Application will be served at :

`localhost:8080` [ Angular application(UI) will search for this IP and Port ] 

###Find Database at

`localhost:8080/h2-console`
