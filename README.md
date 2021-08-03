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
5. If all of the above worked (and you have MongoDB running on port 27017), you
should be able to run this appliation as follows:
```
$ cd $PROJECT_DIR
$ ./gradlew bootRun
```

## Using the GraphiQL interface
Presuming an exceptionless start to the application, the GraphiQL interface
should be available at: http://localhost:9000/graphiql

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