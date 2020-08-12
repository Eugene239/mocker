# Mocker
## OpenSource mock server
Mock Rest API before backend is done, use common https://mocker.badsoft.ml or dedicate instance 
## Build
````
mvn clean package
````
## Docker
### link
https://hub.docker.com/repository/docker/eupavlov/mocker
### Docker build
- Dockerfile - to build
- docker-compose - setting example 
# Run options
## Run
````
java -jar target/mocker.jar
````
## Checked DBs
 - Mysql
 - H2
## Required Params
Set this params for dedicate use with your DB
````
JDBC_DATABASE_PASSWORD
JDBC_DRIVER_CLASS_NAME
JDBC_DATABASE_URL
JDBC_DATABASE_USERNAME
JDBC_POOL_SIZE
````
Add ``hibernate.hbm2ddl.auto = update`` auto creating table in DB
## Local run with H2 in memory db
````
JDBC_DATABASE_PASSWORD = password
JDBC_DRIVER_CLASS_NAME = org.h2.Driver
JDBC_DATABASE_URL = jdbc:h2:mem:testdb
JDBC_DATABASE_USERNAME = sa
JDBC_POOL_SIZE = 1
hibernate.hbm2ddl.auto = update
````
# Database schema
````
create table MOCK
(
    PATH    varchar(512) charset utf8           not null,
    METHOD  varchar(100) charset utf8           not null,
    BODY    varchar(16384) charset utf8         ,
    CODE    int       default 200               not null,
    CREATED timestamp default CURRENT_TIMESTAMP not null,
    primary key (PATH, METHOD)
);
````
