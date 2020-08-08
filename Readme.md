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
