# Pdfmanager

## Installation
1. Install SDKMan: https://sdkman.io/install
```
$ curl -s "https://get.sdkman.io" | bash
$ source "$HOME/.sdkman/bin/sdkman-init.sh"
```
2. Install Java 11: ```sdk install java 11.0.11.hs-adpt```
3. Install gradle 7.1.1: ```sdk install gradle 7.1.1```
4. Install Kotlin 1.5.21: ```sdk install kotlin 1.5.21```
5. Configure application.properties to reflect your Pg setup:
```
spring.datasource.url=jdbc:postgresql://localhost/pdf_manager
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASS
spring.jpa.generate-ddl=true

# Pretty-print JSON responses
spring.jackson.serialization.indent_output=true
```
6. Create a db with same name listed in your application.properties:
```
postgres=# create database pdf_manager;
```
7. If all of the above worked (and you have PostgreSQL running on the standard port), you
should be able to run this appliation as follows:
```
$ cd $PROJECT_DIR
$ ./gradlew bootRun
```

## Using the GraphiQL interface
Presuming an exceptionless start to the application, the GraphiQL interface
should be available at: http://localhost:8080/graphiql

Per the schema there are 2 query operations: `pdfs` and `getPdf(id: ID!)`
(where ID is a UUID string).  There is a single mutation operation:
```
uploadPdf(
    clientName: String!
    countryCode: String!
    data: String!
    reportName: String!
    reportType: ReportType!
): PdfReference!
```

Enjoy!