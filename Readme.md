# Spring Boot API to Count search text and get top text from sample paragraph

Restful API for searching the count for list of text in sample paragraph. 
I am using mysql database to store the paragraph. it is a scalable approach, so that we can add any other paragraphs without redeploying the application.
Application joins all paragraphs in the database and searches for the text in all of them.

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Mysql - 5.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/Dj45chd/counter-api.git
```

**2. Create Mysql database**
```bash
create database optusdb
```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Build and run the app using maven**

```bash
mvn package
java -jar target/counter-api-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following APIs.
    
    POST /counter-api/search
    
    GET /counter-api/top/{count}
    

You can test them using postman or using curl command.
```
curl -X POST http://localhost:8080/counter-api/search -H "Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -d '{"searchText":["Duis", "Sed", "Donec", "Augue", "Pellentesque", "123"]}' -H "Content-Type: application/json"
```

```
curl http://localhost:8080/counter-api/top/20 -H "Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -H "Accept: text/csv"
```
